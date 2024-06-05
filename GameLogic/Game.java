package GameLogic;

import Figures.*;
import GUI.SimpleChessGUI;

import java.util.ArrayList;
import java.util.List;

public class Game {
    static List<Figure> figureInPlay = new ArrayList<>();
    static List<Figure> figureOutOfGame = new ArrayList<>();


    public Game(SimpleChessGUI simpleChessGUI) {
        Board board = new Board();
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

        System.out.println(board.getPlaceChessBoard(0,0));
        System.out.println(board);
        board.setPlaceOnBoard(0, 1, new Queen(0,1, Color.WHITE));
        board.setPlaceOnBoard(3,3,new Queen(3,3,Color.WHITE));
        board.setPlaceOnBoard(2,3, new Pawn(2,3, Color.WHITE));
        board.setPlaceOnBoard(7,7, new Knight(7,7, Color.WHITE));
        while (!isCheckMate(board, Color.WHITE) && !isCheckMate(board, Color.BLACK)) {
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(0,0,1,1), board);
            System.out.println(board);
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(0,1,2,3), board);
            System.out.println(board);
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(3,3,0,3), board);
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(7,7,5,6), board);
            System.out.println(board);
            simpleChessGUI.gameRefreshGUI(board);
            break;
        }
    }

    public void moveFigureOnChessBoardLogic(MoveOnChessBoard moveOnChessBoard, Board board) {
        Figure figure = board.getPlaceChessBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY());
        if (moveOnChessBoard.isPossibleMove(figure, board)){
            board.setPlaceOnBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY(), null);
            board.setPlaceOnBoard(moveOnChessBoard.getDestinationX(), moveOnChessBoard.getDestinationY(), figure);
        }
    }

    // Sprawdza czy jest MAT
    private boolean isCheckMate(Board board, Color color){
        int[] king = lookForKing(board, color);
        assert king != null;
        for (MoveFigure potentialMove : board.getPlaceChessBoard(king[0], king[1]).getPossibleMove()){
            continue;
        }
        return false;
    }

    // Sprawdzenie kooordynatów króla
    private int[] lookForKing(Board board, Color color){
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                Figure figure = board.getPlaceChessBoard(x,y);
                int[] coordinates = {x,y};
                if (figure == null){
                    continue;
                }
                if (figure.getClass() == King.class && figure.getColor() == color){
                    return coordinates;
                }
            }
        }
        return null;
    }

    public List<Figure> getFigureInPlay() {
        return figureInPlay;
    }

    public List<Figure> getFigureOutOfGame() {
        return figureOutOfGame;
    }

}
