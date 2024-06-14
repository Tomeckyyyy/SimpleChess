package GUI;

import GameLogic.Board;
import GameLogic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class SimpleChessGUI extends JFrame {
    protected JButton[][] chessBoardSquares = new JButton[8][8];
    private static final String[] columnNames = {"", "H", "G", "F", "E", "D", "C", "B", "A"};
    private static final ImageIcon whitePawnIcon = new ImageIcon("src/GUI/img/white/Pawn.png");
    private static final ImageIcon whiteRookIcon = new ImageIcon("src/GUI/img/white/Rook.png");
    private static final ImageIcon whiteBishopIcon = new ImageIcon("src/GUI/img/white/Bishop.png");
    private static final ImageIcon whiteKnightIcon = new ImageIcon("src/GUI/img/white/Knight.png");
    private static final ImageIcon whiteKingIcon = new ImageIcon("src/GUI/img/white/King.png");
    private static final ImageIcon whiteQueenIcon = new ImageIcon("src/GUI/img/white/Queen.png");
    private static final ImageIcon blackPawnIcon = new ImageIcon("src/GUI/img/black/Pawn.png");
    private static final ImageIcon blackRookIcon = new ImageIcon("src/GUI/img/black/Rook.png");
    private static final ImageIcon blackBishopIcon = new ImageIcon("src/GUI/img/black/Bishop.png");
    private static final ImageIcon blackKnightIcon = new ImageIcon("src/GUI/img/black/Knight.png");
    private static final ImageIcon blackQueenIcon = new ImageIcon("src/GUI/img/black/Queen.png");
    private static final ImageIcon blackKingIcon = new ImageIcon("src/GUI/img/black/King.png");

    public SimpleChessGUI() {
        initializeGUI();
    }

    public void gameRefreshGUI(Board board) {
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
                JOptionPane.showMessageDialog(null, "Load Game from \"Movements\" file");
                try (BufferedReader br = new BufferedReader(new FileReader("Movements.txt"))) {
                    while (br.readLine() != null){
                        String line = br.readLine();
                    }
                } catch (IOException m) {
                    JOptionPane.showMessageDialog(null, "Nie udało się odczytać pliku");
                    System.out.println(m);
                }
            }
        });
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Save Game to \"Movements\" file");
//                try (BufferedWriter br = new BufferedWriter(new FileWriter("Movements.txt"))) {
//                    for (MoveOnChessBoard x : ) {
//                        String line = name + "/" + species + "/" + age + "\n";
//                        br.write(line);
//                    }
//                } catch (IOException m) {
//                    JOptionPane.showMessageDialog(null, "Nie udało się zapisać do pliku");
//                    System.out.println(m);
//                }
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "New Game");
                new Game(new SimpleChessGUI());
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

    public static void showError(String message){
        JOptionPane.showMessageDialog(null, message);
    }
}