package main.java;
import java.util.*;

/**
 * This is a specific line implementation for this game:
 * The basic rule for the game states that all ship must be a straight line
 * @author Justin Li
 * @version 0.0.1
 */
public class Line implements Directable {
    /** two endpoint of a line **/
    private Coordinate start;
    private Coordinate end;

    /**
     * Default Constructor
     * please ensure two coordinates follow the standard
     * @param s Start Coordinate
     * @param e End Coordinate
     */
    public Line(Coordinate s, Coordinate e) {
        this.start = s;
        this.end = e;
    }

    /**
     * The distance between two endpoints, effectively the length of the ship
     * @return distance between two point
     */
    public int getLength() {
        if (start.getX() == end.getX()) {
            //if the ship is horizontal
            return Math.abs(start.getY() - end.getY()) + 1;
        }
        //if the ship is vertically placed
        return Math.abs(start.getX() - end.getX()) + 1;
    }

    /**
     * Accessor for the end of the line
     * @return end
     */
    public Coordinate getEnd() {
        return end;
    }

    /**
     * Accessor for the beginning of the line
     * @return
     */
    public Coordinate getStart() {
        return start;
    }

    /**
     * Return all coordinate between the start and the end, includsive
     * @return List of Coordinate
     */
    public ArrayList<Coordinate> getLineSequence() {
        ArrayList<Coordinate> arr = new ArrayList<>();
        if (start.getX() == end.getX()) {
            for (int i = start.getY(); i <= end.getY(); i++) {
                arr.add(new Coordinate(start.getX(), i));
            }
        } 
        else {
            for (int i = start.getX(); i <= end.getX(); i++) {
                arr.add(new Coordinate(i, start.getY()));
            }
        }
        return arr;
    }

    /**
     * Get the direction of the line (horizontal or vertical)
     * @return Direction Horizontal or Vertical
     */
    public Direction getDirection() {
        if (start.getX() == end.getX()) {
            return Direction.VERTICAL;
        } 
        else if (start.getY() == end.getY()) {
            return Direction.HORIZONTAL;
        }
        throw new RuntimeException("Coordinate is either vertical or horizontal, impossible");
    }

    /**
     * This method identify if given coordinate is within the line range.
     * Even though it is a line, imagine it as a rectangle
     * The function will return true if that is the case
     * @param c Coordinate to identify
     * @return true if it is within the space, false otherwise
     */
    public boolean isContained(Coordinate c) {
        return (c.getX() >= start.getX() && c.getX() <= end.getX()) || (c.getY() >= start.getY() && c.getY() <= end.getY());
    }

    /**
     * Identify if given Coordinate has same X or Y with the line
     * If the line is parallel with the given one.
     * either on the same y-level or the same x-level
     * @param c
     * @return
     */
    public boolean isParallel(Coordinate c) {
        return c.getY() == start.getY() || c.getX() == start.getX();
    }

    /**
     * Identify if given coordinate is on the line inclusive
     * The coordinate is within the range of the two rectangles highlighted by the contain function
     * The coordinate is paraelle with the given coordinate
     * @param c Coordinate
     * @return true if it is on the line, false otherwise
     */
    public boolean isBetween(Coordinate c) {
        return isContained(c) && isParallel(c);
    }

    /**
     * Test if given line will overlap with self
     * @param l Line to test
     * @return true if they will overlap, false otherwise
     */
    public boolean isOverLapped(Line l) {
        // Same level overlap
        if (isBetween(l.getStart()) || isBetween(l.getEnd())) {
            return true;
        }

        if (getDirection() != l.getDirection()) {
            // @TODO May optimize with better math
            for (Coordinate x : getLineSequence()) {
                for (Coordinate y : l.getLineSequence()) {
                    if (x.equals(y)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Overrides the equals method
     * @param o the line
     * @return boolean whether the lines are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getStart().equals(line.getStart()) && getEnd().equals(line.getEnd());
    }

    /**
     * Overrides the hashcode method
     * @return int the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }
}