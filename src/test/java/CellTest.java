package test.java;

import main.java.Cell;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void getShipId() {
        Cell x = new Cell(1, 1);
        assertNull(x.getShipId());
    }

    @Test
    void hasShip() {
        Cell x = new Cell(1, 1);
        assertFalse(x.hasShip());
        x.setShipId(UUID.randomUUID());
        assertTrue(x.hasShip());
    }

    @Test
    void isDiscovered() {
        Cell x = new Cell(1, 1);
        assertFalse(x.isDiscovered());
        x.setDiscovered(true);
        assertTrue(x.isDiscovered());
    }
}
