package com.chess.board;

import com.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class LegalMovesCalculator {

    public static List<Move> calculate(Board board, List<Piece> pieces) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for(Piece piece : pieces) {
            ArrayList<Move> pieceLegals = piece.calculateLegalMoves(board);
            legalMoves.addAll(pieceLegals);
        }

        return legalMoves;
    }

}
