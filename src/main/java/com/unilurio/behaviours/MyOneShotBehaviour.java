/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.behaviours;

import com.unilurio.agents.SigletonNtxuvaBoard;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author user
 */
public class MyOneShotBehaviour extends OneShotBehaviour {

    private int step = 0;
    private Object[] args;
    private SigletonNtxuvaBoard game;
    
    public MyOneShotBehaviour(Agent agent, SigletonNtxuvaBoard game){
        myAgent = agent;
        args = myAgent.getArguments();
        this.game = game;
    }

    @Override
    public void action() {

        System.out.println("entrou " + (args.length > 0 ? args[0].toString() : "o") + " one shot");
        if (args.length > 0) {
            game.ntxuva.turn = args[0].toString().charAt(0) == 'o' ? 'x' : 'o';
        } else {
            game.ntxuva.turn = 'x';
        }

        System.out.println(game.ntxuva.toString());
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("agent" + (args.length > 0 ? (args[0].toString().charAt(0) == 'o' ? 'x' : 'o') : 'x'), AID.ISLOCALNAME));
        msg.setLanguage("English");
        msg.setContent(args.length > 0 ? (args[0].toString().equals("x") ? "TurnO" : "TurnX") : "TurnO");
        myAgent.send(msg);
    }

}
