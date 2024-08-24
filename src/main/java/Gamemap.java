package main.java;

import main.java.Battleship;
import main.java.StandbyShip;

import java.io.IOException;
import java.util.*;

/**
 * Representation of Ship Map
 * @author Justin Li
 * @version 0.0.1
 */
public class Gamemap {
    /**
     * Cell map
     */
    private Cell map[][];

    /**
     * Map of <Ship id, Battleship>
     */
    private Map<UUID, Battleship> board;

    /**
     * Map row
     */
    private int row;

    /**
     * Map col
     */
    private int col;

    /**
     * Default Constructor for the game map
     * @param row row num
     * @param col col num
     */
    public Gamemap(int row, int col) {
        this.row = row;
        this.col = col;
        initializeMap();
    }

    /**
     * Class initialization helper
     */
    private void initializeMap() {
        map = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = new Cell(i, j);
            }
        }
        board = new HashMap<>();
    }

    /**
     * This method is used for add ship on the map
     * @param s The standbyShip being added
     * @param c The beginning coordinate of the ship
     * @throws IOException
     */
    public void addShip(StandbyShip s, Coordinate c) throws Exception {
        if (!isValidCoordinate(c)) {
            throw new IOException("Invalid start Location: %s".formatted(c.toString()));
        }

        Coordinate end = s.calculateEnd(c);
        if (isValidCoordinate(end)) {
            Line l = new Line(c, end);
            for (Coordinate sc : l.getLineSequence()) {
                //fill in the coorinate between the start and the end as the new ships
                Cell cs = Objects.requireNonNull(getCellByCoordinate(sc));
                if (cs.getShipId() != null) {
                    throw new IOException("Ship overlapped");
                }
            }
            // update ship settings
            Battleship b = new Battleship(l, s.getID());
            addShip(b);
            return;
        }
        //if the ship can't fit
        throw new IOException("Invalid End Coordinate %s".formatted(end.toString()));
    }

    /**
     * accessor for accessing the board
     * @return board
     */
    public Map<UUID, Battleship> getboard() {
        return board;
    }

    /**
     * This method will assume incoming Battleship is valid over the map
     * used for sync between server-client
     * adds the ship onto the map
     * @param b Battleship
     */
    public void addShip(Battleship b) {
        Line l = b.getLine();
        for (Coordinate sc : l.getLineSequence()) {
            Cell cs = Objects.requireNonNull(getCellByCoordinate(sc));
            cs.setShipId(b.getID());
        }
        board.put(b.getID(), b);
    }

    /**
     * Remove the id corresponded ship from the map
     * @param id UUID ship id
     */
    public void removeShip(UUID id) {
        Battleship b = board.get(id);
        if (b != null) {
            for (Coordinate sc : b.getShipSequence()) {
                Cell cs = getCellByCoordinate(sc);
                cs.setShipId(null);
            }
            board.remove(id);
            return;
        }

        throw new RuntimeException("Try removing an non-existing ship: %s".formatted(id.toString()));
    }

    /**
     * Get the corresponding location on the grid for the cell
     * @param c Coordinate location
     * @return Cell if location is valid, null otherwise
     */
    public Cell getCellByCoordinate(Coordinate c) {
        if (isValidCoordinate(c)) {
            return map[c.getX()][c.getY()];
        }

        return null;
    }

    /**
     * Identify if given coordinate is a valid coordinate
     * @param c Coordinate
     * @return true if coordinate is on the Map, false otherwise
     */
    public boolean isValidCoordinate(Coordinate c) {
        return c.getX() >= 0 && c.getX() < row && c.getY() >=0 && c.getY() < col;
    }

    /**
     * Mark given coordinate as discovered
     * @param c Coordinate location to mark
     */
    public void mark(Coordinate c) {
        if (isValidCoordinate(c)) {
            map[c.getX()][c.getY()].setDiscovered(true);
        }
    }

    /**
     * Identify is all ships are down on current map
     * @return true if all ships are marked discovered, false otherwise
     */
    public boolean allDown() {
        for (Battleship b : board.values()) {
            for (Coordinate c : b.getShipSequence()) {
                Cell cs = getCellByCoordinate(c);
                if (!cs.isDiscovered()) {
                    return false;
                }
            }
        }

        return true;
    }
}