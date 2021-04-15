package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public final class Queen extends Piece {

    public static int[][] movingDirections = {
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
                }else {
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
        return this.color == Color.WHITE ? "Q" : "q";
    }

    @Override
    public Queen makePiece(final int row, final int col, final Color color) {
        return new Queen(row, col, color);
    }
}