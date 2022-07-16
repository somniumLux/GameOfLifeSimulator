package org.example;

public class Simulation {

    int width;
    int height;
    int [][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if(board[x][y] == CellState.ALIVE_CELL.getState())
                    line += "*";
                else if(board[x][y] == CellState.DEAD_CELL.getState())
                    line += ".";
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    public void setToAlive(int x, int y) {
        this.board[x][y] = CellState.ALIVE_CELL.getState();
    }

    public void setToDead(int x, int y) {
        this.board[x][y] = CellState.DEAD_CELL.getState();
    }

    public static void main(String[] args) {

        Simulation simulation = new Simulation(10,8);
        simulation.setToAlive(4,4);
        simulation.setToAlive(5,4);
        simulation.setToAlive(6,4);

        simulation.printBoard();

    }

}
