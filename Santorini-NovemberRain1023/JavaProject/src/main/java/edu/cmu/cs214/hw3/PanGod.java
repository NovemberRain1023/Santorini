package edu.cmu.cs214.hw3;

import java.util.Scanner;

public class PanGod extends NormalCheck implements Build, Move{
    Board board;
    boolean willWin;
    int winWorker = -1;

    @Override
    public int checkSuccess(Board board) {
        if (winWorker != -1) return winWorker;
        CheckWin winner = new NormalCheck();
        return winner.checkSuccess(board);
    }

    @Override
    public boolean move(Board board, Worker worker, int x, int y) {
        Move curMove = new NormalMove();
        int oldX = worker.getPosition()[0];
        int oldY = worker.getPosition()[1];
        int oldLevel = board.getBoard()[oldX][oldY];
        boolean isSuccess = curMove.move(board, worker, x, y);
        int newX = worker.getPosition()[0], newY = worker.getPosition()[1];
        int newLevel = board.getBoard()[newX][newY];
        if (oldLevel - newLevel >= 2) {
            for (int i = 0; i < 4; i++) {
                if (board.getWorkers()[i] == worker) {
                    board.setWinner(i);
                }
            }
        }
        return isSuccess;
    }

    @Override
    public boolean build(Board board, Worker worker, int x, int y, boolean useSkill) {
        Build curBuild = new NormalBuild();
        return curBuild.build(board, worker, x, y, useSkill);
    }
}
