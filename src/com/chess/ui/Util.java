package com.chess.ui;

import com.chess.Color;
import com.chess.board.PlayerType;
import com.chess.board.Tile;
import com.chess.pieces.PieceType;

import javax.swing.*;

public class Util {

    public static final String APP_TITLE = "Chess Game";

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final java.awt.Color LIGHT_COLOR = new java.awt.Color( 227,193,111);
    public static final java.awt.Color DARK_COLOR = new java.awt.Color(184,139,74);

    public static String getPieceImagePath(final PieceType pieceType, final Color color) {
        final String imgPath = "img/";
        switch (pieceType) {
            case BISHOP:
                return imgPath + (color == Color.WHITE ? "white_bishop.gif" : "black_bishop.gif");
            case KING:
                return imgPath + (color == Color.WHITE ? "white_king.gif" : "black_king.gif");
            case KNIGHT:
                return imgPath + (color == Color.WHITE ? "white_knight.gif" : "black_knight.gif");
            case PAWN:
                return imgPath + (color == Color.WHITE ? "white_pawn.gif" : "black_pawn.gif");
            case QUEEN:
                return imgPath + (color == Color.WHITE ? "white_queen.gif" : "black_queen.gif");
            case ROOK:
                return imgPath + (color == Color.WHITE ? "white_rook.gif" : "black_rook.gif");
            default:
                return imgPath;
        }
    }

    /// when a player is in the check state we show a popup
    public static void showCheckPopUp(final JFrame frame, final PlayerType player) {

        final String message = (player == PlayerType.WHITE ? "White player is in check" : "Black player is in check");
        final String popUpTitle = "Check State";

        JOptionPane.showMessageDialog(frame, message, popUpTitle, JOptionPane.WARNING_MESSAGE);
    }

    /// when a player is in the check mate state we show a popup
    public static void showCheckMatePopUp(final JFrame frame, final PlayerType player) {

        final String message = (player == PlayerType.BLACK ? "The white player won the game" : "The black player won the game");
        final String popUpTitle = "Checkmate State";

        JOptionPane.showMessageDialog(frame, message, popUpTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    // If asking why I need a wrapper
    // https://stackoverflow.com/questions/14382064/java-how-to-change-a-local-variable-within-an-event-listener
   static final class Wrapper<T> {
        private T value;

        public Wrapper(final T data) {
            this.value = data;
        }

        public void setValue(final T data) {
            this.value = data;
        }

        public T getValue() {
            return this.value;
        }
    }
}