package Figures;


import java.util.List;

public class Knight extends Figure {
    public static final int value = 3;


    public Knight(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public List<MoveFigure> getPossibleMove() {

        return possibleMove;
    }

}
