package edu.cmu.cs214.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


enum Player {
    PLAYER0(0), PLAYER1(1);

    final int value;

    Player(int value) {
        this.value = value;
    }
}

public class UI {
    Board board = new Board();
    int player;
    boolean isBuild = false;
    boolean isInit = true;
    int cnt = 0;

    Worker currentWorker;
    Worker nextWorker;
    Worker prevWorker;


    public void UI() {
        board = new Board();
        isBuild = false;
        isInit = true;
    }


    public UI playTheGame(int x, int y) {
        if (board.checkSuccess() >= 2) return this;

        int i = 0;
        if (isInit) {
            this.isInit = init(board, x, y);
            return this;
        } else if (nextWorker == null) {
            Worker[] workers = board.getWorkers();
            if (isChoosing(x, y, board) == prevWorker) {
                return this;
            } else if ((currentWorker == workers[0] || currentWorker == workers[1]) &&
                    (isChoosing(x, y, board) == workers[2] || isChoosing(x, y, board) == workers[3])) {
                return this;
            } else if ((currentWorker == workers[2] || currentWorker == workers[3]) &&
                    (isChoosing(x, y, board) == workers[0] || isChoosing(x, y, board) == workers[1])) {
                return this;
            }
            currentWorker = isChoosing(x, y, board);
            if (prevWorker != null) return this;
            if (currentWorker == board.getWorkers()[0]) {
                nextWorker = board.getWorkers()[1];
            } else if (currentWorker == board.getWorkers()[1]) {
                nextWorker = board.getWorkers()[0];
            } else if (currentWorker == board.getWorkers()[2]) {
                nextWorker = board.getWorkers()[3];
            } else {
                nextWorker = board.getWorkers()[2];
            }
            return this;
        } else {
            Worker worker = currentWorker;
            boolean isSuccess = this.board.updateCell(x, y, worker, isBuild);
            if (isSuccess) {
                cnt++;
                if (board.checkSuccess() >= 2) return this;
                this.isBuild = this.isBuild == true ? false : true;
                if (isBuild == false) {
                    currentWorker = nextWorker;

                } else {
                    prevWorker = currentWorker;
                }
                if (cnt == 4) {
                    currentWorker = null;
                    prevWorker = null;
                    nextWorker = null;
                    cnt = 0;
                }
            }

            return this;
        }

    }

    private Worker isChoosing(int x, int y, Board board) {
        Worker[] workers = board.getWorkers();
        for (Worker worker : workers) {
            int xPos = worker.getPosition()[0];
            int yPos = worker.getPosition()[1];
            if (x == xPos && y == yPos) {
                return worker;
            }
        }
        return null;
    }

    private boolean init(Board board, int x, int y) {
        return board.init(x, y);
    }


    public int getTurn() {
        if (cnt == 0 || cnt == 1) {
            return 1;
        } else if (cnt == 2 || cnt == 3) {
            return 2;
        }
        return -1;
    }

    public UI setGod(String god) {
        board.setGod(god);
        return this;
    }
}
