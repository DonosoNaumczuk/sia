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
    public boolean equals(Object object) {
        if(object == null)
            return false;

        if(object == this)
            return true;

        if(object instanceof BlockGridLock)
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
