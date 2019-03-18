package gps;

import java.awt.*;

/**
 * Block pieces that fill the gridlock board
 */
public class BlockGridLock {
    private Point begin;
    private Point end;
    private int id;

    public BlockGridLock(int id, Point begin, Point end) {
        if(id < 0)
            throw new IllegalArgumentException("Block id can't be negative");

        this.id = id;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj == this)
            return true;

        if(obj instanceof BlockGridLock)
            return ((BlockGridLock) obj).id == this.id;

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

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }
}
