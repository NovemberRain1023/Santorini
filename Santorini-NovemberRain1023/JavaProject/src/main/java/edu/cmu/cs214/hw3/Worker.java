package edu.cmu.cs214.hw3;

public class Worker {
    private int x;
    private int y;

    /**
     * Constructor to initialize a worker.
     * @param x row number of the worker
     * @param y column number of the worker
     */


    public Worker(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Worker() {
        this.x = -1;
        this.y = -1;
    }

    /**
     * Move the worker to a new grid.
     * @param dx new row number of the grid to move
     * @param dy new column number of the grid to move
     * @return if the move success
     */
    public boolean move(int dx, int dy) {
        if (dx >= 0 && dx < 5 && dy >= 0 && dy < 5) {
            this.x = dx;
            this.y = dy;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the worker's position in board.
     * @return the location of the worker with [x, y] format
     */
    public int[] getPosition() {
        return new int[] {this.x, this.y};
    }

}
