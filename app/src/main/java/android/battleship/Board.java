package android.battleship;

import java.util.Random;

public class Board {
    public static final int BATTLESHIPS_NR = 1;
    public static final int CRUISERS_NR = 2;
    public static final int DESTROYERS_NR = 3;
    public static final int SUBMARINES_NR = 4;

    private final static int BOARD_SIZE = 10;

    private Player player;
    private int numberOfSankShips;
    private Field[][] fields;
    private Ship[] ships;

    private Random rand = new Random();


    Board(Player player) {
        this.player = player;
        this.numberOfSankShips = 0;

        this.fields = new Field[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                fields[i][j] = new Field(new Coordinate(i, j), Field.FieldStatus.WATER);
            }
        }

        this.ships = new Ship[numberOfShips()];
        if (player.getPlayerType() == Player.PlayerType.COMPUTER) {
            setShipsRandomly();
        }
    }

    public int getNumberOfSankShips() {
        return numberOfSankShips;
    }

    public void setNumberOfSankShips(int numberOfSankShips) {
        this.numberOfSankShips = numberOfSankShips;
    }

    private static int numberOfShips(){
        return BATTLESHIPS_NR + CRUISERS_NR + DESTROYERS_NR + SUBMARINES_NR;
    }

    private boolean getRandomShipCoordinates(int shipSize, Coordinate[] coordinates) {
        boolean isVertical = rand.nextBoolean();
        if (isVertical) {
            int x = rand.nextInt(BOARD_SIZE);
            int y = rand.nextInt(BOARD_SIZE + 1 - shipSize);
            for (int i = y; i < shipSize; i++) {
                if (fields[x][i].getStatus() != Field.FieldStatus.WATER) {
                    return false;
                }
            }
            for (int i = 0; i < shipSize; i++) {
                coordinates[i] = new Coordinate(x, y + i);
            }
        } else {
            int x = rand.nextInt(BOARD_SIZE + 1 - shipSize);
            int y = rand.nextInt(BOARD_SIZE);
            for (int i = x; i < shipSize; i++) {
                if (fields[i][y].getStatus() != Field.FieldStatus.WATER) {
                    return false;
                }
            }
            for (int i = 0; i < shipSize; i++) {
                coordinates[i] = new Coordinate(x + i, y);
            }
        }
        return true;
    }

    private void setShipsRandomly() {
        int shipCounter = 0;

        for (int i = 0; i < BATTLESHIPS_NR; i++) {
            Coordinate[] coordinates = new Coordinate[Battleship.BATTLESHIP_SIZE];
            while (!getRandomShipCoordinates(Battleship.BATTLESHIP_SIZE, coordinates)){}

            for (Coordinate coordinate : coordinates) {
                fields[coordinate.getX()][coordinate.getY()].setStatus(Field.FieldStatus.SHIP);
            }
            ships[shipCounter] = new Battleship(coordinates);
            shipCounter++;
        }

        for (int i = 0; i < CRUISERS_NR; i++) {
            Coordinate[] coordinates = new Coordinate[Cruiser.CRUISER_SIZE];
            while (!getRandomShipCoordinates(Cruiser.CRUISER_SIZE, coordinates)){}

            for (Coordinate coordinate : coordinates) {
                fields[coordinate.getX()][coordinate.getY()].setStatus(Field.FieldStatus.SHIP);
            }
            ships[shipCounter] = new Cruiser(coordinates);
            shipCounter++;
        }

        for (int i = 0; i < DESTROYERS_NR; i++) {
            Coordinate[] coordinates = new Coordinate[Destroyer.DESTROYER_SIZE];
            while (!getRandomShipCoordinates(Destroyer.DESTROYER_SIZE, coordinates)){}

            for (Coordinate coordinate : coordinates) {
                fields[coordinate.getX()][coordinate.getY()].setStatus(Field.FieldStatus.SHIP);
            }
            ships[shipCounter] = new Destroyer(coordinates);
            shipCounter++;
        }

        for (int i = 0; i < SUBMARINES_NR; i++) {
            Coordinate[] coordinates = new Coordinate[Submarine.SUBMARINE_SIZE];
            while (!getRandomShipCoordinates(Submarine.SUBMARINE_SIZE, coordinates)){}

            for (Coordinate coordinate : coordinates) {
                fields[coordinate.getX()][coordinate.getY()].setStatus(Field.FieldStatus.SHIP);
            }
            ships[shipCounter] = new Submarine(coordinates);
            shipCounter++;
        }
    }
}
