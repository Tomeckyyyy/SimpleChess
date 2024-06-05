package Figures;

import java.util.List;

public class Pawn extends Figure {
    public static final int value = 1;

    public Pawn(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public List<MoveFigure> getPossibleMove() {
        if (this.getColor() == Color.BLACK) {
            possibleMove.add(new MoveFigure(1, 0, false, false));
            possibleMove.add(new MoveFigure(2, 0, false, true));
            possibleMove.add(new MoveFigure(1, 1, true, false));
            possibleMove.add(new MoveFigure(1, -1, true, false));
        } else {
            possibleMove.add(new MoveFigure(-1, 0, false, false));
            possibleMove.add(new MoveFigure(-2, 0, false, true));
            possibleMove.add(new MoveFigure(-1, 1, true, false));
            possibleMove.add(new MoveFigure(-1, -1, true, false));
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        return "P" + (getColor() == Color.WHITE ? "w" : "b");
    }
}
