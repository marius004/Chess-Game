package com.chess.board;

import com.chess.board.Tile.OccupiedTile;
import com.chess.pieces.Piece;
import com.chess.pieces.Util;

import static com.chess.board.Tile.*;

public final class Move {

    private final Board board;
    private final Piece movedPiece;
    private final int destinationRow;
    private final int destinationCol;
    private final Piece pieceAttacked;

    public Move(final Board board, final Piece movedPiece, final int destinationRow,
                final int destinationCol) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationRow = destinationRow;
        this.destinationCol = destinationCol;
        this.pieceAttacked = null;
    }

    public Move(final Board board ,final Piece movedPiece, final int destinationRow,
                final int destinationCol,final Piece pieceAttacked) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationRow = destinationRow;
        this.destinationCol = destinationCol;
        this.pieceAttacked = pieceAttacked;
    }

    public Piece getPieceAttacked() {
        return this.pieceAttacked;
    }

    public int getDestinationRow() {
        return this.destinationRow;
    }

    public int getDestinationCol() {
        return this.destinationCol;
    }

    // return the initial row of the moved piece
    public int getRowId() {
        return this.movedPiece.getRowId();
    }

    // return the initial col of the moved piece
    public int getColId() {
        return this.movedPiece.getColId();
    }

    public Board createBoard() {
        Tile[][] newBoardConfiguration = new Tile[Util.BOARD_ROWS][Util.BOARD_COLS];

        final Tile[][] config = this.board.getConfig();

        for (int i = 0; i < Util.BOARD_ROWS; ++i)
            for (int j = 0; j < Util.BOARD_COLS; ++j)
                if (config[i][j].isEmptyTile())
                    newBoardConfiguration[i][j] = config[i][j];
                else if(!config[i][j].getPieceOnTile().equals(movedPiece))
                    newBoardConfiguration[i][j] = config[i][j];

        newBoardConfiguration[movedPiece.getRowId()][movedPiece.getColId()] = new EmptyTile(movedPiece.getRowId(), movedPiece.getColId());
        newBoardConfiguration[destinationRow][destinationCol] = new OccupiedTile(destinationRow, destinationCol, movedPiece.makePiece(destinationRow, destinationCol, this.movedPiece.getColor()));

        if(this.movedPiece.getColor().isWhite())
            return new Board(newBoardConfiguration, PlayerType.BLACK);

        return new Board(newBoardConfiguration, PlayerType.WHITE);
    }

    /// make the move if and only if the move is allowed
    public Board makeMove() {

        var legalMoves = board.getCurrentPlayerLegalMoves();
        Board ret = this.board;

        for (Move move : legalMoves) {
            if (this.equals(move)) {
                ret = createBoard();
                break;
            }
        }

        return ret;
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Move))
            return false;

        if(this == obj)
            return true;

        final Move move = (Move) obj;

        if(this.pieceAttacked == null && move.pieceAttacked == null)
            return move.destinationRow == this.destinationRow && move.destinationCol == this.destinationCol &&
                    move.movedPiece.equals(this.movedPiece);
        else if(this.pieceAttacked != null && move.pieceAttacked != null)
            return move.destinationRow == this.destinationRow && move.destinationCol == this.destinationCol &&
                    move.movedPiece.equals(this.movedPiece) && this.pieceAttacked.equals(move.pieceAttacked);

        return false;
    }
}