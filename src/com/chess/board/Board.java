package com.chess.board;

import com.chess.Color;
import com.chess.board.Tile.OccupiedTile;
import com.chess.pieces.*;
import com.chess.player.Player;
import com.chess.player.Player.BlackPlayer;
import com.chess.player.Player.WhitePlayer;

import java.util.ArrayList;

import static com.chess.board.Tile.*;

public final class Board {

    private final Tile[][] config;
    private final PlayerType playerToTurn;

    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;

    private final ArrayList<Move> whitePlayerLegalMoves;
    private final ArrayList<Move> blackPlayerLegalMoves;

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
        this.whitePlayerLegalMoves = calculateLegalMoves(whitePieces, Color.WHITE);
        this.blackPlayerLegalMoves = calculateLegalMoves(blackPieces, Color.BLACK);

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

    public ArrayList<Move> getCurrentPlayerLegalMoves() {
        return (this.playerToTurn == PlayerType.WHITE ? this.whitePlayerLegalMoves : this.blackPlayerLegalMoves);
    }

    public Tile[][] getTiles() {
        return this.config;
    }

    private King getKing(final Color color) {

        if( config[7][4].getPieceOnTile() != null && color.isWhite() && config[7][4].getPieceOnTile().isKing() &&
                config[7][4].getPieceOnTile().getColor().isWhite())
            return (King) config[7][4].getPieceOnTile();
        else if( config[0][4].getPieceOnTile() != null && color.isBlack() && config[0][4].getPieceOnTile().isKing() &&
                config[0][4].getPieceOnTile().getColor().isBlack())
            return (King) config[0][4].getPieceOnTile();

        for(int i = 0;i < Util.BOARD_ROWS;++i)
            for(int j = 0;j < Util.BOARD_COLS;++j)
                if(!config[i][j].isEmptyTile() && config[i][j].getPieceOnTile().getColor() == color &&
                        config[i][j].getPieceOnTile().isKing())
                    return (King) config[i][j].getPieceOnTile();

        return null;
    }

    private ArrayList<Move> calculateLegalMoves(final ArrayList<Piece> pieces, final Color color) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for(Piece piece : pieces) {
            ArrayList<Move> pieceLegals = piece.calculateLegalMoves(this);
            legalMoves.addAll(pieceLegals);
        }

        return legalMoves;
    }

    public ArrayList<Piece> getAllPieces() {
        final ArrayList<Piece> pieces = getPieces(Color.WHITE);
        pieces.addAll(getPieces(Color.BLACK));

        return pieces;
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

    public static Board createStandardBoard() {
        Tile config[][] = new Tile[Util.BOARD_ROWS][Util.BOARD_COLS];

        config[0][0] = new OccupiedTile(0, 0, new Rook(0,0, Color.BLACK));
        config[0][1] = new OccupiedTile(0, 1, new Knight(0,1, Color.BLACK));
        config[0][2] = new OccupiedTile(0, 2, new Bishop(0, 2, Color.BLACK));
        config[0][3] = new OccupiedTile(0, 3, new Queen(0, 3, Color.BLACK));
        config[0][4] = new OccupiedTile(0, 4, new King(0, 4, Color.BLACK));
        config[0][5] = new OccupiedTile(0, 5, new Bishop(0, 5, Color.BLACK));
        config[0][6] = new OccupiedTile(0, 6, new Knight(0,6, Color.BLACK));
        config[0][7] = new OccupiedTile(0, 7, new Rook(0,7, Color.BLACK));

        for(int i = 0;i < Util.BOARD_COLS;++i) {
            config[1][i] = new OccupiedTile(1, i, new Pawn(1, i, Color.BLACK));
            config[6][i] = new OccupiedTile(6, i, new Pawn(6, i, Color.WHITE));
        }

        for(int i = 2;i < 6;++i)
            for(int j = 0; j < Util.BOARD_COLS;++j)
                config[i][j] = new EmptyTile(i, j);

        config[7][0] = new OccupiedTile(7, 0, new Rook(7, 0, Color.WHITE));
        config[7][1] = new OccupiedTile(7,1, new Knight(7, 1, Color.WHITE));
        config[7][2] = new OccupiedTile(7, 2, new Bishop(7,2, Color.WHITE));
        config[7][3] = new OccupiedTile(7, 3, new Queen(7, 3, Color.WHITE));
        config[7][4] = new OccupiedTile(7, 4, new King(7, 4, Color.WHITE));
        config[7][5] = new OccupiedTile(7, 5, new Bishop(7,5, Color.WHITE));
        config[7][6] = new OccupiedTile(7,6, new Knight(7, 6, Color.WHITE));
        config[7][7] = new OccupiedTile(7, 7, new Rook(7, 7, Color.WHITE));

        return new Board(config, PlayerType.WHITE);
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