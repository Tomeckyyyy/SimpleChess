package GUI;

import GameLogic.Board;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SimpleChessGUI extends JFrame {
    protected JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private static final String[] columnNames = {"", "H", "G", "F", "E", "D", "C", "B", "A"};

    public SimpleChessGUI() {
        initializeGUI();
    }

    public void gameRefreshGUI(Board board) {
        ImageIcon whitePawnIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
        ImageIcon whiteRookIcon = new ImageIcon("src/GUI/img/white/Rook.png");
        ImageIcon whiteBishopIcon = new ImageIcon("src/GUI/img/white/Bishop.png");
        ImageIcon whiteKnightIcon = new ImageIcon("src/GUI/img/white/Knight.png");
        ImageIcon whiteKingIcon = new ImageIcon("src/GUI/img/white/King.png");
        ImageIcon whiteQueenIcon = new ImageIcon("src/GUI/img/white/Queen.png");
        ImageIcon blackPawnIcon = new ImageIcon("src/GUI/img/black/Pawn.png");
        ImageIcon blackRookIcon = new ImageIcon("src/GUI/img/black/Rook.png");
        ImageIcon blackBishopIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
        ImageIcon blackKnightIcon = new ImageIcon("src/GUI/img/black/Knight.png");
        ImageIcon blackQueenIcon = new ImageIcon("src/GUI/img/black/Queen.png");
        ImageIcon blackKingIcon = new ImageIcon("src/GUI/img/black/King.png");

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board.getPlaceChessBoard(x, y) == null) {
                    continue;
                }
                String figure = board.getPlaceChessBoard(x, y).toString();
                if (figure.charAt(1) == 'w') {
                    switch (figure.charAt(0)) {
                        case 'R':
                            chessBoardSquares[x][y].setIcon(whiteRookIcon);
                            break;
                        case 'K':
                            chessBoardSquares[x][y].setIcon(whiteKnightIcon);
                            break;
                        case 'B':
                            chessBoardSquares[x][y].setIcon(whiteBishopIcon);
                            break;
                        case '0':
                            chessBoardSquares[x][y].setIcon(whiteKingIcon);
                            break;
                        case 'Q':
                            chessBoardSquares[x][y].setIcon(whiteQueenIcon);
                            break;
                        case 'P':
                            chessBoardSquares[x][y].setIcon(whitePawnIcon);
                            break;
                    }
                } else {
                    switch (figure.charAt(0)) {
                        case 'R':
                            chessBoardSquares[x][y].setIcon(blackRookIcon);
                            break;
                        case 'K':
                            chessBoardSquares[x][y].setIcon(blackKnightIcon);
                            break;
                        case 'B':
                            chessBoardSquares[x][y].setIcon(blackBishopIcon);
                            break;
                        case '0':
                            chessBoardSquares[x][y].setIcon(blackKingIcon);
                            break;
                        case 'Q':
                            chessBoardSquares[x][y].setIcon(blackQueenIcon);
                            break;
                        case 'P':
                            chessBoardSquares[x][y].setIcon(blackPawnIcon);
                            break;
                    }
                }
            }
        }
    }

    private void initializeGUI() {
        setTitle("Simple Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chessBoard.setBackground(new Color(197, 197, 106));
        Font font = new Font("Sans-serif", Font.PLAIN, 60);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        setVisible(true);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0) {
                    JLabel label = new JLabel("" + (i + 1));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    chessBoard.add(label);
                } else {
                    JButton button = new JButton();
                    button.setFont(font);
                    button.setBackground(i % 2 == j % 2 ? new Color(24, 110, 64) : Color.WHITE);
                    chessBoardSquares[i][j - 1] = button;
                    chessBoard.add(button);
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            JLabel label = new JLabel(columnNames[i], JLabel.CENTER);
            chessBoard.add(label);
        }

        add(chessBoard, BorderLayout.CENTER);
        setVisible(true);
    }
}