package GameLogic;

import Figures.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BannedFields extends ArrayList<int[]> {
    private final Color color;

    public BannedFields(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }


}
