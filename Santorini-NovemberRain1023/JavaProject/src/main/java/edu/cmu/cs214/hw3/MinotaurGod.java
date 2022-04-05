package edu.cmu.cs214.hw3;

import java.util.LinkedList;
import java.util.List;

public class MinotaurGod implements Move, Build, CheckWin{

    private int[][] dir = new int[][] {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    @Override
    public boolean move(Board board, Worker worker, int x, int y) {
        if(canMove(board, worker, x, y)) {
            worker.move(x, y);
            return true;
        } else if(!noWorker(board, x, y)) {
            Worker[] workers = board.getWorkers();
            Worker posWorker = getTheWorker(workers, x, y);
            if (worker == workers[0] || worker == workers[1]) {
                if (posWorker == workers[2] || posWorker == workers[3]) {
                    int[] newPlace = getNewPlace(x, y, worker);
                    int nx = newPlace[0];
                    int ny = newPlace[1];
                    if (nx >= 0 && nx <= 4 && ny >= 0 && ny <= 4 && board.getBoard()[nx][ny] < 4) {
                        posWorker.move(newPlace[0], newPlace[1]);
                        worker.move(x, y);
                        return true;
                    } else {
                        return false;
                    }

                }
            } else if (worker == workers[2] || worker == workers[3]) {
                if (posWorker == workers[1] || posWorker == workers[0]) {
                    int[] newPlace = getNewPlace(x, y, worker);
                    int nx = newPlace[0];
                    int ny = newPlace[1];
                    if (nx >= 0 && nx <= 4 && ny >= 0 && ny <= 4 && board.getBoard()[nx][ny] < 4) {
                        posWorker.move(newPlace[0], newPlace[1]);
                        worker.move(x, y);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
        else {
            System.out.println("Cannot move to that place");
            return false;
        }
    }

    private int[] getNewPlace(int x, int y, Worker origWorker) {
        int[] res = new int[2];
        int workerX = origWorker.getPosition()[0];
        int workerY = origWorker.getPosition()[1];
        res[0] = x + (x - workerX);
        res[1] = y + (y - workerY);
        return res;
    }

    private Worker getTheWorker(Worker[] workers, int x, int y) {
        for (Worker worker : workers) {
            int xPos = worker.getPosition()[0];
            int yPos = worker.getPosition()[1];
            if (x == xPos && y == yPos) return worker;
        }
        return null;
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

    @Override
    public boolean build(Board board, Worker worker, int x, int y, boolean useSkill) {
        Build curBuild = new NormalBuild();
        return curBuild.build(board, worker, x, y, useSkill);
    }

    @Override
    public int checkSuccess(Board board) {
        CheckWin checkWin = new NormalCheck();
        return checkWin.checkSuccess(board);
    }
}
