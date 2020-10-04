package com.chess.board;

import com.chess.pieces.Piece;

public abstract class Tile {

    protected final int rowId;
    protected final int colId;

    public Tile(final int rowId, final int colId) {
        this.rowId = rowId;
        this.colId = colId;
    }

    public abstract boolean isEmptyTile();
    public abstract Piece getPieceOnTile();

    public static final class EmptyTile extends Tile {

        public EmptyTile(final int rowId, final int colId) {
            super(rowId, colId);
        }

        @Override
        public boolean isEmptyTile() {
            return true;
        }

        @Override
        public Piece getPieceOnTile() {
            return null;
        }

        @Override
        public String toString() {
            return "-";
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        public OccupiedTile(final int rowId, final int colId, final Piece pieceOnTile) {
            super(rowId, colId);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isEmptyTile() {
            return false;
        }

        @Override
        public Piece getPieceOnTile() {
            return this.pieceOnTile;
        }

        @Override
        public String toString() {
            return this.pieceOnTile.toString();
        }
    }
}