package main.java;
import java.util.*;

/**
 * This class implements a basic elements of battleship, the cells on the grid
 * @author Justin Li
 * @version 0.0.1
 */
public class Cell extends Coordinate implements Discoverable {
    private boolean discovered = false;
    private UUID shipId;

    /**
     * Default constructor of the cell, a location in the grid
     * @param i Row the cell located
     * @param j Col the Cell located
     */
    public Cell(int i, int j) {
        super(i, j);
    }

    /**
     * returns the shipID of the given cell
     * @return shipID
     */
    public UUID getShipId() {
        return shipId;
    }

    /**
     * Test if cell contains part of any ship, shipID will be null if the location on the grid is not initialzied as a ship
     * @return true if a ship exists, false otherwise
     */
    public boolean hasShip() {
        return this.shipId != null;
    }

    /**
     * Test if cell is discovered
     * @return true if a ship exists and is discovered, otherwise falso
     */
    public boolean isDiscovered() {
        return discovered;
    }

    /**
     * Sets the discover state of the ship
     */
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    /**
     * Set the shipID of the given cell
     * @param shipId
     */
    public void setShipId(UUID shipId) {
        this.shipId = shipId;
    }
}