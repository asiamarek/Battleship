package android.battleship;

public class Battleship extends Ship {
    public static final int BATTLESHIP_SIZE = 4;

    Battleship(Coordinate[] coordinates) { super(BATTLESHIP_SIZE, coordinates); }
}
