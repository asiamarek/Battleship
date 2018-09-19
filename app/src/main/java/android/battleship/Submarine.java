package android.battleship;

public class Submarine extends Ship {
    public static final int SUBMARINE_SIZE = 1;

    Submarine(Coordinate[] coordinates) {
        super(SUBMARINE_SIZE, coordinates);
    }
}
