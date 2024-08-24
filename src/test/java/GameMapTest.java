package test.java;

import main.java.Cell;
import main.java.Coordinate;
import main.java.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    GameMap g;

    @BeforeEach
    void setUp() {
        g = new GameMap(5, 5);
    }

    @Test
    void getCellByCoordinate() {
        Cell c = g.getCellByCoordinate(new Coordinate(1, 1));
        Cell c2 = g.getCellByCoordinate(new Coordinate(-1, -1));
        assertNotNull(c);
        assertEquals(1, c.getX());
        assertEquals(1, c.getY());
        assertNull(c2);
    }

    @Test
    void isValidCoordinate() {
        assertTrue(g.isValidCoordinate(new Coordinate(1, 1)));
        assertFalse(g.isValidCoordinate(new Coordinate(10, 10)));
        assertFalse(g.isValidCoordinate(new Coordinate(-1, -1)));
    }

    @Test
    void mark() {
        Coordinate coord = new Coordinate(1, 1);
        assertFalse(g.getCellByCoordinate(coord).isDiscovered());
        g.mark(coord);
        assertTrue(g.getCellByCoordinate(coord).isDiscovered());
    }

    @Test
    void allDown() {
        // Assuming allDown checks if all cells are discovered or not
        assertFalse(g.allDown());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g.mark(new Coordinate(i, j));
            }
        }
        assertTrue(g.allDown());
    }
}
