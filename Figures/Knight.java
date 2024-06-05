package Figures;


import java.util.List;

public class Knight extends Figure {
    public static final int value = 3;


    public Knight(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public String toString() {
        String s = "K" + (getColor() == Color.WHITE ? "w" : "b");
        return s;
    }

    @Override
    public List<MoveFigure> getPossibleMove() {
        possibleMove.add(new MoveFigure(2,1,false,false));
        possibleMove.add(new MoveFigure(1,2,false,false));
        possibleMove.add(new MoveFigure(2,-1,false,false));
        possibleMove.add(new MoveFigure(1,-2,false,false));
        possibleMove.add(new MoveFigure(-1,2,false,false));
        possibleMove.add(new MoveFigure(-2,1,false,false));
        possibleMove.add(new MoveFigure(-1,-2,false,false));
        possibleMove.add(new MoveFigure(-2,-1,false,false));
        return possibleMove;
    }
}
