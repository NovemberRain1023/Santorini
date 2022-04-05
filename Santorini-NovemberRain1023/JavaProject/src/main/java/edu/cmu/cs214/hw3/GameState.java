package edu.cmu.cs214.hw3;

import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private final Player winner;
    private final int turn;

    // private final String curPlayer;
    // winner

    private GameState(Cell[] cells, Player winner, int turn) {
        this.cells = cells;
        this.turn = turn;
        this.winner = winner;

    }

    public static GameState forGame(UI game) {
        Cell[] cells = getCells(game);
        Player winner = getWinner(game);
        int turn = getTurn(game);

        return new GameState(cells, winner, turn);
    }

    @Override
    public String toString() {
        if (this.winner == null) {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                    "\"turn\": " + String.valueOf(this.turn) + "}";
        }
        return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                "\"turn\": " + String.valueOf(this.turn) + "," +
                "\"winner\": " + String.valueOf(this.winner.value) + "}";
    }

    private static int getTurn(UI game) {
        return game.getTurn();
    }

    private static Player getWinner(UI game) {
        return game.board.getWinner();
    }


    public Cell[] getCells() {
        return this.cells;
    }


    private static Cell[] getCells(UI game) {
        Cell cells[] = new Cell[25];
        Board board = game.board;
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                String text = "";
                String link = "";
                String clazz = "";
                Worker[] workers = board.getWorkers();
                for (int i = 0; i < 4; i ++) {
                    if (workers == null || workers[i] == null) break;
                    Worker worker = workers[i];
                    int xPos = worker.getPosition()[0];
                    int yPos = worker.getPosition()[1];
                    if (xPos == x && yPos == y) {
                        if (i == 0 || i == 1) text = "Player0";
                        else text = "Player1";
                    }

                }
                if (board.getBoard()[x][y] == 1) {
                    text = '[' + text + ']';
                } else if (board.getBoard()[x][y] == 2) {
                    text = "[[" + text + "]]";
                } else if (board.getBoard()[x][y] == 3) {
                    text = "[[[" + text + "]]]";
                } else if (board.getBoard()[x][y] == 4) {
                    text = "^" + text;
                }

                clazz = "playable";
                link = "/play?x=" + x + "&y=" + y;

                cells[5 * y + x] = new Cell(text, clazz, link);
            }
        }
        return cells;
    }
}

class Cell {
    private final String text;
    private final String clazz;
    private final String link;

    Cell(String text, String clazz, String link) {
        this.text = text;
        this.clazz = clazz;
        this.link = link;
    }

    public String getText() {
        return this.text;
    }

    public String getClazz() {
        return this.clazz;
    }

    public String getLink() {
        return this.link;
    }

    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"," +
                " \"clazz\": \"" + this.clazz + "\"," +
                " \"link\": \"" + this.link + "\"}";
    }


}