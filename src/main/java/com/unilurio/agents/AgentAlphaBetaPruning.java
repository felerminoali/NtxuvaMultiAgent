/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import com.unilurio.behaviours.MyOneShotBehaviour;
import com.unilurio.behaviours.MyTwoStepBehaviours;
import jade.core.Agent;
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
        System.out.println("Agent " + (args.length > 0 ? args[0].toString() : "x") + " - AlphaBetaPruning");
        System.out.println(game.ntxuva.toString());
        addBehaviour(new MyOneShotBehaviour(this, game));
        addBehaviour(new MyTwoStepBehaviours(this, game, new AlphaBetaPrunningPlayer()));
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
