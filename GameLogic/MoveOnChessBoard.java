package GameLogic;

import Figures.Figure;
import Figures.King;
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
        // Sprawdzania czy jest próba bicia króla
        if (board.getPlaceChessBoard(destinationX, destinationY) != null) {
            if (board.getPlaceChessBoard(destinationX, destinationY).getClass() == King.class){
                return false;
            } else if (figure.getColor() == board.getPlaceChessBoard(destinationX, destinationY).getColor()) {
                return false;
            }
        }
        // Sprawdzanie czy taki ruch istnieje w ruchach potencjalnych, potem sprawdza czy figura przechodzi przez inną;
        for (MoveFigure potentialMove : figure.getPossibleMove()) {
            if (startX + potentialMove.getX() == destinationX && startY + potentialMove.getY() == destinationY) {
                if (potentialMove.isOnlyKill() && board.getPlaceChessBoard(destinationX, destinationY) == null){
                    return false;
                } else if (potentialMove.isStartPos() && figure.isMoved()) {
                    return false;
                }
                if (!this.isMoveThroughFigure(board)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        return false;
    }

    private boolean isMoveThroughFigure(Board board) {
        if (board.getPlaceChessBoard(startX, startY).getClass() == Knight.class) {
            return false;
        } else if (startX == destinationX) {
            int step = (destinationY - startY) > 0 ? 1 : -1;
            for (int y = startY + step; y != destinationY; y += step) {
                if (board.getPlaceChessBoard(startX, y) != null) {
                    return true;
                }
            }
        } else if (startY == destinationY) {
            int step = (destinationX - startX) > 0 ? 1 : -1;
            for (int x = startX + step; x != destinationX; x += step) {
                if (board.getPlaceChessBoard(x, startY) != null) {
                    return true;
                }
            }
        } else if (Math.abs(destinationX - startX) == Math.abs(destinationY - startY)) {
            int stepX = (destinationX - startX) > 0 ? 1 : -1;
            int stepY = (destinationY - startY) > 0 ? 1 : -1;
            int x = startX + stepX;
            int y = startY + stepY;
            while (x != destinationX && y != destinationY) {
                if (board.getPlaceChessBoard(x, y) != null) {
                    return true;
                }
                x += stepX;
                y += stepY;
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
