package android.battleship;

public class Game {
    public enum GameStatus {FINISHED, ACTIVE}

    private Board myBoard;
    private Board opponentsBoard;

    private Player myPlayer;
    private Player opponent;

    private GameStatus status;

    Game(){
        this.status = GameStatus.ACTIVE;
        this.myPlayer = new Player(Player.PlayerType.HUMAN);
        this.opponent = new Player(Player.PlayerType.COMPUTER);
        this.myBoard = new Board(myPlayer);
        this.opponentsBoard = new Board(opponent);
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
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
