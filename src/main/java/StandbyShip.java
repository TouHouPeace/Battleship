package main.java;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class StandbyShip extends Ship implements Directable {
    private Direction direction = Direction.HORIZONTAL;

    /**
     * Default constructor for the Standby Ship
     * with length 1 and random UUID
     */
    public StandbyShip() {}

    /**
     * Generate ship with given length,
     * will generate random UUID
     * @param length ship length
     */
    public StandbyShip(int length) {
        super(length);
    }

    /**
     * Copy Constructor
     * creates the new ship object with the exact same attributes
     * @param length length of the ship
     * @param direction direction of the ship
     * @param uuid ship id
     */
    public StandbyShip(int length, Direction direction, UUID uuid)  {
        super(length);
        setDirection(direction);
    }

    /**
     * Accessor for direction
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * modifier for the direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Calculate corresponded Coordinate of the end of the ship from given Coordinate
     * @param start The start coordinate of the ship
     * @return the end Coordinate of the ship
     */
    public Coordinate calculateEnd(Coordinate start) {
        if (direction == Direction.HORIZONTAL) {
            return new Coordinate(start.getX() + getLength() - 1, start.getY());
        } else {
            return new Coordinate(start.getX(), start.getY() + getLength() - 1);
        }
    }

    /**
     * Generate the string representation of the standby Ship
     * @return string the string representation
     */
    @Override
    public String toString() {
        return "<%s %s>".formatted(direction, super.toString());
    }

    /**
     * Set the standby ship to one represented by the data
     * @param s
     * @throws IOException 
     * @throws NumberFormatException 
     */
    @Override
    public void setShip(String s) throws NumberFormatException, IOException {
        String[] arr = s.substring(s.indexOf('<') + 1, s.indexOf('>')).split(" ", 2);
        Direction d = Direction.valueOf(arr[0]);
        setDirection(d);
        super.setShip(arr[1]);
    }

    /**
     * Factory method generate StandbyShip from String representation
     * parses the string and gets the ship
     * @param s String Representation
     * @return StandbyShip
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public static StandbyShip ValueOf(String s) throws NumberFormatException, IOException {
        StandbyShip ship = new StandbyShip();
        ship.setShip(s);
        return ship;
    }

    /**
     * overrides the equals method to make them work for to standby ships
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof StandbyShip)){
            return false;
        }
        if(!super.equals(o)){
            return false;
        }
        StandbyShip that = (StandbyShip) o;
        return getDirection() == that.getDirection();
    }

    /**
     * Generates the hashcode for a standby ship
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDirection());
    }
}
