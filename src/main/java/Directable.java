package main.java;

public interface Directable {
    public static enum Direction {VERTICAL, HORIZONTAL};
    Direction getDirection();
}