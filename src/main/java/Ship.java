package main.java;
import java.util.*;
import java.io.IOException;


/**
 * Generic Ship representation
 * A ship should have an id and a length
 * @author Justin Li
 * @version 0.0.1
 */
public class Ship {
    //classes that handles the ships, a ship should have an ID and a length
    int length = 1;
    UUID shipID;
    //class variables for the length of the ship and its ID

    public Ship(){}
    //default constructor of the ship

    /**
     * Default constructor
     * will generate random UUID as ship id
     * @param l length of ship
     */
    public Ship(int l){
        this.length = l;
        this.shipID = UUID.randomUUID();
    }

    /**
     * Ship Copy Constructor
     * @param l length of ship
     * @param id shipId
     */
    public Ship(int l, UUID id){
        this.length = l;
        this.shipID = id;
    }

    /**
     * Get the id of the ship
     * @return UUID ship id
     */
    public UUID getID(){
        //accessor for the ID
        return this.shipID;
    }

    /**
     * Set the length of the Ship
     * @param l Length of Ship
     * @throws IOException 
     * @throws IOExcetion
     */
    public void setLength(int l) throws IOException{
        if(l < 1){
            throw new IOException("The length of the ship must be at least one");
        }
        this.length = l;
    }

    /**
     * Get the length of the ship
     * @return int length of ship
     */
    public int getLength(){
        return this.length;
    }

    /**
     * Checks if two ships are equal, that is if they have the same UUID and length
     * overwrites the equals method
     * @param o the other ship
     * @return boolean whether the ships are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Ship)){
            return false;  
        }
        Ship ship = (Ship) o;
        return getLength() == ship.getLength() && getID() == ship.getID();
    }

    /**
     * Generates the Hashcode for a ship object
     * @return int of the hashcode 
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.getLength(), this.getID());
    }

    /**
     * Transforms the ship to a string for output
     * @return String of the ship
     */
    public String toString(){
        return "{%s %s}".formatted(this.length, this.shipID);
    }

    /**
     * Set this with String representation of Ship
     * @param s String representation of ship
     * @throws IOException 
     * @throws NumberFormatException 
     */
    protected void setShip(String s) throws NumberFormatException, IOException {
        String[] params = s.substring(s.indexOf('{') + 1, s.indexOf('}')).split(" ");
        setLength(Integer.parseInt(params[0]));
        this.shipID = UUID.fromString(params[1]);
    }

    /**
     * Get Ship Object from String representation (toString)
     * @param s String representation of Ship
     * @return ship Ship
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public Ship valueOf(String s) throws NumberFormatException, IOException {
        Ship ship = new Ship();
        ship.setShip(s);
        return ship;
    }
}
