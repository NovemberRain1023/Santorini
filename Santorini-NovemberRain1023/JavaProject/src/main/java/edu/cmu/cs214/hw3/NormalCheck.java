package edu.cmu.cs214.hw3;

public class NormalCheck implements CheckWin{
    @Override
    public int checkSuccess(Board board) {
        if (board.getCurrWinner() != -1) return board.getCurrWinner();
        int[][] curBoard = board.getBoard();
        Worker[] workers = board.getWorkers();
        for (int i = 0; i < workers.length; i++) {
            int[] pos = workers[i].getPosition();
            int x = pos[0], y = pos[1];
            if (curBoard[x][y] == 3) {
                return i;
            }
        }
        return -1;
    }
}
