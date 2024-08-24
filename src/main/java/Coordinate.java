package main.java;
import java.util.*;

/**
 * A Generic 2D Coordinate Implementation
 * @author Justin Li
 * @version 0.0.1
 */
public class Coordinate {
    private int x = 0;
    private int y = 0;
    //private variables representing the x and y coordinate on a 2D board

    public Coordinate(){}
    //default constructor of the ship

    /**
     * Default constructor
     * @param x1 x coordinate
     * @param y1 y coordinate
     */
    public Coordinate(int x1, int y1){
        x = x1;
        y = y1;
    }

    /**
     * The accessor for the x coordinate
     * @return x 
     */
    public int getX(){
        return this.x;
    }

    /**
     * The accessor for the y coordinate
     * @return y
     */
    public int getY(){
        return this.y;
    }

    /**
     * Overrides the equals method to check if two coordinates are equal
     * @param o the other coordinate
     * @return boolean whether the ships are equal
     */
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Coordinate)){
            return false;
        }
        Coordinate other = (Coordinate)o;
        return this.getX() == other.getX() && this.getY() == other.getY();
    }


    /**
     * Generates the Hashcode for a coordinate object
     * @return int of the hashcode 
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    /**
     * Transforms the coordinate to a string for output
     * @return String of the coordinate
     */
    @Override
    public String toString() {
        return "[%d %d]".formatted(x, y);
    }

    /**
     * Parse String representation of Coordinate and generate Coordinate Object
     * @param s String Representation
     * @return coordinate
     */
    public static Coordinate valueOf(String s) {
        String[] nums = s.substring(s.indexOf('[') + 1, s.indexOf(']')).split(" ");
        int row = Integer.parseInt(nums[0]);
        int col = Integer.parseInt(nums[1]);
        return new Coordinate(row, col);
    }
}
