package Figures;

public class MoveFigure {
    private final int x;
    private final int y;
    private final boolean onlyKill;
    private final boolean startPos;

    public MoveFigure(int x, int y, boolean onlyKill,boolean startPos) {
        this.x = x;
        this.y = y;
        this.onlyKill = onlyKill;
        this.startPos = startPos;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", onlyKill=" + onlyKill + ", startPos=" + startPos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOnlyKill() {
        return onlyKill;
    }

    public boolean isStartPos() {
        return startPos;
    }
}
