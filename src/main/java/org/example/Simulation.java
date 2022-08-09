package org.example;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation {

    int width;
    int height;
    int [][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public int getState(int x, int y) {
        return this.board[x][y];
    }

    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder("|");
            for (int x = 0; x < width; x++) {
                if(board[x][y] == CellState.ALIVE_CELL.getState())
                    line.append("*");
                else if(board[x][y] == CellState.DEAD_CELL.getState())
                    line.append(".");
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    // creates random living cells on the board
    void initializeRandomBoard(int width, int height) {
        int[][] initializedBoard = new int[width][height];
        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            int randomCellWidth = rand.nextInt(width);
            int randomCellHeight = rand.nextInt(height);
            initializedBoard[randomCellWidth][randomCellHeight] = CellState.ALIVE_CELL.getState();
        }
        this.board = initializedBoard;
    }

    // updates the board checking each cell's state
    public void updateBoard(int width, int height, Simulation simulation) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int numberOfAliveNeighbours = countAliveNeighbours(x,y);

                if (simulation.board[x][y] == CellState.DEAD_CELL.getState()) {
                    if (numberOfAliveNeighbours == 3)
                        simulation.setToAlive(x, y);
                }

                else if(simulation.board[x][y] == CellState.ALIVE_CELL.getState()) {
                    if (numberOfAliveNeighbours <= 1 || numberOfAliveNeighbours >= 4)
                        simulation.setToDead(x, y);
                }
            }
        }
    }

    public void step() {
        int[][] updatedBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int numberOfAliveNeighbours = countAliveNeighbours(x,y);

                if(this.board[x][y] == CellState.DEAD_CELL.getState()) {
                    if(numberOfAliveNeighbours == 3)
                        updatedBoard[x][y] = CellState.ALIVE_CELL.getState();
                }

                else if(this.board[x][y] == CellState.ALIVE_CELL.getState()) {
                    if (numberOfAliveNeighbours <= 1 || numberOfAliveNeighbours >= 4)
                        updatedBoard[x][y] = CellState.DEAD_CELL.getState();
                }
            }
        }
        this.board = updatedBoard;
    }

    // checks position off the cell
    public RelativeNextToBorderPosition nextToBorderPosition(int x, int y) {

        // checking if cell is not close to the border
        if(heightCoordinateNotNextToBorder(y) && widthCoordinateNotNextToBorder(x))
            return RelativeNextToBorderPosition.AWAYFROMBORDERS;

        // checking if the cell is in the top row
        else if(y == 0 && widthCoordinateNotNextToBorder(x))
            return RelativeNextToBorderPosition.TOPCENTER;
        else if(y == 0 && x == 0)
            return RelativeNextToBorderPosition.TOPLEFT;
        else if(y == 0 && x == (width - 1))
            return RelativeNextToBorderPosition.TOPRIGHT;

        //checking if the cell is in the bottom row
        else if(y == (height - 1) && widthCoordinateNotNextToBorder(x))
            return RelativeNextToBorderPosition.BOTTOMCENTER;
        else if(y == (height - 1) && x == 0)
            return RelativeNextToBorderPosition.BOTTOMLEFT;
        else if(y == (height - 1) && x == (width - 1))
            return RelativeNextToBorderPosition.BOTTOMRIGHT;

        // checking if the cell is in the first of last column
        else if(heightCoordinateNotNextToBorder(y) && x == 0)
            return RelativeNextToBorderPosition.FIRSTCOLUMN;
        else if(heightCoordinateNotNextToBorder(y) && x == (width - 1))
            return RelativeNextToBorderPosition.LASTCOLUMN;

        return RelativeNextToBorderPosition.AWAYFROMBORDERS;
    }

    public boolean heightCoordinateNotNextToBorder(int coordinate) {
        return coordinate != 0 && coordinate != (height - 1);
    }

    public boolean widthCoordinateNotNextToBorder(int coordinate) {
        return coordinate != 0 && coordinate != (width - 1);
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        RelativeNextToBorderPosition cellPosition = nextToBorderPosition(x,y);

        if(cellPosition == RelativeNextToBorderPosition.AWAYFROMBORDERS) {
            count += checkLeftUp(x,y);
            count += checkAbove(x,y);
            count += checkRightUp(x,y);
            count += checkLeft(x,y);
            count += checkRight(x,y);
            count += checkLeftDown(x,y);
            count += checkUnder(x,y);
            count += checkRightDown(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.TOPLEFT) {
            count += checkRight(x,y);
            count += checkUnder(x,y);
            count += checkRightDown(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.TOPRIGHT) {
            count += checkLeft(x,y);
            count += checkUnder(x,y);
            count += checkLeftDown(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.BOTTOMLEFT) {
            count += checkRight(x,y);
            count += checkAbove(x,y);
            count += checkRightUp(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.BOTTOMRIGHT) {
            count += checkLeft(x,y);
            count += checkAbove(x,y);
            count += checkLeftUp(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.TOPCENTER) {
            count += checkLeft(x,y);
            count += checkRight(x,y);
            count += checkLeftDown(x,y);
            count += checkUnder(x,y);
            count += checkRightDown(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.BOTTOMCENTER) {
            count += checkLeftUp(x,y);
            count += checkAbove(x,y);
            count += checkRightUp(x,y);
            count += checkLeft(x,y);
            count += checkRight(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.FIRSTCOLUMN) {
            count += checkAbove(x,y);
            count += checkRightUp(x,y);
            count += checkRight(x,y);
            count += checkUnder(x,y);
            count += checkRightDown(x,y);
        }
        else if(cellPosition == RelativeNextToBorderPosition.LASTCOLUMN) {
            count += checkLeftUp(x,y);
            count += checkAbove(x,y);
            count += checkLeft(x,y);
            count += checkLeftDown(x,y);
            count += checkUnder(x,y);
        }
        return count;
    }

    public int checkLeftUp(int x, int y) {
        return this.board[x - 1][y - 1];
    }

    public int checkAbove(int x, int y) {
        return this.board[x][y - 1];
    }

    public int checkRightUp(int x, int y) {
        return this.board[x + 1][y - 1];
    }

    public int checkLeft(int x, int y) {
        return this.board[x - 1][y];
    }

    public int checkRight(int x, int y) {
        return this.board[x + 1][y];
    }

    public int checkLeftDown(int x, int y) {
        return this.board[x - 1][y + 1];
    }

    public int checkUnder(int x, int y) {
        return this.board[x][y + 1];
    }

    public int checkRightDown(int x, int y) {
        return this.board[x + 1][y + 1];
    }


    public void setToAlive(int x, int y) {
        this.board[x][y] = CellState.ALIVE_CELL.getState();
    }

    public void setToDead(int x, int y) {
        this.board[x][y] = CellState.DEAD_CELL.getState();
    }

    public static void main(String[] args) {

        int width = 15;
        int height = 6;
        Simulation simulation = new Simulation(width,height);
        // Random rand = new Random();

        simulation.initializeRandomBoard(width, height);

        for (int i = 0; i < 100; i++) {
            System.out.println(i + 1 + ". print");
            simulation.printBoard();
            simulation.updateBoard(width, height, simulation);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
