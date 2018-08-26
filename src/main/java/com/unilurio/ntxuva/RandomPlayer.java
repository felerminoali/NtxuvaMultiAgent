/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.ntxuva;

import static com.unilurio.ntxuva.AlphaBetaPrunningPlayer.sucessoresAlpha;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author user
 */
public class RandomPlayer extends SearchParent {

    static ArrayList<Sucessor> sucessoresRandom = new ArrayList<>();
    private char turn;

    @Override
    public Position decisao_minimax(Ntxuva ntuxa) {
        turn = ntuxa.turn;
        sucessoresAlpha.clear();
        sucessoresRandom = possibleMoves(ntuxa, (turn == 'o') ? 'o' : 'x');
        
        sucessoresRandom.forEach((sucessor) -> {
            System.out.println(sucessor.position);
        });
        int index = getRandomIndex(0, sucessoresRandom.size() - 1);
        return sucessoresRandom.get(index).position;
    }

    @Override
    public int utilidade(Ntxuva ntxuva) {
        int pc, usr;

        pc = contaPecas(ntxuva, 'o');
        usr = contaPecas(ntxuva, 'x');

        return (usr - pc);
    }

    private int getRandomIndex(int min, int max) {
//        if (min >= max) {
//            throw new IllegalArgumentException("Max must be grater than min");
//        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
