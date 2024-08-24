package main.java;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerBoard extends GameBoard {
    /** Score representation **/
    private int selfScore = 0;
    private int enemyScore = 0;

    /**
     * Initialization PlayerBoard
     * @param row map row
     * @param col map col
     * @param timeLimit round time limit (second)
     * @param ships List of StandbyShips to be used in the game
     */
    public PlayerBoard(int row, int col, int timeLimit, List<StandbyShip> ships) {
        super(row, col, ships, timeLimit);
    }

    /**
     * Default constructor
     * Please follow parent class initialization process
     */
    public PlayerBoard() {
        super(); // Ensure parent class initialization
    }

    /**
     * Get Cell representation by Player and Coordinate
     * @param c Coordinate Location
     * @param p PlayerTarget SELF or ENEMY
     * @return Cell corresponded on the GameMap
     */
    public Cell getCell(Coordinate c, PlayerTarget p) {
        return switch(p) {
            case SELF -> self.getCellByCoordinate(c);
            case ENEMY -> enemy.getCellByCoordinate(c);
        };
    }

    /** getters and mutators **/

    public int getEnemyScore() {
        return enemyScore;
    }

    public int getSelfScore() {
        return selfScore;
    }

    public void incrementSelfScore() {
        selfScore++;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    /**
     * Ensure enemy ships are equal to the self ships
     * on the map
     * @TODO UUID comparison
     * @return true if equal number of ships, false otherwise
     */
    public boolean equalShips() {
        return self.getboard().size() == enemy.getboard().size();
    }
}
