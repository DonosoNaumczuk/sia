package main;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gridlock.*;
import com.google.gson.Gson;
import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.Problem;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static ar.edu.itba.sia.gps.SearchStrategy.*;

public class GridLockSolver {
    private static final int DEFAULT_IDDFS_DEPTH = 50;
    private static String ALGORITHM_RESULT_TEXT       = "\033[0;1mSearch strategy: \u001B[0m";
    private static String NO_HEURISTIC_RESULT_TEXT    = "\033[0;1mHeuristics was not used \u001B[0m";
    private static String HEURISTIC_RESULT_TEXT       = "\033[0;1mHeuristic description: \u001B[0m";
    private static String SUCCESS_RESULT_TEXT         = "\033[0;1mThe search was a ";
    private static String SUCCESS_TEXT                = "\033[1;32msuccess\u001B[0m";
    private static String FAILURE_TEXT                = "\033[1;31mfailure\u001B[0m";
    private static String NODES_EXPANDED_RESULT_TEXT  = "\033[0;1mExpanded nodes: \u001B[0m";
    private static String STATES_ANALYZED_RESULT_TEXT = "\033[0;1mAnalyzed states: \u001B[0m";
    private static String NODES_FRONTIER_RESULT_TEXT  = "\033[0;1mFrontier nodes: \u001B[0m";
    private static String SOLUTION_DEEP_RESULT_TEXT   = "\033[0;1mSolution depth: \u001B[0m";
    private static String SOLUTION_COST_RESULT_TEXT   = "\033[0;1mSolution cost: \u001B[0m";
    private static String TIME_RESULT_TEXT            = "\033[0;1mProcess time: \u001B[0m";
    private static String TIME_UNIT_RESULT_TEXT       = " ms" ;
    private static String STEP_TEXT                   = "Step #";
    private static String INITIAL_STATE_TEXT          = ": Initial state";

    private static int MAX_ARGS                           = 4;
    private static SearchStrategy DEFAULT_SEARCH_STRATEGY = BFS;

    /**
     * args[0]: path to json level
     * args[1]: strategy
     * args[2]: heuristic (for ASTAR or GREEDY) | depth (for IDDFS)
     * args[3]: max depth (for bfs heuristic)
    */
    public static void main(String[] args) throws FileNotFoundException {
        Heuristic heuristic = null;

        if (args.length > MAX_ARGS || args.length < 2)
            args = new String[]{"./boardsJSON/level1.json", "BFS"};

        SearchStrategy searchStrategy = parseSearchStrategy(args[1]);

        // Parse parameters
        BoardGridLock startingBoard = new BoardGridLock(args[0]);
        int depth = 0;

        Problem problem = new ProblemGridLock(startingBoard); //Set board file

        if (searchStrategy == ASTAR || searchStrategy == GREEDY) {
            depth = 0;
            if(args.length > 3) {
                depth = Integer.valueOf(args[3]);
            }
            heuristic = parseHeuristic(args[2], problem, depth);
        }
        else if (searchStrategy == IDDFS) {
            depth = DEFAULT_IDDFS_DEPTH;
            if(args.length > 2) {
                depth = Integer.valueOf(args[2]);
            }
        }

        // Start run
        GPSEngine gpsEngine = new GPSEngine(problem, searchStrategy, heuristic);
        gpsEngine.setDepth(depth);
        long timeOfProcess = System.currentTimeMillis(); //start time
        gpsEngine.findSolution();
        timeOfProcess = System.currentTimeMillis() - timeOfProcess; // duration = finish time - start time

        // Start prints of results
        LinkedList<GPSNode> path = new LinkedList<>();

        if (!gpsEngine.isFailed()) {
            ProblemDefinitionJson problemDefinitionJson = new ProblemDefinitionJson();
            problemDefinitionJson.boards = new LinkedList<>();
            problemDefinitionJson.boards.add(startingBoard.asJSONBoard());
            GPSNode current = gpsEngine.getSolutionNode();
            while (current.getParent() != null) {
                path.push(current);
                current = current.getParent();
            }
            path.push(current);

            // Print the path to solution
            int step = 1;
            for (GPSNode node : path) {
                if (node.getGenerationRule() != null)
                    System.out.println(STEP_TEXT + step + ": " + node.getGenerationRule().getName());
                else
                    System.out.println(STEP_TEXT + step + INITIAL_STATE_TEXT);
                System.out.println(node.getState().getRepresentation());
                step++;
                problemDefinitionJson.boards.add(((StateGridLock) node.getState()).getBoard().asJSONBoard());
            }
            File problemFile = new File("JsonProblem/problem.json");
            problemDefinitionJson.stepCount = step;
            problemDefinitionJson.sideLength = startingBoard.getBoard().length;
            problemDefinitionJson.exit = startingBoard.getExit();
            try {
                problemFile.createNewFile();
                FileWriter writer = new FileWriter(problemFile);
                writer.write(new Gson().toJson(problemDefinitionJson));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(ALGORITHM_RESULT_TEXT + args[1]);
        System.out.println(heuristic == null ? NO_HEURISTIC_RESULT_TEXT :
                HEURISTIC_RESULT_TEXT + heuristic.toString());
        System.out.println(SUCCESS_RESULT_TEXT + (gpsEngine.isFailed() ? FAILURE_TEXT : SUCCESS_TEXT));
        System.out.println(NODES_EXPANDED_RESULT_TEXT + gpsEngine.getExplosionCounter());
        System.out.println(STATES_ANALYZED_RESULT_TEXT + gpsEngine.getBestCosts().size());
        System.out.println(NODES_FRONTIER_RESULT_TEXT + gpsEngine.getOpen().size());
        if (!gpsEngine.isFailed()) {
            System.out.println(SOLUTION_DEEP_RESULT_TEXT + (path.size()-1));
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
                return new HeuristicGridLockDistance();
            case "1":
                return new HeuristicGridLockDistanceAndBlocks();
            case "2":
                return new HeuristicGridLockBfs(depth, problem, new HeuristicGridLockDistanceAndBlocks());
            default:
                return null;
        }
    }

    private static class ProblemDefinitionJson {
        int sideLength;
        int stepCount;
        Point exit;
        List<BoardGridLock.JSONGridLockBoardParser.JSONBoard> boards;
    }
}
