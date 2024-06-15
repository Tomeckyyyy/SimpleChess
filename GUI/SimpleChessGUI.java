package GUI;

import Figures.*;
import GameLogic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class SimpleChessGUI extends JFrame {
    protected JButton[][] chessBoardSquares = new JButton[8][8];
    private final String[] columnNames = {"", "H", "G", "F", "E", "D", "C", "B", "A"};
    private boolean off = false;
    private final ImageIcon whitePawnIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
    private final ImageIcon whiteRookIcon = new ImageIcon("src/GUI/img/white/Rook.png");
    private final ImageIcon whiteBishopIcon = new ImageIcon("src/GUI/img/white/Bishop.png");
    private final ImageIcon whiteKnightIcon = new ImageIcon("src/GUI/img/white/Knight.png");
    private final ImageIcon whiteKingIcon = new ImageIcon("src/GUI/img/white/King.png");
    private final ImageIcon whiteQueenIcon = new ImageIcon("src/GUI/img/white/Queen.png");
    private final ImageIcon blackPawnIcon = new ImageIcon("src/GUI/img/black/Pawn.png");
    private final ImageIcon blackRookIcon = new ImageIcon("src/GUI/img/black/Rook.png");
    private final ImageIcon blackBishopIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
    private final ImageIcon blackKnightIcon = new ImageIcon("src/GUI/img/black/Knight.png");
    private final ImageIcon blackQueenIcon = new ImageIcon("src/GUI/img/black/Queen.png");
    private final ImageIcon blackKingIcon = new ImageIcon("src/GUI/img/black/King.png");

    public SimpleChessGUI() {
        initializeGUI();
    }

    public void gameRefreshGUI(Board board) {
        if (off) {
            SimpleChessGUI.this.dispose();
            new Game(new SimpleChessGUI());
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board.getPlaceChessBoard(x, y) == null) {
                    chessBoardSquares[x][y].setIcon(null);
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

    public char showPromotionDialog() {
        Object[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose a piece to promote your pawn:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION, // Używamy DEFAULT_OPTION, aby wymusić wybór jednej z opcji
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        while (choice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "Please choose a piece to promote your pawn.");
            choice = JOptionPane.showOptionDialog(null,
                    "Choose a piece to promote your pawn:",
                    "Pawn Promotion",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
        }
        switch (choice) {
            case 1:
                return 'R';
            case 2:
                return 'B';
            case 3:
                return 'K';
            default:
                return 'Q';
        }
    }


    private void initializeGUI() {
        setTitle("Simple Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        JPanel chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chessBoard.setBackground(new Color(197, 197, 106));
        Font font = new Font("Sans-serif", Font.PLAIN, 60);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem loadGame = new JMenuItem("Load Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        fileMenu.add(loadGame);
        fileMenu.add(saveGame);
        JMenuItem newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);
        JMenuItem rules = new JMenuItem("Rules");
        JMenuItem gitHub = new JMenuItem("Project GitHub");
        helpMenu.add(rules);
        helpMenu.add(gitHub);

        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        setVisible(true);

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(SimpleChessGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                        Board board = new Board();
                        int x = 0;
                        String line;
                        while ((line=br.readLine())!=null) {
                            for (int y = 0; y < 8; y++) {
                                char symbolNewFigure = line.charAt(3 * y + 1);
                                Figures.Color color = line.charAt(3 * y + 2) == 'w' ? Figures.Color.WHITE : Figures.Color.BLACK;
                                switch (symbolNewFigure) {
                                    case 'R':
                                        board.setPlaceOnBoard(x, y, new Rook(x, y, color));
                                        break;
                                    case 'B':
                                        board.setPlaceOnBoard(x, y, new Bishop(x, y, color));
                                        break;
                                    case 'K':
                                        board.setPlaceOnBoard(x, y, new Knight(x, y, color));
                                        break;
                                    case '0':
                                        board.setPlaceOnBoard(x, y, new King(x, y, color));
                                        break;
                                    case 'P':
                                        board.setPlaceOnBoard(x, y, new Pawn(x, y, color));
                                        break;
                                    case 'Q':
                                        board.setPlaceOnBoard(x, y, new Queen(x, y, color));
                                        break;
                                    default:
                                        board.setPlaceOnBoard(x, y, null);
                                        break;
                                }
                            }
                            x++;
                        }
                        BoardListener.setBoard(board);
                        off = true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(SimpleChessGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedWriter br = new BufferedWriter(new FileWriter(selectedFile))) {
                        br.write(BoardListener.getBoard().toString());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Nie udało się zapisać do pliku");
                        ex.printStackTrace();
                    }
                }
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "New Game");
                off = true;
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLink("https://en.wikipedia.org/wiki/Rules_of_chess");
            }
        });

        gitHub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLink("https://github.com/Tomeckyyyy/SimpleChess");
            }
        });


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0) {
                    JLabel label = new JLabel("" + (i + 1));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    chessBoard.add(label);
                } else {
                    ButtonField button = new ButtonField(i, j - 1);
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

    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public boolean getOff(){
        return off;
    }
}