package com.chess;

import com.chess.board.PlayerType;

/// this enum is used to represent the color of a piece
public enum Color {

    WHITE {

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean canTurn(PlayerType playerType) {
            return playerType == PlayerType.WHITE;
        }

    }, BLACK {

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean canTurn(PlayerType playerType) {
            return playerType == PlayerType.BLACK;
        }
    };

    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract boolean canTurn(PlayerType playerType);
}
