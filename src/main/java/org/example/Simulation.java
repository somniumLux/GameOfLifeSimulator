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

    // checks position off the cell
    public RelativeNextToBorderPosition nextToBorderPosition(int x, int y) {

        // checking if cell is not close to the border
        if(!heightCoordinateNextToBorder(y) && !widthCoordinateNextToBorder(x))
            return RelativeNextToBorderPosition.AWAYFROMBORDERS;

        // checking if the cell is in the top row
        else if(y == 0 && !widthCoordinateNextToBorder(x))
            return RelativeNextToBorderPosition.TOPCENTER;
        else if(y == 0 && x == 0)
            return RelativeNextToBorderPosition.TOPLEFT;
        else if(y == 0 && x == (width - 1))
            return RelativeNextToBorderPosition.TOPRIGHT;

        //checking if the cell is in the bottom row
        else if(y == (height - 1) && !widthCoordinateNextToBorder(x))
            return RelativeNextToBorderPosition.BOTTOMCENTER;
        else if(y == (height - 1) && x == 0)
            return RelativeNextToBorderPosition.BOTTOMLEFT;
        else if(y == (height - 1) && x == (width - 1))
            return RelativeNextToBorderPosition.BOTTOMRIGHT;

        // checking if the cell is in the first of last column
        else if(!heightCoordinateNextToBorder(y) && x == 0)
            return RelativeNextToBorderPosition.FIRSTCOLUMN;
        else if(!heightCoordinateNextToBorder(y) && x == (width - 1))
            return RelativeNextToBorderPosition.LASTCOLUMN;

        return RelativeNextToBorderPosition.AWAYFROMBORDERS;
    }

    public boolean heightCoordinateNextToBorder(int coordinate) {
        return coordinate == 0 || coordinate == (height - 1);
    }

    public boolean widthCoordinateNextToBorder(int coordinate) {
        return coordinate == 0 || coordinate == (width - 1);
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
        Random rand = new Random();

        // creates random living cells
        // TODO refactor as a method "initializeBoard"
        for (int i = 0; i < 10; i++) {
            int randomCellWidth = rand.nextInt(width);
            int randomCellHeight = rand.nextInt(height);

            simulation.setToAlive(randomCellWidth,randomCellHeight);
        }

        //simulation.printBoard();

        // updates the board checking each cell's state and changing it if needed
        // TODO refactor as a method "updateBoard"
        for (int i = 0; i < 10; i++) {
            simulation.printBoard();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int numberOfNeighbours = simulation.countAliveNeighbours(x, y);

                    if (simulation.board[x][y] == CellState.DEAD_CELL.getState()) {
                        if (numberOfNeighbours == 3)
                            simulation.setToAlive(x, y);
                        if (numberOfNeighbours == 2 || numberOfNeighbours == 3)
                            continue;
                    }

                    else if(simulation.board[x][y] == CellState.ALIVE_CELL.getState()) {
                        if (numberOfNeighbours <= 1 || numberOfNeighbours >= 4)
                            simulation.setToDead(x, y);
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
