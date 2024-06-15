package GameLogic;

import Figures.*;
import GUI.MoveListener;
import GUI.SimpleChessGUI;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Figure> figureInPlay = new ArrayList<>();
    private final BannedFields bannedFieldsBlack = new BannedFields(Color.BLACK);
    private final BannedFields bannedFieldsWhite = new BannedFields(Color.WHITE);
    private Board board = new Board();
    private final SimpleChessGUI simpleChessGUI;


    public Game(SimpleChessGUI simpleChessGUI) {
        if (BoardListener.getBoard() != null){
            board = BoardListener.getBoard();
        } else {
            setStartBoard();
//            setTestPawnChange();
        }
        this.simpleChessGUI = simpleChessGUI;
        simpleChessGUI.gameRefreshGUI(board);
        Color colorOnMove = Color.WHITE;
        changeFigureInPlay(board);
        getBannedFields(Color.WHITE);
        getBannedFields(Color.BLACK);
        while (!isCheckMate(board, Color.WHITE) && !isCheckMate(board, Color.BLACK)) {
            waitForMove(colorOnMove);
            colorOnMove = colorOnMove.next();
            System.out.println(colorOnMove);
            simpleChessGUI.gameRefreshGUI(board);
        }
    }

    private void setStartBoard() {
        board.setPlaceOnBoard(0, 3, new King(0, 3, Color.WHITE));
        board.setPlaceOnBoard(0, 0, new Rook(0, 0, Color.WHITE));
        board.setPlaceOnBoard(0, 1, new Knight(0, 1, Color.WHITE));
        board.setPlaceOnBoard(0, 2, new Bishop(0, 2, Color.WHITE));
        board.setPlaceOnBoard(0, 4, new Queen(0, 4, Color.WHITE));
        board.setPlaceOnBoard(0, 5, new Bishop(0, 5, Color.WHITE));
        board.setPlaceOnBoard(0, 6, new Knight(0, 6, Color.WHITE));
        board.setPlaceOnBoard(0, 7, new Rook(0, 7, Color.WHITE));

        board.setPlaceOnBoard(7, 3, new King(7, 3, Color.BLACK));
        board.setPlaceOnBoard(7, 0, new Rook(7, 0, Color.BLACK));
        board.setPlaceOnBoard(7, 1, new Knight(7, 1, Color.BLACK));
        board.setPlaceOnBoard(7, 2, new Bishop(7, 2, Color.BLACK));
        board.setPlaceOnBoard(7, 4, new Queen(7, 4, Color.BLACK));
        board.setPlaceOnBoard(7, 5, new Bishop(7, 5, Color.BLACK));
        board.setPlaceOnBoard(7, 6, new Knight(7, 6, Color.BLACK));
        board.setPlaceOnBoard(7, 7, new Rook(7, 7, Color.BLACK));

        for (int i = 0; i < 8; i++) {
            board.setPlaceOnBoard(1, i, new Pawn(1, i, Color.WHITE));
            board.setPlaceOnBoard(6, i, new Pawn(6, i, Color.BLACK));
        }
    }

    private void setTestPawnChange() {
        board.setPlaceOnBoard(6, 0, new Pawn(6, 0, Color.WHITE));
        board.setPlaceOnBoard(7, 1, new Pawn(7, 1, Color.BLACK));
        board.setPlaceOnBoard(7, 3, new King(7, 3, Color.BLACK));
        board.setPlaceOnBoard(6, 5, new King(6, 5, Color.WHITE));
    }

    private void waitForMove(Color colorOnMove) {
        boolean x = simpleChessGUI.getOff();
        while (!MoveListener.isMoveListenerComplete()) {
            x = simpleChessGUI.getOff();
            if (x) break;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!x) {
            oneMove(colorOnMove);
        }
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
        if (figure == null) {
            throw new MoveException("Trying to move a non-existent piece");
        } else if (figure.getColor() != color) {
            throw new MoveException("Trying to move a piece that isn't yours");
        } else if (moveOnChessBoard.isPossibleMove(figure, board)) {
            if (figure.getClass() == King.class) {
                for (int[] bannedCoordinates : color.next() == Color.BLACK ? bannedFieldsBlack : bannedFieldsWhite) {
                    if (moveOnChessBoard.getDestinationX() == bannedCoordinates[0] && moveOnChessBoard.getDestinationY() == bannedCoordinates[1]) {
                        throw new MoveException("This move is prohibited");
                    }
                }
            }
            if (moveOnChessBoard.isPawnPromotion(figure)) {
                char symbolNewFigure = simpleChessGUI.showPromotionDialog();
                int x = moveOnChessBoard.getDestinationX();
                int y = moveOnChessBoard.getDestinationY();
                switch (symbolNewFigure) {
                    case 'R':
                        board.setPlaceOnBoard(x, y, new Rook(x, y, figure.getColor()));
                        break;
                    case 'B':
                        board.setPlaceOnBoard(x, y, new Bishop(x, y, figure.getColor()));
                        break;
                    case 'K':
                        board.setPlaceOnBoard(x, y, new Knight(x, y, figure.getColor()));
                        break;
                    default:
                        board.setPlaceOnBoard(x, y, new Queen(x, y, figure.getColor()));
                        break;
                }
                board.setPlaceOnBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY(), null);
            } else {
                board.setPlaceOnBoard(moveOnChessBoard.getStartX(), moveOnChessBoard.getStartY(), null);
                board.setPlaceOnBoard(moveOnChessBoard.getDestinationX(), moveOnChessBoard.getDestinationY(), figure);
                figure.setCurrentX(moveOnChessBoard.getDestinationX());
                figure.setCurrentY(moveOnChessBoard.getDestinationY());
                BoardListener.setBoard(board);
                figure.setMoved(true);
                changeFigureInPlay(board);
                getBannedFields(Color.WHITE);
                getBannedFields(Color.BLACK);
            }
        } else {
            throw new MoveException("This move is prohibited");
        }
    }

    // Sprawdza czy jest MAT
    private boolean isCheckMate(Board board, Color color) {
        King king = lookForKing(color);
        assert king != null;
        if (isCheck(king, color.next())) {
            if (!isKingHaveLegalMove(king, board, color)) {
                if (!isItPossibleToCoverKing(color)) {
                    SimpleChessGUI.showError("Check Mate on " + color + "king");
                    return true;
                }
            }
            SimpleChessGUI.showError("Check");
        }
        return false;
    }

    private boolean isCheck(King king, Color color) {
        for (int[] bannedCoordinates : color == Color.BLACK ? bannedFieldsBlack : bannedFieldsWhite) {
            if (king.getCurrentX() == bannedCoordinates[0] && king.getCurrentY() == bannedCoordinates[1]) { // Sprawdza czy jest szach
                return true;
            }
        }
        return false;
    }

    private boolean isItPossibleToCoverKing(Color color) {
        for (Figure figure : figureInPlay) {
            if (figure.getColor() == color) {
                List<MoveFigure> possibleMoves = new ArrayList<>(figure.getPossibleMove());
                for (MoveFigure potentialMove : possibleMoves) {
                    int potentialX = figure.getCurrentX() + potentialMove.getX();
                    int potentialY = figure.getCurrentY() + potentialMove.getY();
                    if (potentialX > 7 || potentialX < 0 || potentialY > 7 || potentialY < 0) {
                        continue;
                    }
                    MoveOnChessBoard move = new MoveOnChessBoard(figure.getCurrentX(), figure.getCurrentY(), potentialX, potentialY);
                    if (move.getFigureMoveThroughMoveFigure(board) == null) {
                        King king = lookForKing(color);
                        assert king != null;
                        if (isCheck(king, color)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void getBannedFields(Color color) {
        if (color == Color.BLACK) {
            bannedFieldsBlack.clear();
        } else {
            bannedFieldsWhite.clear();
        }
        for (Figure figure : figureInPlay) {
            if (figure.getColor() == color) {
                List<MoveFigure> possibleMoves = new ArrayList<>(figure.getPossibleMove());
                for (MoveFigure potentialMove : possibleMoves) {   // ConcurrentModificationEception if "MoveFigure potentialMove : figure.getPossibleMove()"
                    int bannedX = figure.getCurrentX() + potentialMove.getX();
                    int bannedY = figure.getCurrentY() + potentialMove.getY();
                    if (bannedX > 7 || bannedX < 0 || bannedY > 7 || bannedY < 0) {
                        continue;
                    }
                    MoveOnChessBoard move = new MoveOnChessBoard(figure.getCurrentX(), figure.getCurrentY(), bannedX, bannedY);
                    // Tutaj jest problem ---- Przez to zżera pamięć jak łakocie
                    if (move.getFigureMoveThroughMoveFigure(board) == null || move.getFigureMoveThroughMoveFigure(board).charAt(0) == '0') {          // TO .isPossibleMove() TO ZABIERA PAMIĘĆ, chyba jest wyłowywana rekurencyjnie metoda getBannedFields
                        boolean isStoped = false;                                     // Kolejny problem z tym że król sam jakby się zasłaniał
                        for (int[] x : color == Color.BLACK ? bannedFieldsBlack : bannedFieldsWhite) {
                            if (x[0] == bannedX && x[1] == bannedY) {
                                isStoped = true;
                                break;
                            }
                        }
                        if (!isStoped) {
                            if (color == Color.BLACK) {
                                bannedFieldsBlack.add(new int[]{bannedX, bannedY});
                            } else {
                                bannedFieldsWhite.add(new int[]{bannedX, bannedY});
                            }
                        }
                    }
                }
                // Tutaj jest problem ---- Przez to zżera pamięć jak łakocie
            }
        }
    }

    private boolean isKingHaveLegalMove(Figure king, Board board, Color color) {
        for (MoveFigure potentialMove : king.getPossibleMove()) {
            int destinationX = king.getCurrentX() + potentialMove.getX();
            int destinationY = king.getCurrentY() + potentialMove.getY();
            if (destinationX > 7 || destinationX < 0 || destinationY > 7 || destinationY < 0) {
                continue;
            }
            boolean wasBreak = false;
            for (int[] bannedField : color == Color.BLACK ? bannedFieldsBlack : bannedFieldsWhite) {
                if (bannedField[0] == potentialMove.getX() && bannedField[1] == potentialMove.getY()) {
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

    private King lookForKing(Color color) {
        for (Figure figure : figureInPlay) {
            if (figure.getClass() == King.class && figure.getColor() == color) {
                return (King) figure;
            }
        }
        return null;
    }

    private void changeFigureInPlay(Board board) {
        figureInPlay.clear();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Figure figure = board.getPlaceChessBoard(x, y);
                if (figure != null) {
                    figureInPlay.add(figure);
                }
            }
        }
    }
}
