package Figures;

public enum Color {
    BLACK, WHITE;

    public Color next() {
        return this == WHITE ? BLACK : WHITE;
    }
}
