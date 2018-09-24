package android.battleship;

public class Game {
    public enum GameMode {ONE_PLAYER, TWO_PLAYERS}

    private Board myBoard;
    private Board opponentsBoard;

    private Player myPlayer;
    private Player opponent;

    private boolean myTurn;

    Game(GameMode mode){
        this.myPlayer = new Player(Player.PlayerType.HUMAN, 1);
        this.myTurn = true;
        if(mode == GameMode.ONE_PLAYER){
            this.opponent = new Player(Player.PlayerType.COMPUTER,2);
        } else {
            this.opponent = new Player(Player.PlayerType.HUMAN,2);
        }
        this.myBoard = new Board(myPlayer);
        this.opponentsBoard = new Board(opponent);
    }

    public boolean theEnd(){
        return (myBoard.getnumberOfSunkenShips() == Board.numberOfShips() ||
                opponentsBoard.getnumberOfSunkenShips() == Board.numberOfShips());
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getOpponentsBoard() {
        return opponentsBoard;
    }

    public GameMode getMode() {
        if( opponent.getPlayerType() == Player.PlayerType.COMPUTER){
            return GameMode.ONE_PLAYER;
        } else {
            return GameMode.TWO_PLAYERS;
        }
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}
