/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import com.unilurio.behaviours.MyOneShotBehaviour;
import com.unilurio.behaviours.MyTwoStepBehaviours;
import com.unilurio.ntxuva.GameTreeSearch;
import com.unilurio.ntxuva.MiniMaxPlayer;
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
        
        
        
        addBehaviour(new MyOneShotBehaviour(this, game));
        addBehaviour(new MyTwoStepBehaviours(this, game, new RandomPlayer()));
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
