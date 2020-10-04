package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public final class Queen extends Piece {

    public static int[][] legalMovesDirections = {
            { +1, +1 },
            { +1, -1 },
            { -1, +1 },
            { -1, -1 },
            { +1, +0 },
            { -1, +0 },
            { +0, +1 },
            { +0, -1 }
    };

    public Queen(final int rowId, final int colId, final Color color) {
        super(PieceType.QUEEN, rowId, colId, color);
    }

    @Override
    public ArrayList<Move> calculateLegalMoves(final Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for(int i = 0;i < legalMovesDirections.length;++i) {

            int newRowId = this.rowId + legalMovesDirections[i][0];
            int newColId = this.colId + legalMovesDirections[i][1];

            while(Util.isValideCoordonate(newRowId, newColId)) {

                // there is no piece at the newly created coordonates
                if(board.getPiece(newRowId, newColId) == null)
                    legalMoves.add(new Move(board, this, newRowId, newColId));
                else if(board.getPiece(newRowId, newColId).getColor() != this.color) {
                    legalMoves.add(new Move(board, this, newRowId, newColId, board.getPiece(newRowId, newColId)));
                    break;
                }else {
                    break;
                }

                newRowId += legalMovesDirections[i][0];
                newColId += legalMovesDirections[i][1];
            }
        }

        return legalMoves;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public Queen makePiece(final int row, final int col, final Color color) {
        return new Queen(row, col, color);
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "Q" : "q";
    }
}