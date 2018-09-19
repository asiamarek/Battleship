package android.battleship;

public class Destroyer extends Ship {
    public static final int DESTROYER_SIZE = 2;

    Destroyer(Coordinate[] coordinates) {
        super(DESTROYER_SIZE, coordinates);
    }
}
