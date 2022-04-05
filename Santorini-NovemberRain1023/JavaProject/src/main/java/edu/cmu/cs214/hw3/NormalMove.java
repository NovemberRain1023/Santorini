package edu.cmu.cs214.hw3;

import java.util.LinkedList;
import java.util.List;

public class NormalMove implements Move{

    private int[][] dir = new int[][] {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    @Override
    public boolean move(Board board, Worker worker, int x, int y) {
        if(canMove(board, worker, x, y)) {
            worker.move(x, y);
            return true;
        } else {
            System.out.println("Cannot move to that place");
            return false;
        }
    }

    private boolean canMove(Board board, Worker worker, int x, int y) {
        List<int[]> path = newGetPath(board, worker);
        if (checkValidInput(x, y , path)) {
            return true;
        } else {
            return false;
        }
    }

    public List<int[]> newGetPath(Board board, Worker worker) {
        int x = worker.getPosition()[0];
        int y = worker.getPosition()[1];
        List<int[]> ans = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int[] curDir = dir[i];
            int nx = x + curDir[0], ny = y + curDir[1];
            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if (noWorker(board, nx, ny) && newLevelLessThanOne(board, worker, x, y)) {
                    ans.add(new int[] {nx, ny});
                }
            }
        }
        return ans;
    }

    private boolean newLevelLessThanOne(Board board, Worker worker, int x, int y) {
        int currX = worker.getPosition()[0], currY = worker.getPosition()[1];
        if (board.getBoard()[x][y] - board.getBoard()[currX][currY] > 1) {
            return false;
        }
        return true;
    }

    private boolean noWorker(Board board, int x, int y) {
        for (Worker worker : board.getWorkers()) {
            int[] pos = worker.getPosition();
            if (x == pos[0] && y == pos[1]) return false;
        }
        return true;
    }

    private boolean checkValidInput(int nx, int ny, List<int[]> paths) {
        for (int[] path : paths) {
            if (path[0] == nx && path[1] == ny) {
                return true;
            }
        }
        return false;
    }
}
