package main;

import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;
import gps.api.Heuristic;
import gps.api.Problem;
import gridlock.*;

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
    private static String STEP_TEXT                   = "Step #";
    private static String INITIAL_STATE_TEXT          = ": Initial state";

    private static int MAX_ARGS                      = 3;
    private static SearchStrategy DEFAULT_SEARCH_STRATEGY = BFS;

    public static void main(String[] args) throws FileNotFoundException {
        // Parse parameters
        SearchStrategy searchStrategy = DEFAULT_SEARCH_STRATEGY;
        Heuristic heuristic = new RandomHeuristic();

        if (args.length > MAX_ARGS)
            args = new String[]{"BFS"};
        else
            searchStrategy = parseSearchStrategy(args[0]);

        Problem problem = new ProblemGridLock(new BoardGridLock("boardsJSON/level8.json")); //TODO: elija el nivel

        if (searchStrategy == ASTAR || searchStrategy == GREEDY) {
            int depth = 0;
            if(args.length == 3) {
                depth = Integer.getInteger(args[2]);
            }
            heuristic = parseHeuristic(args[1], problem, depth);
        }

        // Start run
        long timeOfProcess = System.currentTimeMillis(); //start time
        GPSEngine gpsEngine = new GPSEngine(problem, searchStrategy, heuristic);
        gpsEngine.findSolution();
        timeOfProcess = System.currentTimeMillis() - timeOfProcess; // duration = finish time - start time

        // Start prints of results
        LinkedList<GPSNode> path = new LinkedList<>();

        if (!gpsEngine.isFailed()) {
            GPSNode current = gpsEngine.getSolutionNode();
            while (current.getParent() != null) {
                path.push(current);
                current = current.getParent();
            }
            path.push(current);

            // Print the path to solution
            int step = 1;
            for (GPSNode node: path) {
                if (node.getGenerationRule() != null)
                    System.out.println(STEP_TEXT + step + ": " + node.getGenerationRule().getName());
                else
                    System.out.println(STEP_TEXT + step + INITIAL_STATE_TEXT);
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
        System.out.println(NODES_FRONTIER_RESULT_TEXT + gpsEngine.getOpen().size());
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

    private static Heuristic parseHeuristic(String s, Problem problem, int depth) {
        switch (s) {
            case "0":
                return new HeuristicGridLock1();
            case "1":
                return new HeuristicGridLockBfs(depth, problem, new HeuristicGridLock1());
            case "2":
                return new RandomHeuristic();
            default:
                return null;
        }
    }
}
