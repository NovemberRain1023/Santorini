package edu.cmu.cs214.hw3;

import java.util.Scanner;

public class DemeterGod implements Move, Build{
    boolean isFirstBuild = true;
    int[] oldPlace = {-1, -1};
    @Override
    public boolean build(Board board, Worker worker, int x, int y, boolean useSkill) {
        Build firstBuild = new NormalBuild();
        if (isFirstBuild) {
            oldPlace = new int[]{x, y};
            firstBuild.build(board, worker, x, y, false);
            isFirstBuild = false;
            return false;
        } else {
            isFirstBuild = true;
            int workerX = worker.getPosition()[0];
            int workerY = worker.getPosition()[1];
            if (!(x == oldPlace[0] && y == oldPlace[1])) {
                if (workerX == x && workerY == y) return true;
                boolean isSuccess = firstBuild.build(board, worker, x, y, false);
                return isSuccess;
            }
            else {
                return false;
            }
        }

    }

    @Override
    public boolean move(Board board, Worker worker, int x, int y) {
        Move curMove = new NormalMove();
        return curMove.move(board, worker, x, y);
    }
}
