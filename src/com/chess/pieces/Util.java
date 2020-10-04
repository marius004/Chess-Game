package com.chess.pieces;

public final class Util {

    public static final int BOARD_ROWS = 8;
    public static final int BOARD_COLS = 8;

    public static boolean isValideCoordonate( final int rowID,
                                              final int colId ) {
        return rowID >= 0 && rowID < BOARD_ROWS && colId >= 0 && colId < BOARD_COLS;
    }
}