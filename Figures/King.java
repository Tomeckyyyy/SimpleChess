package Figures;


import java.util.List;

public class King extends Figure {
    public static final int value = 10000;

    public King(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public List<MoveFigure> getPossibleMove() {
        possibleMove.add(new MoveFigure(1,1,false, false));
        possibleMove.add(new MoveFigure(1,0,false, false));
        possibleMove.add(new MoveFigure(1,-1,false, false));
        possibleMove.add(new MoveFigure(0,1,false, false));
        possibleMove.add(new MoveFigure(0,-1,false, false));
        possibleMove.add(new MoveFigure(-1,1,false, false));
        possibleMove.add(new MoveFigure(-1,0,false, false));
        possibleMove.add(new MoveFigure(-1,-1,false, false));
        return possibleMove;
    }

    @Override
    public String toString() {
        return "0" + (getColor() == Color.WHITE ? "w" : "b");
    }
}
