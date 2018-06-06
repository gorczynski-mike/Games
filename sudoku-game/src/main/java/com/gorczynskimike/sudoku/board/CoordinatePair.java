package com.gorczynskimike.sudoku.board;

/**
 * Simple immutable class to hold a pair of x,y coordinates
 */
public final class CoordinatePair {

    private final int x;
    private final int y;

    public CoordinatePair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

}