package GameLogic;

import Figures.Figure;
import Figures.Knight;
import Figures.MoveFigure;


public class MoveOnChessBoard {
    private final int startX;
    private final int startY;
    private final int destinationX;
    private final int destinationY;

    public MoveOnChessBoard(int startX, int startY, int destinationX, int destinationY) {
        this.startX = startX;
        this.startY = startY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public boolean isPossibleMove(Figure figure, Board board) {
        for (MoveFigure potentialMove : figure.getPossibleMove()) {
            if (startX + potentialMove.getX() == destinationX && startY + potentialMove.getY() == destinationY) {
                if (!this.isMoveThroughFigure(board)) {
                    return true;
                }
            }
        }
        return false;
    }
    // przetestować czy ten koń działa

    private boolean isMoveThroughFigure(Board board) {
        if (board.getPlaceChessBoard(startX, startY).getClass() == Knight.class) {
            return false;
        } else if (startX == destinationX) {
            int step = (destinationY - startY) > 0 ? 1 : -1;
            for (int y = startY - step; y != destinationY; y += step) {
                if (board.getPlaceChessBoard(startX, y) != null) {
                    return true;
                }
            }
        } else if (startY == destinationY) {
            int step = (destinationX - startX) > 0 ? 1 : -1;
            for (int x = startX - step; x != destinationX; x += step) {
                if (board.getPlaceChessBoard(x, startY) != null) {
                    return true;
                }
            }
        } else if (Math.abs(destinationX - startX) == Math.abs(destinationY - startY)) {
            int stepX = (destinationX - startX) > 0 ? 1 : -1;
            int stepY = (destinationY - startY) > 0 ? 1 : -1;
            for (int i = 1; i != destinationX; i++) {
                if (board.getPlaceChessBoard(startX + stepX, startY + stepY) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }
}
