package com.chess.player;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;
import com.chess.pieces.King;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King king;
    protected final List<Move> whiteLegalMoves;
    protected final List<Move> blackLegalMoves;

    public Player(final Board board, final King king, final List<Move> whiteLegalMoves,
                  final List<Move> blackLegalMoves) {
        this.board = board;
        this.king = king;
        this.whiteLegalMoves = whiteLegalMoves;
        this.blackLegalMoves = blackLegalMoves;
    }

    public abstract boolean isInCheck();
    public abstract Color getPlayerColor();
    public abstract  boolean isInCheckMate();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public static class WhitePlayer extends Player {

        public WhitePlayer(final Board board, final King king, final List<Move> whiteLegalMoves,
                           final List<Move> blackLegalMoves) {
            super(board, king, whiteLegalMoves, blackLegalMoves);
        }

        @Override
        public boolean isInCheck() {

            for(Move move : blackLegalMoves)
                if(move.getDestinationRow() == king.getRowId() &&
                        move.getDestinationCol() == king.getColId())
                    return true;

            return false;
        }

        @Override
        public Color getPlayerColor() {
            return Color.WHITE;
        }

        @Override
        public boolean isInCheckMate() {

            if(!isInCheck())
                return false;

            final ArrayList<Move> kingLegals = king.calculateLegalMoves(this.board);

            boolean hasEscapeMoves = false;
            for(Move move : kingLegals) {

                boolean isAttacked = false;
                for(Move attackMove : blackLegalMoves) {
                    if(attackMove.getDestinationRow() == move.getDestinationRow() &&
                            attackMove.getDestinationCol() == move.getDestinationCol()) {
                        isAttacked = true;
                        break;
                    }
                }

                if(!isAttacked) {
                    hasEscapeMoves = true;
                    break;
                }
            }

            return !hasEscapeMoves;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    }

    public static class BlackPlayer extends Player {

        public BlackPlayer(final Board board, final King king, final List<Move> whiteLegalMoves,
                           final List<Move> blackLegalMoves) {
            super(board, king, whiteLegalMoves, blackLegalMoves);
        }

        @Override
        public boolean isInCheck() {

            for(final Move move : whiteLegalMoves)
                if(move.getDestinationRow() == king.getRowId() &&
                        move.getDestinationCol() == king.getColId())
                    return true;

            return false;
        }

        @Override
        public Color getPlayerColor() {
            return Color.BLACK;
        }

        @Override
        public boolean isInCheckMate() {

            if(!isInCheck())
                return false;

            final ArrayList<Move> kingLegals = this.king.calculateLegalMoves(this.board);

            boolean hasEscapeMoves = false;
            for(Move move : kingLegals) {

                boolean isAttacked = false;
                for(Move attackMove : whiteLegalMoves) {
                    if(attackMove.getDestinationRow() == move.getDestinationRow() &&
                            attackMove.getDestinationCol() == move.getDestinationCol()) {
                        isAttacked = true;
                        break;
                    }
                }

                if(!isAttacked) {
                    hasEscapeMoves = true;
                    break;
                }
            }

            return !hasEscapeMoves;
        }


        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    }
}
