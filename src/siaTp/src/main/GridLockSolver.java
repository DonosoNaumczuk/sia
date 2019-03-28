import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;
import gps.api.Heuristic;
import gridlock.BoardGridLock;
import gridlock.HeuristicGridLock1;
import gridlock.ProblemGridLock;
import gridlock.RandomHeuristic;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static gps.SearchStrategy.*;

public class GridLockSolver {
    private static String ALGORITHM_RESULT_TEXT       = "\033[0;1mSearch strategy: \u001B[0m";
    private static String NO_HEURISTIC_RESULT_TEXT    = "\033[0;1mHeuristics was not used \u001B[0m";
    private static String HEURISTIC_RESULT_TEXT       = "\033[0;1mHeuristic description: \u001B[0m";
    private static String SUCCESS_RESULT_TEXT         = "\033[0;1mThe search was a ";
    private static String SUCCESS_TEXT                = "success\u001B[0m";
    private static String FAILURE_TEXT                = "failure\u001B[0m";
    private static String NODES_EXPANDED_RESULT_TEXT  = "\033[0;1mExpanded nodes: \u001B[0m";
    private static String STATES_ANALYZED_RESULT_TEXT = "\033[0;1mAnalyzed states: \u001B[0m";
    private static String NODES_FRONTIER_RESULT_TEXT  = "\033[0;1mFrontier nodes: \u001B[0m";
    private static String SOLUTION_DEEP_RESULT_TEXT   = "\033[0;1mSolution depth: \u001B[0m";
    private static String SOLUTION_COST_RESULT_TEXT   = "\033[0;1mSolution cost: \u001B[0m";
    private static String TIME_RESULT_TEXT            = "\033[0;1mProcess time: \u001B[0m";
    private static String TIME_UNIT_RESULT_TEXT       = " ms" ;

    public static void main(String[] args) throws FileNotFoundException {
        // Parse parameters
        SearchStrategy searchStrategy = parseSearchStrategy(args[0]);
        Heuristic heuristic = parseHeuristic(args[1]);

        // Start run
        long timeOfProcess = System.currentTimeMillis(); //start time
        GPSEngine gpsEngine = new GPSEngine(new ProblemGridLock(new BoardGridLock()), searchStrategy, heuristic);
        gpsEngine.findSolution();
        timeOfProcess = System.currentTimeMillis() - timeOfProcess; // duration = finish time - start time

        // Start prints of results
        LinkedList<GPSNode> path = new LinkedList<>();

        if(!gpsEngine.isFailed()) {
            GPSNode current = gpsEngine.getSolutionNode();
            while (current.getParent() != null) {
                path.push(current);
                current = current.getParent();
            }

            // Print the path to solution
            int step = 1;
            for (GPSNode node: path) {
                if (node.getGenerationRule() != null)
                    System.out.println("Step #" + step + ": " + node.getGenerationRule().getName());
                else
                    System.out.println("Step #" + step + ": Initial state");
                if (heuristic != null)
                    System.out.println("Heuristic = " + heuristic.getValue(node.getState()));
                System.out.println(node.getState().getRepresentation());
                step++;
            }
        }

        System.out.println(ALGORITHM_RESULT_TEXT + args[0]);
        System.out.println(heuristic == null? NO_HEURISTIC_RESULT_TEXT :
                HEURISTIC_RESULT_TEXT + heuristic.toString());
        System.out.println(SUCCESS_RESULT_TEXT + (gpsEngine.isFailed()?FAILURE_TEXT:SUCCESS_TEXT));
        System.out.println(NODES_EXPANDED_RESULT_TEXT + gpsEngine.getExplosionCounter());
        System.out.println(STATES_ANALYZED_RESULT_TEXT + gpsEngine.getBestCosts().size());
        System.out.println(NODES_FRONTIER_RESULT_TEXT + gpsEngine.getOpen().size()); //TODO: IDDFS clears open structure, and lose information
        if (!gpsEngine.isFailed()) {
            System.out.println(SOLUTION_DEEP_RESULT_TEXT + path.size());
            System.out.println(SOLUTION_COST_RESULT_TEXT + gpsEngine.getSolutionNode().getCost());
        }
        System.out.println(TIME_RESULT_TEXT + timeOfProcess + TIME_UNIT_RESULT_TEXT);
    }

    private static SearchStrategy parseSearchStrategy(String s) {
        SearchStrategy searchStrategy;
        switch (s) {
            case "BFS":
                searchStrategy = BFS;
                break;
            case "DFS":
                searchStrategy = DFS;
                break;
            case "IDDFS":
                searchStrategy = IDDFS;
                break;
            case "GREEDY":
                searchStrategy = GREEDY;
                break;
            case "ASTAR":
                searchStrategy = ASTAR;
                break;
            default:
                throw new RuntimeException("Invalid search strategy");
        }
        return searchStrategy;
    }

    private static Heuristic parseHeuristic(String s) {
        switch (s) {
            case "0":
                return new HeuristicGridLock1();
            case "1":
                return null; // TODO: heuristic 1
            case "2":
                return new RandomHeuristic();
            default:
                return null;
        }
    }
}