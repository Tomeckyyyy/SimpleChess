package Figures;

import java.util.List;

public class Bishop extends Figure {
    public static final int value = 3;

    public Bishop(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public List<MoveFigure> getPossibleMove() {
        for (int i = 1; i < 8; i++) {
            possibleMove.add(new MoveFigure(i,i,false,false));
            possibleMove.add(new MoveFigure(i,-i,false,false));
            possibleMove.add(new MoveFigure(-i,i,false,false));
            possibleMove.add(new MoveFigure(-i,-i,false,false));
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        return "B" + (getColor() == Color.WHITE ? "w" : "b");
    }
}
