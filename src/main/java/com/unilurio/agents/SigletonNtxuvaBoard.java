/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.agents;

import com.unilurio.ntxuva.Ntxuva;

/**
 *
 * @author DNIC2012
 */
public class SigletonNtxuvaBoard {

    public Ntxuva ntxuva;

    public static SigletonNtxuvaBoard uniqueInstance;

    public SigletonNtxuvaBoard() {
        ntxuva = new Ntxuva();
    }

    public static synchronized SigletonNtxuvaBoard getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SigletonNtxuvaBoard();
        }

        return uniqueInstance;
    }

    public static synchronized SigletonNtxuvaBoard reset() {
        return new SigletonNtxuvaBoard();
    }

}
