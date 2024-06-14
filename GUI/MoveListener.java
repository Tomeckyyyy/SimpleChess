package GUI;

import GameLogic.MoveOnChessBoard;

public class MoveListener {
    private static int startX = -1;
    private static int startY = -1;
    private static int destinationX = -1;
    private static int destinationY = -1;
    private static boolean isSecondCoordinates = false;

    public static MoveOnChessBoard moveListener() {
        return new MoveOnChessBoard(startX, startY, destinationX, destinationY);
    }

    public static void getFieldsCoordinatesListener(int x, int y){
        if (!isSecondCoordinates) {
            startX = x;
            startY = y;
            isSecondCoordinates = true;
        } else {
            destinationX = x;
            destinationY = y;
        }
    }

    public static boolean isMoveListenerComplete() {
        return startX != -1 && startY != -1 & destinationX != -1 && destinationY != -1;
    }

    public static void clearListener(){
        startX = -1;
        startY = -1;
        destinationX = -1;
        destinationY = -1;
        isSecondCoordinates = false;
    }
}
