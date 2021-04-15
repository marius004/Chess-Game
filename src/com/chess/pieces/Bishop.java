package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public final class Bishop extends Piece {

    public static int[][] movingDirections = {
            { +1, +1 },
            { +1, -1 },
            { -1, +1 },
            { -1, -1 }
    };

    public Bishop(final int rowId, final int colId, final Color color) {
        super(PieceType.BISHOP, rowId, colId, color);
    }

    @Override
    public ArrayList<Move> calculateLegalMoves(final Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for(int i = 0; i < movingDirections.length; ++i) {

            int newRowId = this.rowId + movingDirections[i][0];
            int newColId = this.colId + movingDirections[i][1];

            while(Util.isValidCoordinate(newRowId, newColId)) {

                // there is no piece at the newly created coordinates
                if(board.getPiece(newRowId, newColId) == null)
                    legalMoves.add(new Move(board, this, newRowId, newColId));
                else if(board.getPiece(newRowId, newColId).getColor() != this.color) {
                    legalMoves.add(new Move(board, this, newRowId, newColId, board.getPiece(newRowId, newColId)));
                    break;
                } else {
                    break;
                }

                newRowId += movingDirections[i][0];
                newColId += movingDirections[i][1];
            }
        }

        return legalMoves;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "B" : "b";
    }

    @Override
    public Bishop makePiece(final int row, final int col, final Color color) {
        return new Bishop(row, col, color);
    }
}