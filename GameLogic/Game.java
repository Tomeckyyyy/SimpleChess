package GameLogic;

import Figures.*;
import GUI.MoveListener;
import GUI.SimpleChessGUI;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static List<Figure> figureInPlay = new ArrayList<>();
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

        // TODO: Testowanie matowania.
        // TODO: Zmiana osób na ruchu.
        while (!isCheckMate(board, Color.WHITE) && !isCheckMate(board, Color.BLACK)) {
            try {
                moveFigureOnChessBoardLogic(new MoveOnChessBoard(1,4,3,4));
//                moveFigureOnChessBoardLogic(new MoveOnChessBoard(3,4,4,4));
//                moveFigureOnChessBoardLogic(new MoveOnChessBoard(4,4,5,4));
//                moveFigureOnChessBoardLogic(new MoveOnChessBoard(0,4,4,4));
//                moveFigureOnChessBoardLogic(new MoveOnChessBoard(7,6,5,7));
//                System.out.println(board);
                simpleChessGUI.gameRefreshGUI(board);
            } catch (MoveException e){
                e.printStackTrace();
            }

            break;  // wyłącznie do testów
        }
    }

    public void moveFigureOnChessBoardLogic(MoveOnChessBoard moveOnChessBoard) throws MoveException {
        Figure figure = board.getPlaceChessBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY());
        if (figure == null){
            throw new MoveException("Próba przesunięcia nieistniejącej figury");
        }
        if (moveOnChessBoard.isPossibleMove(figure, board)){
            Figure potentialKickedFigure = board.getPlaceChessBoard(moveOnChessBoard.getDestinationX(), moveOnChessBoard.getDestinationY());
            if (potentialKickedFigure != null){
                figureInPlay.remove(potentialKickedFigure);      // Usuwanie z figur w grze, zbitej figury
            }
            board.setPlaceOnBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY(), null);
            board.setPlaceOnBoard(moveOnChessBoard.getDestinationX(), moveOnChessBoard.getDestinationY(), figure);
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
        List<int[]> bannedFields = getBannedFields();
        for (int[] bannedCoordinates : bannedFields) {
            if (king.getCurrentX() == bannedCoordinates[0] && king.getCurrentY() == bannedCoordinates[1]){ // Sprawdza czy jest szach
                if (!isKingHaveLegalMove(king, board, bannedFields)){
                    // TODO: Sprawdzanie czy da się zasłonić króla
                    System.out.println("MAMY MATA dla króla:" + color);
                    return true;
                }
            }
        }
        return false;
    }

    private List<int[]> getBannedFields(){
        List<int[]> bannedFields = new ArrayList<>();
        for (Figure figure : figureInPlay){
            for (MoveFigure potentialMove : figure.getPossibleMove()){
                int bannedX = figure.getCurrentX() + potentialMove.getX();
                int bannedY = figure.getCurrentY() + potentialMove.getY();
                bannedFields.add(new int[]{bannedX, bannedY});
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
