package android.battleship;

public class Player {
    public enum PlayerType {HUMAN, COMPUTER}

    private PlayerType playerType;

    Player(PlayerType playerType){
        this.playerType = playerType;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

}
