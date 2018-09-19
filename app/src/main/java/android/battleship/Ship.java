package android.battleship;

public class Ship {
    private final int size;
    private boolean sunk;
    private Coordinate[] coordinates;

    Ship(int size, Coordinate[] coordinates){
        this.size = size;
        this.sunk = false;
        this.coordinates = coordinates;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public int getSize() {
        return size;
    }
}
