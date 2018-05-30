/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.teste;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.awt.Color;

/**
 *
 * @author DNIC2012
 */
public class AgentX extends Agent {

    private SigletonTestGui myGui = SigletonTestGui.getInstance();

    protected void setup() {

        myGui.showGui();

        addBehaviour(new OneShotBehaviour(this) {
            @Override
            public void action() {

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("agento", AID.ISLOCALNAME));
                msg.setLanguage("English");
                msg.setContent("TurnO");
                myAgent.send(msg);
            }

        });

        addBehaviour(new Behaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();

                    if (content.equalsIgnoreCase("TurnX")) {
                        System.out.println("X playing ...");
//                        System.out.println("messagem:" + content);
                        myGui.btn.setBackground(Color.CYAN);
                    }

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("TurnO");
                    myAgent.send(reply);

                }

            }

            @Override
            public boolean done() {
                return false;
            }
        });

    }

    protected void takeDown() {
//        myGui.dispose();
        System.out.println("" + getAID().getName() + " terminating.");
    }

}
