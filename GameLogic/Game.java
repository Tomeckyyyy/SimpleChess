package GameLogic;

import Figures.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    static List<Figure> figureInPlay = new ArrayList<Figure>();
    static List<Figure> figureOutOfGame = new ArrayList<Figure>();
    boolean isCheckMate = false;

    public Game() {
        Board board = new Board();
        board.setPlaceOnBoard(0, 0, new King(0, 0, Color.WHITE));
        board.setPlaceOnBoard(0, 1, new Queen(0,1, Color.WHITE));
        board.setPlaceOnBoard(3,3,new Queen(3,3,Color.WHITE));
        board.setPlaceOnBoard(2,3, new Pawn(3,2, Color.WHITE));
        board.setPlaceOnBoard(7,7, new Knight(7,7, Color.WHITE));
//Sprawdza czy dobrze zrobiona jest lista possible move:
//        for (MoveFigure x : board.getPlaceChessBoard(0,1).getPossibleMove()){
//            System.out.println(x);
//        }
//        System.out.println(board.getPlaceChessBoard(0,0).getClass());
        while (!isCheckMate) {
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(0,0,1,1), board);
            System.out.println(board);
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(0,1,2,3), board);
            System.out.println(board);
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(3,3,0,3), board);
            moveFigureOnChessBoardLogic(new MoveOnChessBoard(7,7,5,6), board);
            System.out.println(board);
            isCheckMate = true;
        }
    }

    public void moveFigureOnChessBoardLogic(MoveOnChessBoard moveOnChessBoard, Board board) {
        Figure figure = board.getPlaceChessBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY());
        if (moveOnChessBoard.isPossibleMove(figure, board)){
            board.setPlaceOnBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY(), null);
            board.setPlaceOnBoard(moveOnChessBoard.getDestinationX(), moveOnChessBoard.getDestinationY(), figure);
        }
    }



    public List<Figure> getFigureInPlay() {
        return figureInPlay;
    }

    public List<Figure> getFigureOutOfGame() {
        return figureOutOfGame;
    }

}
