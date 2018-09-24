/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import com.unilurio.behaviours.MyOneShotBehaviour;
import com.unilurio.behaviours.MyTwoStepBehaviours;
import com.unilurio.ntxuva.GameTreeSearch;

import com.unilurio.ntxuva.RandomPlayer;

import jade.core.Agent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author user
 */
public class AgentRadom extends Agent {

    private SigletonNtxuvaBoard game = SigletonNtxuvaBoard.getInstance();
    private String p;

    @Override
    protected void setup() {

        Object[] args = getArguments();

        if ((args.length > 1) && (!args[0].toString().equals("x") || !args[0].toString().equals("o"))) {
            System.out.println("Error:  Invalid arguments! ");
            return;
        }

        p = args[0].toString();
        System.out.println(args.length > 0 ? args[0].toString() : "");

        System.out.println("Agent " + (args.length > 0 ? args[0].toString() : "o") + "- Random");
        System.out.println(game.ntxuva.toString());

        addBehaviour(new MyOneShotBehaviour(this, game));
        addBehaviour(new MyTwoStepBehaviours(this, game, new RandomPlayer()));
    }

    @Override
    protected void takeDown() {

        int u = new GameTreeSearch(new RandomPlayer()).utilidade(game.ntxuva);
        System.out.println(RandomPlayer.class.getSimpleName() + " utility:" + u);

        BufferedWriter bw = null;
        FileWriter fw = null;
        if (u > 0) {
            if (p.equals("x")) {
                System.out.println(RandomPlayer.class.getSimpleName() + "X ganhou");
                writeResult(RandomPlayer.class.getSimpleName()+"\n");
            }
        } else {
            if (p.equals("o")) {
                System.out.println(RandomPlayer.class.getSimpleName() + "O ganhou");
                writeResult(RandomPlayer.class.getSimpleName()+"\n");
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
