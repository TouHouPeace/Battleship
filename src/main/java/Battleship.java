package main.java;
import main.java.Coordinate;
import main.java.Line;

import java.util.*;

/**
 * Basic BattleShip representation
 * @author Justin Li
 * @version 0.0.1
 */
public class Battleship extends StandbyShip {
    Line line;

    /**
     * Default Constructor
     * @param line Line where the ship lies
     * @param uuid ship id
     */
    public Battleship(Line line, UUID uuid) {
        super(line.getLength(), line.getDirection(), uuid);
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    /**
     * Get the beginning of the ship
     * @return Coordinate
     */
    public Coordinate getHead() {
        return line.getStart();
    }

    /**
     * Get List of Coordinates for which the ship occupies
     * @return List of Coordinates
     */
    public List<Coordinate> getShipSequence() {
        return line.getLineSequence();
    }
}