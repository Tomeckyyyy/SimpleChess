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
                if (this.isMoveThroughFigure(board)){
                    return true;
                }
            }
        }
        return false;
    }
    // przetestować czy ten koń działa

    private boolean isMoveThroughFigure(Board board){
        if (board.getPlaceChessBoard(startX, startY).getClass() == Knight.class){
            return false;
        }
        if (startX - destinationX == 0){
            
        }
        if (startY - destinationY == 0){

        }
        else {

        }
        return true;
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
