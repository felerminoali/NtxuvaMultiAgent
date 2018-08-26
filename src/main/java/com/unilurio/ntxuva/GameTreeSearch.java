/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.ntxuva;

import java.util.ArrayList;

/**
 *
 * @author felermino
 */
public class GameTreeSearch {

    
    Player search;

    public GameTreeSearch(Player search) {
        this.search = search;
    }
    
    public Position getBestMove(Ntxuva ntxuva){
        return search.decisao_minimax(ntxuva);
    }
    
    public int utilidade(Ntxuva ntxuva){
     return search.utilidade(ntxuva);
    }

}
