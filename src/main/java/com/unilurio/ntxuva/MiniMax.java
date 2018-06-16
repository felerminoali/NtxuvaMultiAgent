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
public class MiniMax extends SearchParent{

    static ArrayList<Sucessor> sucessores = new ArrayList<>();
    char turn;
    int maxProf = 5;
    @Override
    public Position decisao_minimax(Ntxuva ntuxa) {
        
        turn = ntuxa.turn;
        /*
     * Limpa os sucessores
         */
        sucessores.clear();

        /*
     * Recebe a utilidade mÃ¡xima
         */
        int v = valor_max(ntuxa, true, 1);

        /*
     * Percorre a lista em busca do primeiro sucessor com utilidade maxima
         */
        for (Sucessor s : sucessores) {
            if (s.utilidade == v) {
                return s.position;
            }
        }
        
        return null;
        
    }

    public int valor_max(Ntxuva ntxuva, boolean prim, int prof) {
        
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

            v = Math.max(v, valor_min(s.tabuleiro, prof));
            s.utilidade = v;

            /*
       * Se forem os primeiros sucessores, adiciona na lista de sucessores...
             */
            if (prim) {
                sucessores.add(s);
            }
        }
        return v;
    }
    
    public int valor_min(Ntxuva ntxuva, int prof) {
        
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
            v = Math.min(v, valor_max(s.tabuleiro, false, prof));
            s.utilidade = v;
        }

        return v;
    }
    
    @Override
    public int utilidade(Ntxuva ntxuva) {
        
        if(contaPecas(ntxuva, 'o') == 0){
            return -1;
        } if(contaPecas(ntxuva, 'x') == 0){
            return 1;
        }else{
            return 0;
        }
    }
    
    
    
    
    
}
