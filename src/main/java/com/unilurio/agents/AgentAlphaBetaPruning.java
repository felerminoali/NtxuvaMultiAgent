/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.awt.Color;
import javax.swing.JOptionPane;
import com.unilurio.ntxuva.*;

/**
 *
 * @author DNIC2012
 */
public class AgentAlphaBetaPruning extends Agent {

//    private SigletonNtxuvaGui myGui = SigletonNtxuvaGui.getInstance();
    private SigletonNtxuvaBoard game = SigletonNtxuvaBoard.getInstance();

    protected void setup() {

//        myGui.showGui();
//        System.out.println("Agent X - MiniMax");
        Object[] args = getArguments();

        if ((args.length > 1) && (!args[0].toString().equals("x") || !args[0].toString().equals("o"))) {
            System.out.println("Error:  Invalid arguments! ");
            return;
        }

        System.out.println(args.length > 0 ? args[0].toString() : "");
        System.out.println("Agent "+(args.length > 0 ? args[0].toString() : "x")+" - AlphaBetaPruning");
        System.out.println(game.ntxuva.toString());

        addBehaviour(new OneShotBehaviour(this) {
            @Override
            public void action() {
                System.out.println("entrou "+ (args.length > 0 ? args[0].toString() : "x") + " one shot");

                //game.ntxuva.turn = 'o';
                if (args.length > 0) {
                    game.ntxuva.turn = args[0].toString().charAt(0) == 'x' ? 'o' : 'x';
                } else {
                    game.ntxuva.turn = 'o';
                }

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                msg.addReceiver(new AID("agent"+(args.length > 0 ? (args[0].toString().charAt(0) == 'x' ? 'o' : 'x'):'o'), AID.ISLOCALNAME));
                msg.setLanguage("English");
//                msg.setContent("TurnO");
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
                    if (content.equalsIgnoreCase(args.length > 0 ? labelTurn : "TurnX")) {
                        System.out.println((args.length > 0 ? args[0].toString() : "x") + " playing ...");

                        System.out.println("antes ");
                        System.out.println(game.ntxuva.toString());

                        Position bestMove = new GameTreeSearch(new AlphaBetaPrunningPlayer()).getBestMove(game.ntxuva);
//                         Position bestMove = new GameTreeSearch(new MiniMax()).getBestMove(game.ntxuva);
                        System.out.println((args.length > 0 ? args[0].toString() : "x") +" Move:" + bestMove);
                        System.out.println("N os seeds:" + game.ntxuva.board[bestMove.row][bestMove.column]);

                        try {
                            game.ntxuva = game.ntxuva.move(bestMove);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "fail to move");
                        }
                        System.out.println("depois");
                        System.out.println(game.ntxuva.toString());
                        //myGui.btn.setBackground(Color.CYAN);
                    }

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
//                    reply.setContent("TurnO");
                     reply.setContent(args[0].toString().equals("x") ? "TurnO" : "TurnX");
                    myAgent.send(reply);

                }

            }

            @Override
            public boolean done() {
                //System.out.println("entrou X done return ="+game.ntxuva.gameEnd());
                if (game.ntxuva.gameEnd()) {
                    myAgent.doDelete();
                }
                return game.ntxuva.gameEnd();
            }
        });

    }

    @Override
    protected void takeDown() {
//        myGui.dispose();

        int u = new GameTreeSearch(new AlphaBetaPrunningPlayer()).utilidade(game.ntxuva);

        System.out.println("x utility:" + u);
        if (u > 0) {
            System.out.println("X ganhou");
        } else {
            System.out.println("O ganhou");
        }
        System.out.println("" + getAID().getName() + " terminating.");
    }

}
