package android.battleship;

public class Ship {
    private final int size;
    private int hits;
    private Coordinate[] coordinates;

    Ship(int size, Coordinate[] coordinates){
        this.size = size;
        this.hits = 0;
        this.coordinates = coordinates;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public boolean isSunk() {
        return (hits == size);
    }

    public void hit(){
        hits++;
    }

    public int getSize() {
        return size;
    }
}
