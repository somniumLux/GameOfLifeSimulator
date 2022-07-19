package org.example;

public enum CellState {

    ALIVE_CELL(1),
    DEAD_CELL(0);

    private final int state;
    CellState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}