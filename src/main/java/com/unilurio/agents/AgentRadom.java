/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import com.unilurio.ntxuva.GameTreeSearch;
import com.unilurio.ntxuva.Position;
import com.unilurio.ntxuva.RandomPlayer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class AgentRadom extends Agent {

    private SigletonNtxuvaBoard game = SigletonNtxuvaBoard.getInstance();

    @Override
    protected void setup() {

        Object[] args = getArguments();

        if ((args.length > 1) && (!args[0].toString().equals("x") || !args[0].toString().equals("o"))) {
            System.out.println("Error:  Invalid arguments! ");
            return;
        }

        System.out.println(args.length > 0 ? args[0].toString() : "");

        System.out.println("Agent " + (args.length > 0 ? args[0].toString() : "o") + "- Random");
        System.out.println(game.ntxuva.toString());
        addBehaviour(new OneShotBehaviour(this) {
            @Override
            public void action() {

//                System.out.println("entrou one shot O");
                System.out.println("entrou " + (args.length > 0 ? args[0].toString() : "o") + " one shot");
//                game.ntxuva.turn = 'x';
                if (args.length > 0) {
                    game.ntxuva.turn = args[0].toString().charAt(0) == 'o' ? 'x' : 'o';
                } else {
                    game.ntxuva.turn = 'x';
                }

                System.out.println(game.ntxuva.toString());
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("agent" + (args.length > 0 ? (args[0].toString().charAt(0) == 'o' ? 'x' : 'o') : 'x'), AID.ISLOCALNAME));
                msg.setLanguage("English");
//                msg.setContent("TurnX");
                msg.setContent(args.length > 0 ? (args[0].toString().equals("x") ? "TurnO" : "TurnX") : "TurnO");
                myAgent.send(msg);
            }

        });

        addBehaviour(new Behaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    String labelTurn = args[0].toString().equals("x") ? "TurnX" : "TurnO";
//                    if (content.equalsIgnoreCase("TurnO")) {
//                        System.out.println("O playing ...");
                    if (content.equalsIgnoreCase(args.length > 0 ? labelTurn : "TurnX")) {
                        System.out.println((args.length > 0 ? args[0].toString() : "x") + " playing ...");
                        System.out.println("antes ");
                        System.out.println(game.ntxuva.toString());
                        //System.out.println(game.ntxuva.turn);
//                         Position bestMove = new GameTreeSearch(new AlphaBetaPrunning()).getBestMove(game.ntxuva);
                        Position bestMove = new GameTreeSearch(new RandomPlayer()).getBestMove(game.ntxuva);
//                        System.out.println("o Move:" + bestMove);
                        System.out.println((args.length > 0 ? args[0].toString() : "x") + " Move:" + bestMove);
                        System.out.println("N os seeds:" + game.ntxuva.board[bestMove.row][bestMove.column]);

                        try {
                            game.ntxuva = game.ntxuva.move(bestMove);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "fail to move");
                        }
                        System.out.println("depois");
                        System.out.println(game.ntxuva.toString());
                        //myGui.btn.setBackground(Color.RED);
                    }

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
//                    reply.setContent("TurnX");
                    reply.setContent(args[0].toString().equals("x") ? "TurnO" : "TurnX");
                    myAgent.send(reply);
                }

            }

            @Override
            public boolean done() {
                //System.out.println("entrou O done return ="+game.ntxuva.gameEnd());
                if (game.ntxuva.gameEnd()) {
                     
                }
                return game.ntxuva.gameEnd();
            }
        });
    }

    @Override
    protected void takeDown() {

//        myGui.dispose();
//        int u = new GameTreeSearch(new AlphaBetaPrunning()).utilidade(game.ntxuva);
        int u = new GameTreeSearch(new RandomPlayer()).utilidade(game.ntxuva);
        System.out.println("o utility:" + u);
        if (u > 0) {
            System.out.println("X ganhou");
        } else {
            System.out.println("O ganhou");
        }
        System.out.println("" + getAID().getName() + " terminating.");
    }

}
