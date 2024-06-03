package GameLogic;

import Figures.Figure;

public class Board {
    private Figure[][] chessBoard;

    public Board() {
        this.chessBoard = new Figure[8][8];
    }

    public void setPlaceOnBoard(int x, int y, Figure figure){
        chessBoard[x][y] = figure;
    }

    @Override
    public String toString() {
        StringBuilder consoleBoard = new StringBuilder();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                consoleBoard.append("|").append((chessBoard[x][y] == null) ? " " : chessBoard[x][y]);
            }
            consoleBoard.append("| \n");
        }
        return consoleBoard.toString();
    }

    public Figure getPlaceChessBoard(int x, int y) {
        return chessBoard[x][y];
    }
}
