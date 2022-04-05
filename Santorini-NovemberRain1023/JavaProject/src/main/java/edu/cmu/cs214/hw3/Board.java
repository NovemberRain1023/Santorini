package edu.cmu.cs214.hw3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final Player[] cells = new Player[25];
    public Player getWinner;
    private int winner = -1;
    public void setWinner(int winner) {
        this.winner = winner;
    }
    public int getCurrWinner() {
        return this.winner;
    }
    public Board() {
        this(IntStream.range(0, 25).mapToObj(i -> null)
                .collect(Collectors.toList()).toArray(new Player[0]));
    }

    public Board(Player[] cells) {
        for(int i = 0; i < 25; i++){
            this.cells[i] = cells[i];
        }
    }

    public Player getWinner() {
        if (winner == 0 || winner == 1) {
            getWinner = Player.PLAYER0;
        } else if (winner == 2 || winner == 3) {
            getWinner = Player.PLAYER1;
        } else {
            getWinner = null;
        }
        return getWinner;

    }

    public Player getCell(int x, int y) {
        return this.cells[y * 5 + x];
    }

    public int[][] getBoard() {
        return this.board;
    }
    /**
     * a game board with 5 X 5 integer. Each integer represents the level of the location.
     */
    private int[][] board = new int[5][5];

    /**
     * The four workers of the game. The first two worker belongs to the first player.
     */
    private Worker[] workers;

    /**
     * Eight direction to go through the neighbor locations of a grid.
     */
    private int[][] dir = new int[][] {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    /**
     * Constructor method to initialize a board.
     * @param workers List of workers to initialize the board.
     */
    public Board(List<Worker> workers) {
        this.board = new int[5][5];
        this.workers = new Worker[workers.size()];
        int i = 0;
        for (Worker worker : workers) {
            this.workers[i++] = worker;
        }
    }

    /**
     * Method to print out each level of grid in the board.
     */
    public void getBoardLevel() {
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    private Move move1;
    private Move move2;

    private Build build1;
    private Build build2;

    private CheckWin win1;
    private CheckWin win2;

    /**
     * Get all possible location of a worker can move.
     * @param n worker index. 0,1 represent the first player's two worker. 2,3 represent the other.
     * @return All the possible locations of a worker can move with [x, y] format.
     */
    public List<int[]> getPath(int n) {
        int x = workers[n].getPosition()[0];
        int y = workers[n].getPosition()[1];
        List<int[]> ans = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int[] curDir = dir[i];
            int nx = x + curDir[0], ny = y + curDir[1];
            if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length) {
                if (noWorker(nx, ny) && levelLessThanOne(n, x, y)) {
                    ans.add(new int[] {nx, ny});
                }
            }
        }
        return ans;
    }

    public List<int[]> newGetPath(Worker worker) {
        int x = worker.getPosition()[0];
        int y = worker.getPosition()[1];
        List<int[]> ans = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int[] curDir = dir[i];
            int nx = x + curDir[0], ny = y + curDir[1];
            if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length) {
                if (noWorker(nx, ny) && newLevelLessThanOne(worker, x, y)) {
                    ans.add(new int[] {nx, ny});
                }
            }
        }
        return ans;
    }

    public boolean noWorker(int x, int y) {
        for (Worker worker : workers) {
            int[] pos = worker.getPosition();
            if (x == pos[0] && y == pos[1]) return false;
        }
        return true;
    }

    private boolean levelLessThanOne(int n, int x, int y) {
        int currX = workers[n].getPosition()[0], currY = workers[n].getPosition()[1];
        if (board[x][y] - board[currX][currY] > 1) {
            return false;
        }
        return true;
    }

    private boolean newLevelLessThanOne(Worker worker, int x, int y) {
        int currX = worker.getPosition()[0], currY = worker.getPosition()[1];
        if (board[x][y] - board[currX][currY] > 1) {
            return false;
        }
        return true;
    }

    /**
     * Get all possible locations of a worker can build.
     * @param n worker index. 0,1 represent the first player's two worker. 2,3 represent the other.
     * @return All the possible locations of a worker can build with [x, y] format.
     */
    public List<int[]> getBuild(int n) {
        int x = workers[n].getPosition()[0];
        int y = workers[n].getPosition()[1];
        List<int[]> path = new LinkedList<>();
        for (int i = 0; i < 8; i++ ) {
            int nx = x + dir[i][0], ny = y + dir[i][1];
            if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length) {
                if (noWorker(nx, ny) && board[nx][ny] < 4) {
                    path.add(new int[] {nx, ny, board[nx][ny]});
                }
            }
        }
        return path;
    }

    /**
     * Check if the game has over.
     * @return The winner worker's index.
     */
    public int checkSuccess() {
        if (this.winner != - 1) return this.getCurrWinner();
        if (workers == null || workers[3] == null) return -1;
        winner = win1.checkSuccess(this);
        winner = win2.checkSuccess(this);
        return winner;
    }

    /**
     * build a level in a grid.
     * @param x row number of the board.
     * @param y col number of the board.
     * @return if the build success.
     */
    public boolean build(int x, int y) {
        if (board[x][y] <= 3) {
            board[x][y]++;
            return true;
        } else {
            return false;
        }
    }


    /**
     * get the four workers in the board.
     * @return
     */
    public Worker[] getWorkers() {
        return this.workers;
    }

    public boolean updateCell(int x, int y, Worker worker, boolean isBuild) {
        if (!isBuild) {
            if (worker == this.workers[0] || worker == this.workers[1]) {
                return this.move1.move(this, worker, x, y);
            } else {
                return this.move2.move(this, worker, x, y);
            }
        } else {
            if (worker == this.workers[0] || worker == this.workers[1]) {
                return this.build1.build(this, worker, x, y, false);
            } else {
                return this.build2.build(this, worker, x, y, false);
            }
        }
    }
    public void setGodWithNormal() {
        move1 = new NormalMove();
        move2 = new NormalMove();
        build1 = new NormalBuild();
        build2 = new NormalBuild();
        win1 = new NormalCheck();
        win2 = new NormalCheck();
    }

    public void setGod(Move move1, Build build1, CheckWin win1, Move move2, Build build2, CheckWin win2) {
        this.move1 = move1;
        this.build1 = build1;
        this.win1 = win1;
        this.move2 = move2;
        this.build2 = build2;
        this.win2 = win2;
    }
    public boolean init(int x, int y) {
        if (move1 == null) {
            setGodWithNormal();
        }
        for (int i = 0; i < 4; i++) {
            if (workers == null) {
                workers = new Worker[4];
            }
            if (workers[i] == null) {
                workers[i] = new Worker(x, y);
                break;
            } else {
                if (workers[i].getPosition()[0] == x && workers[i].getPosition()[1] == y) {
                    break;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            if (workers[i] == null) return true;
        }
        return false;
    }

    public void setGod(String god) {
        if (move1 == null) {
            if (god.equals("Demeter")) {
                move1 = new DemeterGod();
                build1 = new DemeterGod();
                win1 = new NormalCheck();
            } else if (god.equals("Minotaur")) {
                move1 = new MinotaurGod();
                build1 = new MinotaurGod();
                win1 = new MinotaurGod();
            } else if (god.equals("Pan")) {
                move1 = new PanGod();
                build1 = new PanGod();
                win1 = new PanGod();
            } else if (god.equals("NoGod")) {
                move1 = new NormalMove();
                build1 = new NormalBuild();
                win1 = new NormalCheck();
            }
        } else if (move2 == null){
            if (god.equals("Demeter")) {
                move2 = new DemeterGod();
                build2 = new DemeterGod();
                win2 = new NormalCheck();
            } else if (god.equals("Minotaur")) {
                move2 = new MinotaurGod();
                build2 = new MinotaurGod();
                win2 = new MinotaurGod();
            } else if (god.equals("Pan")) {
                move2 = new PanGod();
                build2 = new PanGod();
                win2 = new PanGod();
            } else if (god.equals("NoGod")) {
                move2 = new NormalMove();
                build2 = new NormalBuild();
                win2 = new NormalCheck();
            }
        }
    }
}
