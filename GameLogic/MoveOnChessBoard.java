package GameLogic;

import Figures.*;


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
                if (potentialMove.isOnlyKill()){
                    if (board.getPlaceChessBoard(destinationX, destinationY) != null) {
                        return true;
                    }
                    continue;
                } else if (potentialMove.isStartPos() && figure.isMoved()) {
                    return false;
                }
                if (this.getFigureMoveThroughMoveFigure(board) == null) {
                    if (figure.getClass() == Pawn.class && board.getPlaceChessBoard(destinationX, destinationY) != null){
                        return false;
                    }
                    return true;
                } else {
                    break;
                }
            }
        }
        return false;
    }

    public boolean isPawnPromotion(Figure figure){
        if (figure.getClass() == Pawn.class) {
            if (destinationX == 1 && figure.getColor() == Color.BLACK) {
                return true;
            } else if (destinationX == 7 && figure.getColor() == Color.WHITE) {
                return true;
            }
        }
        return false;
    }

    public String getFigureMoveThroughMoveFigure(Board board) {
        if (board.getPlaceChessBoard(startX, startY).getClass() == Knight.class) {
            return null;
        } else if (startX == destinationX) {
            int step = (destinationY - startY) > 0 ? 1 : -1;
            for (int y = startY + step; y != destinationY; y += step) {
                if (board.getPlaceChessBoard(startX, y) != null) {
                    return board.getPlaceChessBoard(startX, y).toString();
                }
            }
        } else if (startY == destinationY) {
            int step = (destinationX - startX) > 0 ? 1 : -1;
            for (int x = startX + step; x != destinationX; x += step) {
                if (board.getPlaceChessBoard(x, startY) != null) {
                    return board.getPlaceChessBoard(x, startY).toString();
                }
            }
        } else if (Math.abs(destinationX - startX) == Math.abs(destinationY - startY)) {
            int stepX = (destinationX - startX) > 0 ? 1 : -1;
            int stepY = (destinationY - startY) > 0 ? 1 : -1;
            int x = startX + stepX;
            int y = startY + stepY;
            while (x != destinationX && y != destinationY) {
                if (board.getPlaceChessBoard(x, y) != null) {
                    return board.getPlaceChessBoard(x, y).toString();
                }
                x += stepX;
                y += stepY;
            }
        }
        return null;
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
