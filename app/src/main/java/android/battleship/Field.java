package android.battleship;

public class Field {
    public enum FieldStatus {WATER, HIT_WATER, HIT_SHIP, BATTLESHIP, CRUISER, DESTROYER, SUBMARINE}

    private Coordinate coordinate;
    private FieldStatus status;

    Field(Coordinate coordinate, FieldStatus status){
        this.coordinate = coordinate;
        this.status = status;
    }

    public FieldStatus getStatus() {
        return status;
    }

    public void setStatus(FieldStatus status) {
        this.status = status;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
