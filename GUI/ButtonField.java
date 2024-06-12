package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonField extends JButton {
    private final int currentX;
    private final int currentY;

    public ButtonField(int x, int y) {
        this.currentX = x;
        this.currentY = y;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick();
            }
        });
    }

    private void onClick( ) {
        MoveListener.getFieldsCoordinatesListener(currentX, currentY);
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }
}
