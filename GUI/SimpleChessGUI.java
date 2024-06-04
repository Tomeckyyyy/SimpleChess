package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SimpleChessGUI extends JFrame {
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private static final String[] columnNames = {"", "H", "G", "F", "E", "D", "C", "B", "A"};

    public SimpleChessGUI() {
        initializeGUI();
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

        ImageIcon whitePawnIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
        ImageIcon whiteRookIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
        ImageIcon whiteBishopIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
        ImageIcon whiteKnightIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
        ImageIcon whiteKingIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
        ImageIcon whiteQueenIcon = new ImageIcon("src/GUI/img/white/Pawn.png");

        ImageIcon blackPawnIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
        ImageIcon blackRookIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
        ImageIcon blackBishopIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
        ImageIcon blackKnightIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
        ImageIcon blackQueenIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
        ImageIcon blackKingIcon = new ImageIcon("src/GUI/img/black/Bishop.png");

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

                    // Dodawanie Figur
                    if (i == 0){
                        if (j == 0) {
                            button.setIcon(whiteRookIcon);
                        } else if (j == 1){
                            button.setIcon(whiteKnightIcon);
                        } else if (j == 1){
                            button.setIcon(whiteBishopIcon);
                        } else if (j == 1){
                            //button.setIcon(whiteIcon);
                        } else if (j == 1){
                            button.setIcon(whiteKnightIcon);
                        } else if (j == 1){
                            button.setIcon(whiteKnightIcon);
                        }
                    } else if (i == 7) {

                    }
                    // Dodawanie Pionków
                    if (i == 1) {
                        button.setIcon(whitePawnIcon);
                    } else if (i == 6) {
                        button.setIcon(blackPawnIcon);
                    }
                    chessBoard.add(button);
                }
            }
        }

        // Pobiera źródło obrazku
        System.out.println(chessBoardSquares[1][6].getIcon());

        for (int i = 0; i < 9; i++) {
            JLabel label = new JLabel(columnNames[i], JLabel.CENTER);
            chessBoard.add(label);
        }

        add(chessBoard, BorderLayout.CENTER);

        setVisible(true);
    }
}