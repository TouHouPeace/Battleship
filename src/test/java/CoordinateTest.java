package test.java;

import main.java.Coordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void getX() {
        Coordinate c = new Coordinate(1, 2);
        assertEquals(2, c.getY());
        assertEquals(1, c.getX());
    }

    @Test
    void testEquals() {
        Coordinate a1 = new Coordinate(1, 1);
        Coordinate a2 = new Coordinate(2, 1);
        Coordinate a3 = new Coordinate(1, 2);
        Coordinate a4 = new Coordinate(1, 1);
        assertNotEquals(a2, a1);
        assertNotEquals(a3, a2);
        assertNotEquals(a4, a3);
        assertEquals(a1, a4);
    }

    @Test
    void valueOf() {
        Coordinate x = Coordinate.valueOf("[1 1]");
        assertEquals(new Coordinate(1, 1), x);
    }
}
