/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.ntxuva;

import java.util.ArrayList;
import static com.unilurio.ntxuva.GameTreeSearch.sucessores;

/**
 *
 * @author DNIC2012
 */
public interface Search {
    Position decisao_minimax(Ntxuva ntuxa);
    public int utilidade(Ntxuva ntxuva);
}



