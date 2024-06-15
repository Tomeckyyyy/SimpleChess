package GameLogic;

public class BoardListener {
    private static Board board;

    public static void setBoard(Board board1) {
        board = board1;
    }

    public static Board getBoard() {
        return board;
    }
}
