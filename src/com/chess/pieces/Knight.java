package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public final class Knight extends Piece {

    public static int[][] movingDirections = {
            { +2, +1 },
            { +2, -1 },
            { -2, -1 },
            { -2, +1 },
            { +1, +2 },
            { +1, -2 },
            { -1, -2 },
            { -1, +2 }
    };

    public Knight(final int rowId, final int colId, final Color color) {
        super(PieceType.KNIGHT, rowId, colId, color);
    }

    @Override
    public ArrayList<Move> calculateLegalMoves(final Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for(int i = 0; i < movingDirections.length; ++i) {
            final int newRowId = this.rowId + movingDirections[i][0];
            final int newColId = this.colId + movingDirections[i][1];

            if(Util.isValidCoordinate(newRowId, newColId)) {

                if(board.getPiece(newRowId, newColId) == null) {
                    legalMoves.add(new Move(board ,this, newRowId, newColId));
                } else {
                    final Piece pieceAtDestination = board.getPiece(newRowId, newColId);

                    if(pieceAtDestination.getColor() != this.color)
                        legalMoves.add(new Move(board, this, newRowId, newColId, pieceAtDestination));
                }
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
        return this.color == Color.WHITE ? "N" : "n";
    }

    @Override
    public Knight makePiece(final int row, final int col, final Color color) {
        return new Knight(row, col, color);
    }
}