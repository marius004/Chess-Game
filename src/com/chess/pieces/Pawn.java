package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public final class Pawn extends Piece {

    public Pawn(final int rowId, final int colId, final Color color) {
        super(PieceType.PAWN, rowId, colId, color);
    }

    @Override
    public ArrayList<Move> calculateLegalMoves(final Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        // jump 2 blocks
        if(this.color == Color.WHITE && this.rowId == 6 &&
                board.getPiece(rowId - 2, colId) == null &&
                board.getPiece(rowId - 1, colId) == null)
            legalMoves.add( new Move(board, this, rowId - 2, colId));
        else if(this.color == Color.BLACK && this.rowId == 1 &&
                board.getPiece(rowId + 2, colId) == null &&
                board.getPiece(rowId + 1, colId) == null)
            legalMoves.add( new Move(board,this, rowId + 2, colId));

        // attack move
        if(this.color == Color.WHITE) {
            if(Util.isValideCoordonate(this.rowId - 1, this.colId - 1) &&
                    board.getPiece(this.rowId - 1, this.colId - 1) != null &&
                    board.getPiece(this.rowId - 1, this.colId - 1).getColor() != this.color)
                legalMoves.add( new Move(board,this, this.rowId - 1, this.colId - 1, board.getPiece(this.rowId - 1, this.colId - 1) ));
            if (Util.isValideCoordonate(this.rowId - 1, this.colId + 1) &&
                    board.getPiece(this.rowId - 1, this.colId + 1) != null &&
                    board.getPiece(this.rowId - 1, this.colId + 1).getColor() != this.color)
                legalMoves.add( new Move(board,this, this.rowId - 1, this.colId + 1, board.getPiece(this.rowId - 1, this.colId + 1) ));
        }else {
            if(Util.isValideCoordonate(this.rowId + 1, this.colId - 1) &&
                    board.getPiece(this.rowId + 1, this.colId - 1) != null &&
                    board.getPiece(this.rowId + 1, this.colId - 1).getColor() != this.color)
                legalMoves.add( new Move(board,this, this.rowId + 1, this.colId - 1, board.getPiece(this.rowId + 1, this.colId - 1) ));
            if (Util.isValideCoordonate(this.rowId + 1, this.colId + 1) &&
                    board.getPiece(this.rowId + 1, this.colId + 1) != null &&
                    board.getPiece(this.rowId + 1, this.colId + 1).getColor() != this.color)
                legalMoves.add( new Move(board,this, this.rowId + 1, this.colId + 1, board.getPiece(this.rowId + 1, this.colId + 1) ));
        }

        // advance move
        if(this.color == Color.WHITE &&
                Util.isValideCoordonate(this.rowId - 1, this.colId) &&
                board.getPiece(this.rowId - 1, this.colId) == null)
            legalMoves.add( new Move(board, this, this.rowId - 1, this.colId));
        else if (this.color == Color.BLACK && Util.isValideCoordonate(this.rowId + 1, this.colId) &&
                board.getPiece(this.rowId + 1, this.colId) == null)
            legalMoves.add( new Move(board, this, this.rowId + 1, this.colId));

        return legalMoves;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public Pawn makePiece(final int row, final int col, final Color color) {
        return new Pawn(row, col, color);
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "P" : "p";
    }
}
