package android.battleship;

public class Game {
    private Board myBoard;
    private Board opponentsBoard;

    private Player myPlayer;
    private Player opponent;

    Game(){
        this.myPlayer = new Player(Player.PlayerType.HUMAN);
        this.opponent = new Player(Player.PlayerType.COMPUTER);
        this.myBoard = new Board(myPlayer);
        this.opponentsBoard = new Board(opponent);
    }

    public boolean theEnd(){
        return (myBoard.getNumberOfSankShips() == Board.numberOfShips() ||
                opponentsBoard.getNumberOfSankShips() == Board.numberOfShips());
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getOpponentsBoard() {
        return opponentsBoard;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public Player getOpponent() {
        return opponent;
    }
}
