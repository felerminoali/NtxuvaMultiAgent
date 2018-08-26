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
abstract class SearchParent {
    
    public boolean teste_terminal(Ntxuva ntxuva) {
        return false;
    }
    
    /*
   * Fim de jogo?
   * O jogo so termina se nao houver mais espaco para jogadas...
     */
    
    public ArrayList<Sucessor> possibleMoves(Ntxuva ntxuva, char turn) {

        ArrayList<Sucessor> suc = new ArrayList<>();

        int startRow = (turn == 'x') ? Ntxuva.ROW_ZERO : Ntxuva.ROW_TWO;
        int finishRow = (turn == 'x') ? Ntxuva.ROW_TWO : Ntxuva.ROWS;

        if (ntxuva.moreThanOnePiece(turn)) {
            for (int i = startRow; i < finishRow; i++) {
                for (int j = 0; j < Ntxuva.COLUMNS; j++) {
                    if (ntxuva.board[i][j] > 1) {

                        if (!ntxuva.isNeverEndingMove(new Position(i, j))) {
                            Position p = new Position(i, j);
                            Ntxuva move = ntxuva.move(p);
                            suc.add(new Sucessor(move, p));
                        } else {
//                            System.out.println("=========Cyclte ========");
//                            System.out.println(ntxuva);
//                            System.out.println("P: " + new Position(i, j));
//                            System.out.println("=================");

                        }

                    }
                }
            }
        } else {
            for (int i = startRow; i < finishRow; i++) {
                for (int j = 0; j < Ntxuva.COLUMNS; j++) {
                    if (ntxuva.board[i][j] != 0) {

                        Position next = new Position(i, j).moveAntiClockWise();
                        if (ntxuva.board[next.row][next.column] == 0) {
                            Position p = new Position(i, j);
                            Ntxuva move = ntxuva.move(p);

                            suc.add(new Sucessor(move, p));
                        }
                    }
                }
            }
        }
        return suc;

    }
    
     public int contaPecas(Ntxuva ntxuva, char turn) {
        return ntxuva.sumPieces(turn);
    }
     
    public abstract Position decisao_minimax(Ntxuva ntuxa);
     public abstract  int utilidade(Ntxuva ntxuva);
    
}
