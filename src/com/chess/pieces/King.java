package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public final class King extends Piece {

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

    public King(final int rowId, final int colId, final Color color) {
        super(PieceType.KING, rowId, colId, color);
    }

    @Override
    public ArrayList<Move> calculateLegalMoves(final Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for(int i = 0;i < legalMovesDirections.length;++i) {
            final int newRowId = this.rowId + legalMovesDirections[i][0];
            final int newColId = this.colId + legalMovesDirections[i][1];

            if(Util.isValideCoordonate(newRowId, newColId)) {

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
        return true;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "K" : "k";
    }

    @Override
    public King makePiece(final int row, final int col, final Color color) {
        return new King(row, col, color);
    }
}