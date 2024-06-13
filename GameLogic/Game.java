package GameLogic;

import Figures.*;
import GUI.MoveListener;
import GUI.SimpleChessGUI;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final List<Figure> figureInPlay = new ArrayList<>();
    private final Board board = new Board();


    public Game(SimpleChessGUI simpleChessGUI) {
        board.setPlaceOnBoard(0, 3, new King(0, 3, Color.WHITE));
        board.setPlaceOnBoard(0,0, new Rook(0,0, Color.WHITE));
        board.setPlaceOnBoard(0,1, new Knight(0,1, Color.WHITE));
        board.setPlaceOnBoard(0,2, new Bishop(0,2, Color.WHITE));
        board.setPlaceOnBoard(0,4, new Queen(0,4, Color.WHITE));
        board.setPlaceOnBoard(0,5, new Bishop(0,5, Color.WHITE));
        board.setPlaceOnBoard(0,6, new Knight(0,6, Color.WHITE));
        board.setPlaceOnBoard(0,7, new Rook(0,7, Color.WHITE));

        board.setPlaceOnBoard(7, 3, new King(7, 7, Color.BLACK));
        board.setPlaceOnBoard(7, 0, new Rook(7, 0, Color.BLACK));
        board.setPlaceOnBoard(7, 1, new Knight(7, 1, Color.BLACK));
        board.setPlaceOnBoard(7, 2, new Bishop(7, 2, Color.BLACK));
        board.setPlaceOnBoard(7, 4, new Queen(7, 4, Color.BLACK));
        board.setPlaceOnBoard(7, 5, new Bishop(7, 5, Color.BLACK));
        board.setPlaceOnBoard(7, 6, new Knight(7, 6, Color.BLACK));
        board.setPlaceOnBoard(7, 7, new Rook(7, 7, Color.BLACK));

        for (int i = 0; i < 8; i++){
            board.setPlaceOnBoard(1, i, new Pawn(1 ,i ,Color.WHITE));
            board.setPlaceOnBoard(6, i, new Pawn(6 ,i ,Color.BLACK));
        }
        simpleChessGUI.gameRefreshGUI(board);

        // TODO: Testowanie matowania.
        // TODO: Zrobienie wiadomości po angielsku      moveFigureOnChessBoardLogic

        Color colorOnMove = Color.WHITE;
        while (!isCheckMate(board, Color.WHITE) && !isCheckMate(board, Color.BLACK)) {
            waitForMove(colorOnMove);
            colorOnMove = colorOnMove.next();
            System.out.println(colorOnMove);
            simpleChessGUI.gameRefreshGUI(board);
        }
    }

    private void waitForMove(Color colorOnMove){
        while (!MoveListener.isMoveListenerComplete()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        oneMove(colorOnMove);
    }

    private void oneMove(Color color) {
        if (MoveListener.isMoveListenerComplete()) {
            try {
                moveFigureOnChessBoardLogic(MoveListener.moveListener(), color);
            } catch (MoveException e) {
                MoveListener.clearListener();
                waitForMove(color);
                e.printStackTrace();
            }
        }
        MoveListener.clearListener();
    }


    public void moveFigureOnChessBoardLogic(MoveOnChessBoard moveOnChessBoard, Color color) throws MoveException {
        Figure figure = board.getPlaceChessBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY());
        if (figure == null){
            throw new MoveException("Próba przesunięcia nieistniejącej figury");
        } else if (figure.getColor() != color) {
            throw new MoveException("Próba przesunięcia nie swojej figury");
        } else if (moveOnChessBoard.isPossibleMove(figure, board)){
            board.setPlaceOnBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY(), null);
            board.setPlaceOnBoard(moveOnChessBoard.getDestinationX(), moveOnChessBoard.getDestinationY(), figure);
            figure.setCurrentX(moveOnChessBoard.getDestinationX());
            figure.setCurrentY(moveOnChessBoard.getDestinationY());
            figure.setMoved(true);
        }
        else {
            throw new MoveException("Ten ruch jest zabrioniony");
        }
        changeFigureInPlay(board);
    }

    // Sprawdza czy jest MAT
    private boolean isCheckMate(Board board, Color color){
        assert lookForKing(color) != null;
        Figure king = lookForKing(color);
        List<int[]> bannedFields = getBannedFields(color.next());
        for (int[] bannedCoordinates : bannedFields) {
            if (king.getCurrentX() == bannedCoordinates[0] && king.getCurrentY() == bannedCoordinates[1]){ // Sprawdza czy jest szach
                if (!isKingHaveLegalMove(king, board, bannedFields)){
                    // TODO: Sprawdzanie czy da się zasłonić króla
                    SimpleChessGUI.showError("MAMY MATA dla króla:" + color);
                    return true;
                }
            }
        }
        return false;
    }

    private List<int[]> getBannedFields(Color color){
        List<int[]> bannedFields = new ArrayList<>();
        for (Figure figure : figureInPlay){
            if (figure.getColor() == color){
                for (MoveFigure potentialMove : figure.getPossibleMove()) {
                    int bannedX = figure.getCurrentX() + potentialMove.getX();
                    int bannedY = figure.getCurrentY() + potentialMove.getY();
                    MoveOnChessBoard move = new MoveOnChessBoard(figure.getCurrentX(), figure.getCurrentY(), bannedX, bannedY);

                    // wyrzuca błędy

//                    if (move.isPossibleMove(figure, board)){
//                        bannedFields.add(new int[]{bannedX, bannedY});
//                    }
                }
            }
        }
        return bannedFields;
    }

    private boolean isKingHaveLegalMove(Figure king, Board board, List<int[]> bannedFields){
        for (MoveFigure potentialMove : king.getPossibleMove()) {
            int destinationX = king.getCurrentX() + potentialMove.getX();
            int destinationY = king.getCurrentY() + potentialMove.getY();
            boolean wasBreak = false;
            for (int[] bannedField : bannedFields){
                if (bannedField[0] == potentialMove.getX() && bannedField[1] == potentialMove.getY()){
                    wasBreak = true;
                    break;
                }
            }
            if (wasBreak) {
                continue;
            }
            if (board.getPlaceChessBoard(destinationX, destinationY) == null) {
                return true;
            }
        }
        return false;
    }

    // Sprawdzenie kooordynatów króla
    private Figure lookForKing(Color color){
        for (Figure figure : figureInPlay) {
            if (figure.getClass() == King.class && figure.getColor() == color) {
                return figure;
            }
        }
        return null;
    }

    private void changeFigureInPlay(Board board){
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                Figure figure = board.getPlaceChessBoard(x,y);
                if (figure != null){
                    figureInPlay.add(figure);
                }
            }
        }
    }
}
