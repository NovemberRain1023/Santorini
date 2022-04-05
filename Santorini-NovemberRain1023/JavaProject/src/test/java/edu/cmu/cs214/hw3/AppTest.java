package edu.cmu.cs214.hw3;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private Worker worker1;
    private Worker worker2;
    private Worker worker3;
    private Worker worker4;
    private List<Worker> workers;
    private Board board;
    private UI ui;

    @Before
    public void setUp() {
        worker1 = new Worker(0,0);
        worker2 = new Worker(2, 1);
        worker3 = new Worker(3, 0);
        worker4 = new Worker(3, 3);
        workers = new LinkedList<>();
        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);
        workers.add(worker4);

        board = new Board(workers);
        ui = new UI();
    }

    @Test
    public void testUpdateCellWithMove() {
        board.setGodWithNormal();
        assertTrue(board.updateCell(0, 1, worker1, false));
    }

    @Test
    public void testUpdateCellWithMoveAndBuild() {
        board.setGodWithNormal();
        board.updateCell(0, 1, worker1, false);
        assertTrue(board.updateCell(0, 0, worker1, true));
    }

    @Test
    public void testUpdateCellWithMoveWithDemeterGod() {
        Build build = new DemeterGod();
        Move move = new DemeterGod();
        CheckWin win = new NormalCheck();
        Build build1 = new DemeterGod();
        Move move1 = new DemeterGod();
        CheckWin win1 = new NormalCheck();
        board.setGod(move, build, win, move1, build1, win1);
        assertTrue(board.updateCell(0, 1, worker1, false));
        assertTrue(board.updateCell(0, 0, worker1, false));
    }

    @Test
    public void testUpdateCellWithMoveWithMinotaurGod() {
        Build build = new MinotaurGod();
        Move move = new MinotaurGod();
        CheckWin win = new MinotaurGod();
        Build build1 = new MinotaurGod();
        Move move1 = new MinotaurGod();
        CheckWin win1 = new MinotaurGod();
        board.setGod(move, build, win, move1, build1, win1);
        assertTrue(board.updateCell(1, 0, worker1, false));
        assertTrue(worker3.getPosition()[0] == 2);
    }

    @Test
    public void testUpdateCellWithMoveWithPanGod() {
        Build build = new PanGod();
        Move move = new PanGod();
        CheckWin win = new PanGod();
        Build build1 = new PanGod();
        Move move1 = new PanGod();
        CheckWin win1 = new PanGod();
        board.setGod(move, build, win, move1, build1, win1);
        board.updateCell(1, 0, worker1, true);
        board.updateCell(1, 0, worker1, true);
        board.updateCell(1, 0, worker1, false);
        board.updateCell(0, 0, worker1, false);
        //assertTrue(board.updateCell(1, 0, worker1, false));
        assertTrue(board.getCurrWinner() == 0);
    }

    @Test
    public void testMove() {
        assertTrue(worker1.move(0, 1));
    }

    @Test
    public void testBuild() {
        assertTrue(board.build(0, 1));
    }

    @Test
    public void testNewWorker1() {
        assertTrue(worker1.getPosition()[0] == 0);
    }

    @Test
    public void testGetBoardLevel() {
        board.getBoardLevel();
    }

    @Test
    public void testNewWorker2() {
        assertTrue(worker1.getPosition()[1] == 0);
    }

    @Test
    public void testNewWorkers() {
        assertTrue(workers.size() == 4);
    }

    @Test
    public void testNewWorkersPos() {
        assertTrue(workers.get(0).getPosition()[0] == 0);
    }

    @Test
    public void testNewBoard2() {
        assertTrue(board.getWorkers().length == 4);
        assertTrue(board.getWorkers()[0].getPosition()[0] == 0);
    }

    @Test
    public void testGetPath() {
        List<int[]> path = board.getPath(0);
        assertTrue(path.size() == 3);

        assertTrue(path.get(0)[0] == 0);
        assertTrue(path.get(0)[1] == 1);
        assertTrue(path.get(1)[0] == 1);
        assertTrue(path.get(1)[1] == 1);
        assertTrue(path.get(2)[0] == 1);
        assertTrue(path.get(2)[1] == 0);

    }

    @Test
    public void testGetBuild() {
        //board.build(1,1);
        List<int[]> path = board.getBuild(0);
        assertTrue(path.size() == 3);

        assertTrue(path.get(0)[0] == 0);
        assertTrue(path.get(0)[1] == 1);
        assertTrue(path.get(1)[0] == 1);
        assertTrue(path.get(1)[1] == 1);
        assertTrue(path.get(2)[0] == 1);
        assertTrue(path.get(2)[1] == 0);

    }

    @Test
    public void testGetBuild2() {
        board.build(1,1);
        board.build(1,1);
        board.build(1,1);
        //board.build(1,1);
        List<int[]> path = board.getBuild(0);
        assertTrue(path.size() == 3);

        List<int[]> path1 = board.getBuild(1);
        assertTrue(path1.size() == 8);

    }

    @Test
    public void testGetPath1() {
        List<int[]> path = board.getPath(1);
        assertTrue(path.size() == 8);
    }

    @Test
    public void testGetPath2() {
        List<int[]> path = board.getPath(2);
        assertTrue(path.size() == 8);
    }

    @Test
    public void testGetPath3() {
        List<int[]> path = board.getPath(3);
        assertTrue(path.size() == 8);
    }

    @Test
    public void testWin() {
        board.build(1,1);
        board.build(1,1);
        board.build(1,1);
        worker1.move(1,1);
        //board.build(1,1);
        assertTrue(board.checkSuccess() == 0);
    }
}
