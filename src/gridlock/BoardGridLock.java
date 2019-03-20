package gridlock;

import java.awt.Point;
import java.nio.file.Path;
import java.util.LinkedList;
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
    public BoardGridLock() {// 0   1   2   3   4   5 (< Y)
        board = new int[][] {{ 1,  1, -1, -1, -1,  7},  // 0
                             { 2, -1, -1,  5, -1,  7},  // 1
                             { 2,  0,  0,  5, -1,  7},  // 2
                             { 2, -1, -1,  5, -1, -1},  // 3
                             { 3, -1, -1, -1,  6,  6},  // 4
                             { 3, -1,  4,  4,  4, -1}}; // 5 (^ X)

        exit = new Point(5, 2);
        goalBlock = new BlockGridLock(0, new Point(2, 1), new Point(2, 2));
        blocks = new LinkedList<>();
        blocks.add(goalBlock);
        blocks.add(new BlockGridLock(1, new Point(0, 0), new Point(0, 1)));
        blocks.add(new BlockGridLock(2, new Point(1, 0), new Point(3, 0)));
        blocks.add(new BlockGridLock(3, new Point(4, 0), new Point(5, 0)));
        blocks.add(new BlockGridLock(4, new Point(5, 2), new Point(5, 4)));
        blocks.add(new BlockGridLock(5, new Point(1, 3), new Point(3, 3)));
        blocks.add(new BlockGridLock(6, new Point(4, 4), new Point(4, 5)));
        blocks.add(new BlockGridLock(7, new Point(0, 5), new Point(2, 5)));
    }

    public BlockGridLock getGoalBlock() {
        return goalBlock;
    }

    public List<BlockGridLock> getBlocks() {
        return blocks;
    }

    public Point getExit() {
        return exit;
    }

    public BoardGridLock move(BlockGridLock block, Direction direction) {
        //TODO: return a new board with the given movement done
        return null;
    }

    /**
     * Block pieces that fill the gridlock board
     */
     class BlockGridLock {
        private Point begin;
        private Point end;
        private Direction firstDirection;
        private Direction secondDirection; //TODO: find a better way to store directions, this is temporary
        private int id;

        BlockGridLock(int id, Point begin, Point end) {
            if (id < 0)
                throw new IllegalArgumentException("Block id can't be negative");

            this.id = id;
            this.begin = begin;
            this.end = end;
            //TODO: remember to initialize directions looking at what coordinate difference is zero at (begin - end)
        }

        @Override
        public boolean equals(Object object) {
            if (object == null)
                return false;

            if (object == this)
                return true;

            if (object instanceof BlockGridLock)
                return ((BlockGridLock) object).id == this.id;

            return false;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }

        public int getId() {
            return id;
        }

        public Point getEnd() {
            return end;
        }

        public Point getBegin() {
            return begin;
        }

        public Direction getFirstDirection() {
            return firstDirection;
        }

        public Direction getSecondDirection() {
            return secondDirection;
        }
    }
}
