import javax.swing.*;
import java.awt.*;

public class SimpleChessGUI extends JFrame {
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private static final String[] columnNames = {"", "A", "B", "C", "D", "E", "F", "G", "H"};

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
        JMenu gameMenu = new JMenu("GameLogic.Game");
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
                    button.setBackground(i % 2 == j % 2 ? Color.WHITE : new Color(24, 110, 64));
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