package com.chess.board;

import com.chess.player.Player;
import com.chess.player.Player.BlackPlayer;
import com.chess.player.Player.WhitePlayer;

public enum PlayerType {

    WHITE{

        @Override
        public PlayerType chooseOpponent() {
            return PlayerType.BLACK;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public Player chooseOpponent(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    },
    BLACK {

        @Override
        public PlayerType chooseOpponent() {
            return PlayerType.WHITE;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public Player chooseOpponent(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

    };

    public abstract PlayerType chooseOpponent();
    public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
    public abstract Player chooseOpponent(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
}