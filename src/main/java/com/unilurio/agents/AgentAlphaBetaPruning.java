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

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author DNIC2012
 */
public class AgentAlphaBetaPruning extends Agent {

//    private SigletonNtxuvaGui myGui = SigletonNtxuvaGui.getInstance();
    public SigletonNtxuvaBoard game = SigletonNtxuvaBoard.getInstance();
    private String p;

    protected void setup() {

//        myGui.showGui();
//        System.out.println("Agent X - MiniMax");
        Object[] args = getArguments();

        if ((args.length > 1) && (!args[0].toString().equals("x") || !args[0].toString().equals("o"))) {
            System.out.println("Error:  Invalid arguments! ");
            return;
        }

        p = args[0].toString();

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

        System.out.println(AgentAlphaBetaPruning.class.getSimpleName() + "utility:" + u);

        BufferedWriter bw = null;
        FileWriter fw = null;
        if (u > 0) {
            if (p.equals("x")) {
                String content = AgentAlphaBetaPruning.class.getSimpleName() + " X ganhou";
                System.out.println(content);
                writeResult(AgentAlphaBetaPruning.class.getSimpleName()+"\n");
            }
        } else {
            if (p.equals("o")) {
                String content = AgentAlphaBetaPruning.class.getSimpleName() + " O ganhou";
                System.out.println(content);
               writeResult(AgentAlphaBetaPruning.class.getSimpleName()+"\n");
            }
        }
        System.out.println("" + getAID().getName() + " terminating.");
    }

    private void writeResult(String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("results.txt", true);
            fw.write(content);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
