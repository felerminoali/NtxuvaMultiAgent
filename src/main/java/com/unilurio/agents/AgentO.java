/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import com.unilurio.ntxuva.AlphaBetaPrunning;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.awt.Color;
import javax.swing.JOptionPane;
import com.unilurio.ntxuva.GameTreeSearch;
import com.unilurio.ntxuva.MiniMax;
import com.unilurio.ntxuva.Position;

/**
 *
 * @author DNIC2012
 */
public class AgentO extends Agent {

//    private SigletonNtxuvaGui myGui = SigletonNtxuvaGui.getInstance();
    private SigletonNtxuvaBoard game = SigletonNtxuvaBoard.getInstance();

    protected void setup() {
        //myGui.showGui();
//        System.out.println("Agent O - MiniMax");
         System.out.println("Agent O - AlphaBetaPruning");
        System.out.println(game.ntxuva.toString());

        addBehaviour(new OneShotBehaviour(this) {
            @Override
            public void action() {

               
                System.out.println("entrou one shot O");
                 game.ntxuva.turn = 'o';
                System.out.println(game.ntxuva.toString());
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("agentx", AID.ISLOCALNAME));
                msg.setLanguage("English");
                msg.setContent("TurnX");
                myAgent.send(msg);
            }

        });

        addBehaviour(new Behaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();

                    if (content.equalsIgnoreCase("TurnO")) {
                        System.out.println("O playing ...");
                       
                        System.out.println(game.ntxuva.turn);
                         Position bestMove = new GameTreeSearch(new AlphaBetaPrunning()).getBestMove(game.ntxuva);
//                         Position bestMove = new GameTreeSearch(new MiniMax()).getBestMove(game.ntxuva);
                        System.out.println("o Move:" + bestMove);

                        try {
                            game.ntxuva = game.ntxuva.move(bestMove);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }

                        System.out.println(game.ntxuva.toString());
                        //myGui.btn.setBackground(Color.RED);
                    }

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("TurnX");
                    myAgent.send(reply);
                }

            }

            @Override
            public boolean done() {
                //System.out.println("entrou O done return ="+game.ntxuva.gameEnd());
                if(game.ntxuva.gameEnd()){
                    myAgent.doDelete();
                }
                return game.ntxuva.gameEnd();
            }
        });
    }

    @Override
    protected void takeDown() {

//        myGui.dispose();
        int u = new GameTreeSearch(new AlphaBetaPrunning()).utilidade(game.ntxuva);
//        int u = new GameTreeSearch(new MiniMax()).utilidade(game.ntxuva);
        if (u < 0) {
            System.out.println("O ganhou");
        } else {
            System.out.println("X ganhou");
        }
        System.out.println("" + getAID().getName() + " terminating.");
    }

}
