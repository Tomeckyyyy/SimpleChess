package Figures;

import java.util.List;

public class Rook extends Figure {
    public static final int value = 5;

    public Rook(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public List<MoveFigure> getPossibleMove() {
        for (int i = 1; i < 8; i++) {
            possibleMove.add(new MoveFigure(0, i , false, false));
            possibleMove.add(new MoveFigure(0, -i , false, false));
            possibleMove.add(new MoveFigure(i, 0, false, false));
            possibleMove.add(new MoveFigure(-i, 0, false, false));
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        String s = "R" + (getColor() == Color.WHITE ? "w" : "b");
        return s;
    }
}
