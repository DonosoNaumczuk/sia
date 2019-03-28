package gridlock;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import static gridlock.Direction.*;

public class BoardGridLock {

    private static int EMPTY_CELL = -1;
    private int[][] board;
    private Point exit;
    private BlockGridLock goalBlock;
    private List<BlockGridLock> blocks;

    /**
     * Constructor to initialize the first gridlock board
     * @param path path to the file that have a JSON representation of the board
     * First block of the file will be always set as the goal block
     */
    public BoardGridLock(String path) throws FileNotFoundException {
        FileReader reader = new FileReader(path);
        JsonParser parser = new JsonParser();
        JSONGridLockBoardParser.JSONBoard jsonBoard = new Gson().fromJson(parser.parse(reader),
                JSONGridLockBoardParser.JSONBoard.class);

        exit   = jsonBoard.exit;
        board  = new int[jsonBoard.rows][jsonBoard.columns];
        setAllBoardCellsToEmpty();
        blocks = new ArrayList<>();

        for (int i = 0; i < jsonBoard.blocks.length; i++) {
            JSONGridLockBoardParser.JSONBlock block = jsonBoard.blocks[i];
            BlockGridLock blockGridLock             = new BlockGridLock(i, block.firstPoint, block.secondPoint);
            blocks.add(blockGridLock);
            fillBoardWithBlock(blockGridLock);
        }

        goalBlock = blocks.get(0);
    }

    private BoardGridLock(int[][] board, Point exit, List<BlockGridLock> blocks, BlockGridLock goalBlock) {
        this.board      = board;
        this.exit       = exit;
        this.blocks     = blocks;
        this.goalBlock  = goalBlock;
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

        exit = new Point(2, 5);
        goalBlock = new BlockGridLock(0, new Point(2, 1), new Point(2, 2));
        blocks = new ArrayList<>();
        blocks.add(goalBlock);
        blocks.add(new BlockGridLock(1, new Point(0, 0), new Point(0, 1)));
        blocks.add(new BlockGridLock(2, new Point(1, 0), new Point(3, 0)));
        blocks.add(new BlockGridLock(3, new Point(4, 0), new Point(5, 0)));
        blocks.add(new BlockGridLock(4, new Point(5, 2), new Point(5, 4)));
        blocks.add(new BlockGridLock(5, new Point(1, 3), new Point(3, 3)));
        blocks.add(new BlockGridLock(6, new Point(4, 4), new Point(4, 5)));
        blocks.add(new BlockGridLock(7, new Point(0, 5), new Point(2, 5)));
    }

    public BoardGridLock move(BlockGridLock block, Direction direction) {
        if (!canMove(block, direction))
            throw new RuntimeException("Can't perform a movement with the given block and directions");

        List<BlockGridLock> newBlocks = new ArrayList<>(this.blocks);
        int[][] newBoard = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++)
            newBoard[i] = board[i].clone();

        BlockGridLock newBlock = move(newBoard, block, direction);
        newBlocks.set(block.id, newBlock);

