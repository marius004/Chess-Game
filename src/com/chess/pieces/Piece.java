package com.chess.pieces;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.ArrayList;

public abstract class Piece {

    protected final int rowId;
    protected final int colId;
    protected final Color color;
    protected final PieceType pieceType;

    public Piece(final PieceType pieceType,final int rowId, final int colId, final Color color) {
        this.pieceType = pieceType;
        this.rowId = rowId;
        this.colId = colId;
        this.color = color;
    }

    public abstract ArrayList<Move> calculateLegalMoves(final Board board);

    public abstract boolean isKing();

    public abstract Piece makePiece(final int row, final int col, final Color color);

    public int getRowId() {
        return this.rowId;
    }

    public int getColId() {
        return this.colId;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Piece))
            return false;

        if(this == obj)
            return true;

        final Piece piece = (Piece) obj;
        return piece.rowId == this.rowId && piece.colId == this.colId && piece.color == this.color && this.pieceType == piece.pieceType;
    }
}
