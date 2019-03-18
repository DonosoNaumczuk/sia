package gps;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;

public class BoardGridLock {
    private int[][] board;
    private Point exit;
    private BlockGridLock goalBlock;
    private List<BlockGridLock> blocks;

    /**
     * Constructor to initialize the first gridlock board
     * @param boardFile path to the file that have a representation of the board
     */
    public BoardGridLock(Path boardFile) {
        //TODO: fill board using a given file
    }

    /**
     * Constructor to initialize the first gridlock board with a hardcoded
     * the configuration, given at level 1 of http://www.gtds.net/Gridlock/
     */
    public BoardGridLock() {
        board = new int[][] {{ 1,  1, -1, -1, -1,  7},
                             { 2, -1, -1,  5, -1,  7},
                             { 2,  0,  0,  5, -1,  7},
                             { 2, -1, -1,  5, -1, -1},
                             { 3, -1, -1, -1,  6,  6},
                             { 3, -1,  4,  4,  4, -1}};

        exit = new Point(5, 2);
    }

    public BlockGridLock getGoalBlock() {
        return goalBlock;
    }

    public int[][] getBoard() {
        return board;
    }

    public List<BlockGridLock> getBlocks() {
        return blocks;
    }

    public Point getExit() {
        return exit;
    }
}