        return new BoardGridLock(newBoard, exit, newBlocks, newBlocks.get(0));
    }

    private BlockGridLock move(int[][] board, BlockGridLock block, Direction direction) {
        if (direction == LEFT) {
            board[block.begin.x][block.begin.y - 1] = block.id;
            board[block.end.x][block.end.y]         = EMPTY_CELL;
            return new BlockGridLock(block.id, new Point(block.begin.x, block.begin.y - 1), new Point(block.end.x,
                    block.end.y - 1));
        }
        else if (direction == RIGHT) {
            board[block.end.x][block.end.y + 1] = block.id;
            board[block.begin.x][block.begin.y] = EMPTY_CELL;
            return new BlockGridLock(block.id, new Point(block.begin.x, block.begin.y + 1), new Point(block.end.x,
                    block.end.y + 1));
        }
        else if (direction == UP) {
            board[block.begin.x - 1][block.begin.y] = block.id;
            board[block.end.x][block.end.y]         = EMPTY_CELL;
            return new BlockGridLock(block.id, new Point(block.begin.x - 1, block.begin.y),
                    new Point(block.end.x - 1, block.end.y));
        }
        else {
            board[block.end.x + 1][block.end.y] = block.id;
            board[block.begin.x][block.begin.y] = EMPTY_CELL;
            return new BlockGridLock(block.id, new Point(block.begin.x + 1, block.begin.y),
                    new Point(block.end.x + 1, block.end.y));
        }
    }

    public BlockGridLock getBlockById(int id) {
        return blocks.get(id);
    }

    /**
     *  @return if the block can be moved in the given direction
     */
    public boolean canMove(BlockGridLock block, Direction direction) {
        if (block.firstDirection == LEFT) {
            if (direction == UP || direction == DOWN)
                return false;

            if (direction == LEFT)
                return block.begin.y != 0 && board[block.begin.x][block.begin.y - 1] == EMPTY_CELL;
            else
                return block.end.y != board[0].length - 1 && board[block.end.x][block.end.y + 1] == EMPTY_CELL;
        }

        if (direction == RIGHT || direction == LEFT)
            return false;

        if (direction == UP) {
            return block.begin.x != 0 && board[block.begin.x - 1][block.begin.y] == EMPTY_CELL;
        }
        else {
            return block.end.x != board.length - 1 && board[block.end.x + 1][block.end.y] == EMPTY_CELL;
        }
    }

    private void setAllBoardCellsToEmpty() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = EMPTY_CELL;
    }

    private void fillBoardWithBlock(BlockGridLock block) {
        if (block.firstDirection == LEFT)
            for (int y = block.begin.y; y <= block.end.y; y++)
                board[block.begin.x][y] = block.id;
        else
            for (int x = block.begin.x; x <= block.end.x; x++)
                board[x][block.begin.y] = block.id;
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

    @Override
    public int hashCode() {
        int prime = 31;
        int hash = 1;

        for (int x = 0; x < board.length; x++)
            for (int y = 0; y < board[x].length; y++)
                hash = prime * hash + board[x][y];

        hash = prime * hash + exit.hashCode();

        return hash;
    }

    @Override
    public String toString() {
        String[] colors     = {"\033[1;31m", "\033[1;32m", "\033[1;33m", "\033[1;34m", "\033[1;35m", "\033[1;36m",
                               "\033[1;37m", "\033[0;31m", "\033[0;32m", "\033[0;33m", "\033[0;34m", "\033[0;35m",
                               "\033[0;36m", "\033[0;37m",};
        String resetColors  = "\033[0m";
        String background   = "\033[0;100m";
        String underline    = "\033[4;37m";

        StringBuilder boardString = new StringBuilder();
        int digits = String.valueOf(blocks.size()).length();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (exit.x == x && exit.y == y)
                    boardString.append(background);

                if (board[x][y] == EMPTY_CELL) {
                    boardString.append(underline);
                    for (int i = 0; i < digits; i++)
                        boardString.append(" ");
                }
                else
                    boardString.append(colors[board[x][y] % colors.length]).append(board[x][y]);

                boardString.append(resetColors).append("\t");
            }

            boardString.append("\n");
        }

        return boardString.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object == this)
            return true;

        if (!(object instanceof BoardGridLock)) {
            return false;
        }

        BoardGridLock otherBoard = (BoardGridLock) object;

        return exit.equals(otherBoard.exit) && equalBoard(otherBoard.board);
    }

    private boolean equalBoard(int[][] otherBoard) {
        if (otherBoard == null)
            return false;

        if (otherBoard.length != board.length)
            return false;

        if (otherBoard[0].length != board[0].length)
            return false;

        for (int x = 0; x < board.length; x++)
            for (int y = 0; y < board[x].length; y++)
                if (board[x][y] != otherBoard[x][y])
                    return false;

        return true;
    }

    public int[][] getBoard() {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++)
            newBoard[i] = board[i].clone();

        return newBoard;
    }

    /**
     * Block pieces that fill the gridlock board
     */
     class BlockGridLock {
        private Point begin;
        private Point end;
        private Direction firstDirection;
        private Direction secondDirection;
        private int id;

        BlockGridLock(int id, Point firstPoint, Point secondPoint) {
            if (id < 0)
                throw new IllegalArgumentException("Block id can't be negative");

            this.id = id;
            setDirections(firstPoint, secondPoint);
            setBeginAndEndPoints(firstPoint, secondPoint);
        }

        /**
         * Set begin point always as left most point (for horizontal pieces) or upper point (for vertical pieces) in
         * order to optimize move and canMove functions from the BoardGridLock class
         */
        private void setBeginAndEndPoints(Point firstPoint, Point secondPoint) {
            if (((firstDirection == LEFT) && (firstPoint.y < secondPoint.y)) ||
                    ((firstDirection == UP) && (firstPoint.x < secondPoint.x))) {
                begin = firstPoint;
                end = secondPoint;
            }
            else {
                begin   = secondPoint;
                end     = firstPoint;
            }
        }

        private void setDirections(Point firstPoint, Point secondPoint) {
            if (firstPoint.x - secondPoint.x == 0) {
                firstDirection  = LEFT;
                secondDirection = RIGHT;
            }
            else if (firstPoint.y - secondPoint.y == 0) {
                firstDirection  = UP;
                secondDirection = DOWN;
            }
            else
                throw new RuntimeException("Invalid block coordinates");
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

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
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

    /**
     * A class done just to make the JSON parsing easier
     */
     private class JSONGridLockBoardParser {
        class JSONBoard {
            int rows;
            int columns;
            Point exit;
            JSONBlock[] blocks;
        }

        class JSONBlock {
            Point firstPoint;
            Point secondPoint;
        }
    }
}
