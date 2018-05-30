/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio.teste;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author DNIC2012
 */
public class SigletonTestGui {

    JFrame frame;
    private static SigletonTestGui uniqueInstance;

    public SigletonTestGui() {

        frame = new JFrame("Ntxuva");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 1));

        btn = new JButton("Button");
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setBackground(Color.CYAN);
        btn.setActionCommand("btn_" + 0 + "_" + 0);
        btn.addActionListener(null);
        btn.setText("Button");

        frame.add(btn);
       

    }

    public void showGui() {
        frame.pack();
        frame.setVisible(true);
    }

    public static synchronized SigletonTestGui getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SigletonTestGui();
        }

        return uniqueInstance;
    }
    
    public JButton btn;
    
    
    
}
