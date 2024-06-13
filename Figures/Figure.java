package Figures;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    private int currentX;
    private int currentY;
    private final Color color;
    private boolean isMoved = false;
    List<MoveFigure> possibleMove = new ArrayList<>();

    public Figure(int startX, int startY, Color color){
        this.currentX = startX;
        this.currentY = startY;
        this.color = color;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public abstract List<MoveFigure> getPossibleMove();

    public int getCurrentX(){
        return currentX;
    }
    public int getCurrentY(){
        return currentY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public Color getColor() {
        return color;
    }

    public abstract String toString();
}
