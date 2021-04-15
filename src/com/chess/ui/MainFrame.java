package com.chess.ui;

import com.chess.Color;
import com.chess.board.*;
import com.chess.pieces.Piece;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/// TODO refactor the UI
/// maybe break it into multiple components

public class MainFrame {

    private final JFrame frame;
    private JPanel chessBoardPanels[][];
    private Board board;
    private JMenuBar menuBar;
    private boolean showLegalMoves = true;
    private Piece pieceToMove = null;
    private ArrayList<Move> pieceToMoveLegalMoves;

    public MainFrame() {

        frame = createJFrame();
        board = StandardBoardCreator.create();

        createPanels();
        createMenuBar();
        assignTilePieces();
        addTileEventListeners();

        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void unmarkLegalMoves(final ArrayList<Move> moves) {

        for (Move move : moves) {
            if (move.getPieceAttacked() == null) {
                final int destinationRow = move.getDestinationRow();
                final int destinationCol = move.getDestinationCol();
                final JLabel label = (JLabel) chessBoardPanels[destinationRow][destinationCol].getComponent(0);
                label.setIcon(null);
            } else {
                chessBoardPanels[move.getDestinationRow()][move.getDestinationCol()].setBorder(null);
            }
        }
    }

    private void markLegalMoves(final ArrayList<Move> moves) {

        for (Move move : moves) {

            final int destinationRow = move.getDestinationRow();
            final int destinationCol = move.getDestinationCol();

            try {

                if (move.getPieceAttacked() == null) {
                    BufferedImage image = ImageIO.read(new File("img/red_dot.png"));
                    final JLabel label = (JLabel) chessBoardPanels[destinationRow][destinationCol].getComponent(0);
                    label.setIcon(new ImageIcon(image.getScaledInstance(45, 45, Image.SCALE_SMOOTH)));
                    label.setHorizontalAlignment(JLabel.CENTER);
                } else {
                    Border border = BorderFactory.createLineBorder(java.awt.Color.RED);
                    chessBoardPanels[move.getDestinationRow()][move.getDestinationCol()].setBorder(border);
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void addTileEventListeners() {

        var booleanWrapper = new Util.Wrapper<Boolean>(false);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {

                final JLabel label = (JLabel) chessBoardPanels[i][j].getComponent(0);
                final int ROW = i, COL = j;

                label.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(final MouseEvent e) {

                        booleanWrapper.setValue(false);

                        // set the piece we want to move
                        if (board.getPiece(ROW, COL) != null &&
                                ((board.getPiece(ROW, COL).getColor() == Color.WHITE && board.getPlayerToTurn() == PlayerType.WHITE) ||
                                        (board.getPiece(ROW, COL).getColor() == Color.BLACK && board.getPlayerToTurn() == PlayerType.BLACK))) {

                            if (pieceToMove != null && showLegalMoves)
                                unmarkLegalMoves(pieceToMoveLegalMoves);

                            pieceToMove = board.getPiece(ROW, COL);
                            pieceToMoveLegalMoves = pieceToMove.calculateLegalMoves(board);

                            ArrayList<Move> aux = new ArrayList<>();

                            for (Move move : pieceToMoveLegalMoves) {

                                // simulate the move on a newly created board
                                Board simulate = move.makeMove();

                                if (!simulate.getOpponentPlayer().isInCheck())
                                    aux.add(move);
                            }

                            pieceToMoveLegalMoves = aux;

                            if (showLegalMoves)
                                markLegalMoves(pieceToMoveLegalMoves);

                            return;

                        } else if (pieceToMove != null && board.getPiece(ROW, COL) == null) {

                            if (showLegalMoves)
                                unmarkLegalMoves(pieceToMoveLegalMoves);

                            boolean makeMove = false;

                            // create a new move
                            Move newMove = new Move(board, pieceToMove, ROW, COL);

                            for (Move move : pieceToMoveLegalMoves) {
                                if (move.equals(newMove)) {
                                    makeMove = true;
                                    break;
                                }
                            }

                            if (makeMove) {
                                Board aux = newMove.makeMove();

                                booleanWrapper.setValue(aux.getCurrentPlayer().isInCheck());

                                if (aux.getOpponentPlayer().isInCheck()) {
                                    pieceToMove = null;
                                    return;
                                }

                                board = aux;

                                final ImageIcon image = new ImageIcon(Util.getPieceImagePath(pieceToMove.getPieceType(), pieceToMove.getColor()));
                                final JLabel label1 = (JLabel) chessBoardPanels[ROW][COL].getComponent(0);

                                label1.setIcon(new ImageIcon(image.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH)));
                                chessBoardPanels[ROW][COL].revalidate();
                                chessBoardPanels[ROW][COL].repaint();

                                final JLabel label2 = (JLabel) chessBoardPanels[pieceToMove.getRowId()][pieceToMove.getColId()].getComponent(0);

                                label2.setIcon(null);
                                label2.setPreferredSize(new Dimension(65, 65));

                                chessBoardPanels[pieceToMove.getRowId()][pieceToMove.getColId()].revalidate();
                                chessBoardPanels[pieceToMove.getRowId()][pieceToMove.getColId()].repaint();

                                System.out.println(board);
                            }

                            pieceToMove = null;

                        } else if (pieceToMove != null && board.getPiece(ROW, COL) != null &&
                                ((board.getPiece(ROW, COL).getColor() == Color.WHITE && board.getPlayerToTurn() == PlayerType.BLACK) ||
                                        (board.getPiece(ROW, COL).getColor() == Color.BLACK && board.getPlayerToTurn() == PlayerType.WHITE))) {

                            if (showLegalMoves)
                                unmarkLegalMoves(pieceToMoveLegalMoves);

                            final Piece pieceAtDestination = board.getPiece(ROW, COL);

                            boolean makeMove = false;
                            Move newMove = new Move(board, pieceToMove, ROW, COL, pieceAtDestination);

                            for (Move move : pieceToMoveLegalMoves) {
                                if (move.equals(newMove)) {
                                    makeMove = true;
                                    break;
                                }
                            }

                            if (makeMove) {

                                Board aux = newMove.makeMove();

                                booleanWrapper.setValue(aux.getCurrentPlayer().isInCheck());

                                if (aux.getOpponentPlayer().isInCheck()) {
                                    pieceToMove = null;
                                    return;
                                }

                                board = aux;

                                final ImageIcon image = new ImageIcon(Util.getPieceImagePath(pieceToMove.getPieceType(), pieceToMove.getColor()));
                                final JLabel label1 = (JLabel) chessBoardPanels[ROW][COL].getComponent(0);

                                label1.setIcon(new ImageIcon(image.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH)));
                                chessBoardPanels[ROW][COL].revalidate();
                                chessBoardPanels[ROW][COL].repaint();

                                final JLabel label2 = (JLabel) chessBoardPanels[pieceToMove.getRowId()][pieceToMove.getColId()].getComponent(0);

                                label2.setIcon(null);
                                label2.setPreferredSize(new Dimension(65, 65));

                                chessBoardPanels[pieceToMove.getRowId()][pieceToMove.getColId()].revalidate();
                                chessBoardPanels[pieceToMove.getRowId()][pieceToMove.getColId()].repaint();

                                System.out.println(board);
                            }

                            pieceToMove = null;

                        } else {
                            pieceToMove = null;
                        }

                        if (board.getCurrentPlayer().isInCheckMate()) {
                            if (board.getCurrentPlayer().isWhite())
                                System.out.println("Black player won");
                            else
                                System.out.println("White Player won");

                            if (board.getCurrentPlayer().isWhite())
                                Util.showCheckMatePopUp(frame, PlayerType.WHITE);
                            else
                                Util.showCheckMatePopUp(frame, PlayerType.BLACK);

                            restoreToInitialBoard();
                            booleanWrapper.setValue(false);
                            return;

                        } else if (pieceToMove == null && board.getCurrentPlayer().isInCheck() && booleanWrapper.getValue() == true) {
                            booleanWrapper.setValue(false);
                            Util.showCheckPopUp(frame, board.getPlayerToTurn());
                            return;
                        }
                    }
                });
            }
        }
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        final JMenu settingsMenu = new JMenu("Settings");
        final JMenuItem i1 = new JMenuItem("Hide Allowed Moves");

        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showLegalMoves = !showLegalMoves;
                if (pieceToMove != null)
                    unmarkLegalMoves(pieceToMoveLegalMoves);
                if (showLegalMoves)
                    i1.setText("Hide Allowed Moves");
                else
                    i1.setText("Show Allowed Moves");
            }
        });

        final JMenuItem i2 = new JMenuItem("Exit");

        i2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }

        });

        final JMenuItem i3 = new JMenuItem("Restore Board");

        i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                restoreToInitialBoard();
            }
        });

        settingsMenu.add(i1);
        settingsMenu.add(i3);
        settingsMenu.add(i2);

        menuBar.add(settingsMenu);

        frame.setJMenuBar(menuBar);
    }

    private void restoreToInitialBoard() {

        board = StandardBoardCreator.create();
        final Tile[][] tiles = board.getTiles();

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {

                final int ROW = i, COL = j;
                JLabel label = (JLabel) chessBoardPanels[i][j].getComponent(0);

                if (!tiles[i][j].isEmptyTile()) {

                    final Piece piece = tiles[ROW][COL].getPieceOnTile();
                    final ImageIcon image = new ImageIcon(Util.getPieceImagePath(piece.getPieceType(), piece.getColor()));

                    label.setIcon(new ImageIcon(image.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH)));
                } else {
                    label.setIcon(null);
                    label.setPreferredSize(chessBoardPanels[ROW][COL].getSize());
                }
            }
        }

    }

    private JFrame createJFrame() {
        JFrame frame = new JFrame(Util.APP_TITLE);

        frame.setSize(new Dimension(Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // by default, the close window button doesn't work
        frame.setLayout(new GridLayout(8, 8)); // make the layout look like a chessboard grid

        return frame;
    }

    // create the panels and assign a color to them.
    private void createPanels() {

        chessBoardPanels = new JPanel[8][8];

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                chessBoardPanels[i][j] = new JPanel();
                if ((i + j) % 2 == 0)
                    chessBoardPanels[i][j].setBackground(Util.LIGHT_COLOR);
                else
                    chessBoardPanels[i][j].setBackground(Util.DARK_COLOR);
                frame.add(chessBoardPanels[i][j]);
            }
        }
    }

    private void assignTilePieces() {

        final Tile[][] tiles = board.getTiles();

        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {

                if (!tiles[row][col].isEmptyTile()) {

                    final Piece piece = tiles[row][col].getPieceOnTile();
                    final ImageIcon image = new ImageIcon(Util.getPieceImagePath(piece.getPieceType(), piece.getColor()));

                    final JLabel label = new JLabel();
                    label.setIcon(new ImageIcon(image.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH)));

                    chessBoardPanels[row][col].add(label);
                } else {

                    // create an empty label that takes the full width and height of the parent pannel.
                    final int ROW = row, COL = col;

                    final JLabel label = new JLabel() {
                        public Dimension getPreferredSize() {
                            return chessBoardPanels[ROW][COL].getSize();
                        }

                        ;
                    };

                    chessBoardPanels[row][col].add(label);
                }
            }
        }
    }
}