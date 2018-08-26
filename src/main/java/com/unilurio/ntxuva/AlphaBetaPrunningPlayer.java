/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.ntxuva;

import java.util.ArrayList;

/**
 *
 * @author DNIC2012
 */
public class AlphaBetaPrunningPlayer extends Player {

    static ArrayList<Sucessor> sucessoresAlpha = new ArrayList<>();
    int maxProf = 8;
    private char turn;

    @Override
    //    public int[][] decisao_minimax(int[][] tab) {
    public Position decisao_minimax(Ntxuva ntuxa) {

        turn = ntuxa.turn;
        /*
     * Limpa os sucessores
         */
        sucessoresAlpha.clear();

        /*
     * Recebe a utilidade mÃ¡xima
         */
        int v = valor_max(ntuxa, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 1);

        /*
     * Percorre a lista em busca do primeiro sucessor com utilidade maxima
         */
        for (Sucessor s : sucessoresAlpha) {
            if (s.utilidade == v) {
                return s.position;
            }
        }

        return null;

    }

    public int valor_max(Ntxuva ntxuva, int alfa, int beta, boolean prim, int prof) {
        /*
     * Se o jogo acabou, retorna a utilidade
         */
        if (teste_terminal(ntxuva) || prof++ > maxProf) {
            return utilidade(ntxuva);
        }

        /*
     * Atribui -Infinito
         */
        int v = Integer.MIN_VALUE;

        /*
     * Percorre os nos sucessores de MAX
         */
        for (Sucessor s : possibleMoves(ntxuva, (turn == 'o') ? 'o' : 'x')) {

            v = Math.max(v, valor_min(s.tabuleiro, alfa, beta, prof));
            s.utilidade = v;

            /*
       * Se forem os primeiros sucessores, adiciona na lista de sucessores...
             */
            if (prim) {
                sucessoresAlpha.add(s);
            }

            /*
       * Poda Beta - Se o valor for maior que beta, retorna o valor..
             */
            if (v >= beta) {
                return v;
            }

            /*
       * Se o valor for maior que Alfa, Alfa recebe o valor...
             */
            alfa = Math.max(alfa, v);
        }

        return v;
    }

    public int valor_min(Ntxuva ntxuva, int alfa, int beta, int prof) {
        /*
     * Se o jogo acabou, retorna a utilidade
         */
        if (teste_terminal(ntxuva) || prof++ > maxProf) {
            return utilidade(ntxuva);
        }

        /*
     * Atribui +Infinito
         */
        int v = Integer.MAX_VALUE;

        /*
     * Percorre os nos sucessores de MIN
         */
        for (Sucessor s : possibleMoves(ntxuva, (turn == 'o') ? 'x' : 'o')) {
            v = Math.min(v, valor_max(s.tabuleiro, alfa, beta, false, prof));
            s.utilidade = v;

            /*
       * Poda Alfa - Se o valor for menor que alfa, retorna o valor...
             */
            if (v <= alfa) {
                return v;
            }

            /*
       * Se valor menor que Beta, Beta o recebe...
             */
            beta = Math.min(beta, v);
        }

        return v;
    }

    /*
   * Retorna a utilidade...
   * Aqui a utilidade considerada e a diferenca de pontos entre o computador e
   * o jogador, o computador nao deseja apenas vencer, mas tambem humilhar =P
     */
    public int utilidade(Ntxuva ntxuva) {
        int pc, usr;

        pc = contaPecas(ntxuva, 'o');
        usr = contaPecas(ntxuva, 'x');

        //return (pc - usr);
         return (usr - pc);
    }

}