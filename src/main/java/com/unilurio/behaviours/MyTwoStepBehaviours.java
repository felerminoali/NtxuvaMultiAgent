/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.behaviours;

import com.unilurio.agents.SigletonNtxuvaBoard;
import com.unilurio.ntxuva.GameTreeSearch;
import com.unilurio.ntxuva.Player;
import com.unilurio.ntxuva.Position;
import com.unilurio.ntxuva.RandomPlayer;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class MyTwoStepBehaviours extends Behaviour {

    private int step = 0;
    private Object[] args;
    private SigletonNtxuvaBoard game;
    private Player player;

    public MyTwoStepBehaviours(Agent agent, SigletonNtxuvaBoard game, Player player) {
        myAgent = agent;
        args = myAgent.getArguments();
        this.game = game;
        this.player = player;
    }

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

                // check if it is a win situation or not
                
                Position bestMove;
                if (step == 0) {
                    bestMove = new GameTreeSearch(new RandomPlayer()).getBestMove(game.ntxuva);
                    step++;
                } else {
                    bestMove = new GameTreeSearch(player).getBestMove(game.ntxuva);
                }

                System.out.println((args.length > 0 ? args[0].toString() : "x") + " Move:" + bestMove);
                System.out.println("N os seeds:" + game.ntxuva.board[bestMove.row][bestMove.column]);

                try {
                    game.ntxuva = game.ntxuva.move(bestMove);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "fail to move");
                }
                System.out.println("depois");
                System.out.println(game.ntxuva.toString());
            }

            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent(args[0].toString().equals("x") ? "TurnO" : "TurnX");
            myAgent.send(reply);
        }

    }

    @Override
    public boolean done() {
        if (game.ntxuva.gameEnd()) {
            myAgent.doDelete();
        }
        return game.ntxuva.gameEnd();
    }

}
