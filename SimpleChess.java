import GameLogic.Game;

class SimpleChess {
    public static void main(String[] args) {
        GUI.SimpleChessGUI simplechessGUI = new GUI.SimpleChessGUI();
        Game game = new Game(simplechessGUI);
    }
}
