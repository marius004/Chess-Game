package com.chess.board;

import com.chess.Color;
import com.chess.pieces.*;

public class StandardBoardCreator {

    public static Board create() {
        Tile config[][] = new Tile[Util.BOARD_ROWS][Util.BOARD_COLS];

        config[0][0] = new Tile.OccupiedTile(0, 0, new Rook(0,0, Color.BLACK));
        config[0][1] = new Tile.OccupiedTile(0, 1, new Knight(0,1, Color.BLACK));
        config[0][2] = new Tile.OccupiedTile(0, 2, new Bishop(0, 2, Color.BLACK));
        config[0][3] = new Tile.OccupiedTile(0, 3, new Queen(0, 3, Color.BLACK));
        config[0][4] = new Tile.OccupiedTile(0, 4, new King(0, 4, Color.BLACK));
        config[0][5] = new Tile.OccupiedTile(0, 5, new Bishop(0, 5, Color.BLACK));
        config[0][6] = new Tile.OccupiedTile(0, 6, new Knight(0,6, Color.BLACK));
        config[0][7] = new Tile.OccupiedTile(0, 7, new Rook(0,7, Color.BLACK));

        for(int i = 0;i < Util.BOARD_COLS;++i) {
            config[1][i] = new Tile.OccupiedTile(1, i, new Pawn(1, i, Color.BLACK));
            config[6][i] = new Tile.OccupiedTile(6, i, new Pawn(6, i, Color.WHITE));
        }

        for(int i = 2;i < 6;++i)
            for(int j = 0; j < Util.BOARD_COLS;++j)
                config[i][j] = new Tile.EmptyTile(i, j);

        config[7][0] = new Tile.OccupiedTile(7, 0, new Rook(7, 0, Color.WHITE));
        config[7][1] = new Tile.OccupiedTile(7,1, new Knight(7, 1, Color.WHITE));
        config[7][2] = new Tile.OccupiedTile(7, 2, new Bishop(7,2, Color.WHITE));
        config[7][3] = new Tile.OccupiedTile(7, 3, new Queen(7, 3, Color.WHITE));
        config[7][4] = new Tile.OccupiedTile(7, 4, new King(7, 4, Color.WHITE));
        config[7][5] = new Tile.OccupiedTile(7, 5, new Bishop(7,5, Color.WHITE));
        config[7][6] = new Tile.OccupiedTile(7,6, new Knight(7, 6, Color.WHITE));
        config[7][7] = new Tile.OccupiedTile(7, 7, new Rook(7, 7, Color.WHITE));

        return new Board(config, PlayerType.WHITE);
    }

}
