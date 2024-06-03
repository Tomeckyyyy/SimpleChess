package Figures;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    private final int startX;
    private final int startY;
    private final Color color;
    List<MoveFigure> possibleMove = new ArrayList<>();

    public Figure(int startX, int startY, Color color){
        this.startX = startX;
        this.startY = startY;
        this.color = color;
    }

    public abstract List<MoveFigure> getPossibleMove();

    public int getStartX(){
        return startX;
    }
    public int getStartY(){
        return startY;
    }
    public Color getColor() {
        return color;
    }

    public abstract String toString();
}
