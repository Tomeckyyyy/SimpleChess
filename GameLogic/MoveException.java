package GameLogic;

import GUI.SimpleChessGUI;

public class MoveException extends Exception{
    public MoveException() {
        super();
    }

    public MoveException(String message) {
        super(message);
        SimpleChessGUI.showError(message);
    }

    public MoveException(String message, Throwable cause) {
        super(message, cause);
        SimpleChessGUI.showError(message);
    }

    public MoveException(Throwable cause) {
        super(cause);
    }
}
