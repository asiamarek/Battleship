package android.battleship;

public class Player {
    public enum PlayerType {HUMAN, COMPUTER}

    private String playerName;
    private PlayerType playerType;

    Player(String playerName, PlayerType playerType){
        this.playerName = playerName;
        this.playerType = playerType;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public String getPlayerName() {
        return playerName;
    }
}
