import gps.GPSEngine;
import gridlock.BoardGridLock;
import gridlock.ProblemGridLock;
import static gps.SearchStrategy.*;

public class GridLockSolver {
    public static void main(String[] args) {
        //TODO: Initialize GPSEngine from args
        GPSEngine gpsEngine = new GPSEngine(new ProblemGridLock(new BoardGridLock()), BFS, null);
        gpsEngine.findSolution();
    }
}
