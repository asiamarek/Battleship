package android.battleship;

public class Cruiser extends Ship {
    public static final int CRUISER_SIZE = 3;

    Cruiser(Coordinate[] coordinates) {
        super(CRUISER_SIZE, coordinates);
    }
}
