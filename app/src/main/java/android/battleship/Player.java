package android.battleship;

public class Player {
    public enum PlayerType {HUMAN, COMPUTER}

    private PlayerType playerType;
    private int playerNumber;

    Player(PlayerType playerType, int playerNumber) {
        this.playerType = playerType;
        this.playerNumber = playerNumber;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
