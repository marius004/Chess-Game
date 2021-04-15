package com.chess.board;

import com.chess.Color;
import com.chess.pieces.*;
import com.chess.player.Player;
import com.chess.player.Player.BlackPlayer;
import com.chess.player.Player.WhitePlayer;

import java.util.ArrayList;
import java.util.List;

public final class Board {

    private final Tile[][] config;
    private final PlayerType playerToTurn;

    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;

    private final List<Move> whitePlayerLegalMoves;
    private final List<Move> blackPlayerLegalMoves;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    public Board(final Tile[][] config, final PlayerType playerToTurn) {
        this.config = config;
        this.playerToTurn = playerToTurn;

        // get the white and black pieces on the table
        this.whitePieces = getPieces(Color.WHITE);
        this.blackPieces = getPieces(Color.BLACK);

        // get the white and black players' legal moves
        this.whitePlayerLegalMoves = LegalMovesCalculator.calculate(this, whitePieces);
        this.blackPlayerLegalMoves = LegalMovesCalculator.calculate(this, blackPieces);

        // players
        this.whitePlayer = new WhitePlayer(this, getKing(Color.WHITE), whitePlayerLegalMoves, blackPlayerLegalMoves);
        this.blackPlayer = new BlackPlayer(this, getKing(Color.BLACK), whitePlayerLegalMoves, blackPlayerLegalMoves);

        currentPlayer = playerToTurn.choosePlayer(whitePlayer, blackPlayer);
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Tile getTile(final int rowId, final int colId) {
        return config[rowId][colId];
    }

    public Player getOpponentPlayer() {
        return (this.playerToTurn == PlayerType.WHITE ? blackPlayer : whitePlayer);
    }

    public List<Move> getCurrentPlayerLegalMoves() {
        return (this.playerToTurn == PlayerType.WHITE ? this.whitePlayerLegalMoves : this.blackPlayerLegalMoves);
    }

    public Tile[][] getTiles() {
        return this.config;
    }

    private King getKing(final Color color) {

        for(int i = 0;i < Util.BOARD_ROWS;++i)
            for(int j = 0;j < Util.BOARD_COLS;++j)
                if(!config[i][j].isEmptyTile() && config[i][j].getPieceOnTile().getColor() == color &&
                        config[i][j].getPieceOnTile().isKing())
                    return (King) config[i][j].getPieceOnTile();

        return null;
    }

    private ArrayList<Piece> getPieces(final Color color) {
        final ArrayList<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < Util.BOARD_ROWS; ++i)
            for (int j = 0; j < Util.BOARD_COLS; ++j)
                if (this.config[i][j].getPieceOnTile() != null && this.config[i][j].getPieceOnTile().getColor() == color)
                    pieces.add(this.config[i][j].getPieceOnTile());

        return pieces;
    }

    public Piece getPiece(final int rowId, final int colId) {
        return this.config[rowId][colId].getPieceOnTile();
    }

    @Override
    public String toString() {

        String s = "";
        for(int i = 0;i < Util.BOARD_ROWS;++i) {
            for (int j = 0; j < Util.BOARD_COLS; ++j)
                s += config[i][j].toString();
            s += "\n";
        }

        return s;
    }

    public Tile[][] getConfig() {
        return this.config;
    }

    public PlayerType getPlayerToTurn() {
        return this.playerToTurn;
    }
}