package edu.cmu.cs214.hw3;

import java.util.LinkedList;
import java.util.List;

public class NormalBuild implements Build{

    private int[][] dir = new int[][] {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    @Override
    public boolean build(Board board, Worker worker, int x, int y, boolean useSkill) {
        if(canBuild(board, worker, x, y)) {
            board.build(x, y);
            return true;
        } else {
            return false;
        }
    }

    private boolean canBuild(Board board, Worker worker, int x, int y) {
        List<int[]> path = getBuild(board, worker);
        if (checkValidInput(x, y , path)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkValidInput(int nx, int ny, List<int[]> paths) {
        for (int[] path : paths) {
            if (path[0] == nx && path[1] == ny) {
                return true;
            }
        }
        return false;
    }

    public List<int[]> getBuild(Board board, Worker worker) {
        int x = worker.getPosition()[0];
        int y = worker.getPosition()[1];
        List<int[]> path = new LinkedList<>();
        for (int i = 0; i < 8; i++ ) {
            int nx = x + dir[i][0], ny = y + dir[i][1];
            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if (board.noWorker(nx, ny) && board.getBoard()[nx][ny] < 4) {
                    path.add(new int[] {nx, ny, board.getBoard()[nx][ny]});
                }
            }
        }
        return path;
    }
}
