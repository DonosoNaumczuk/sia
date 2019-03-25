import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;
import gps.api.Heuristic;
import gridlock.BoardGridLock;
import gridlock.ProblemGridLock;
import gridlock.RandomHeuristic;

import java.util.LinkedList;

import static gps.SearchStrategy.*;

public class GridLockSolver {
    //TODO: I think this Strings must be in English to be consistent
    private static String ALGORITHM_RESULT_TEXT       = "Se usó el algoritmo: ";
    private static String NO_HEURISTIC_RESULT_TEXT    = "No se usó una heurística";
    private static String HEURISTIC_RESULT_TEXT       = "La heurística usada es: ";
    private static String SUCCESS_RESULT_TEXT         = "La búsqueda fue un ";
    private static String SUCCESS_TEXT                = "éxito";
    private static String FAILURE_TEXT                = "fracaso";
    private static String NODES_EXPANDED_RESULT_TEXT  = "Nodos expandidos: ";
    private static String STATES_ANALYZED_RESULT_TEXT = "Estados analizados: ";
    private static String NODES_FRONTIER_RESULT_TEXT  = "Nodos frontera: ";
    private static String SOLUTION_DEEP_RESULT_TEXT   = "Profundidad de la solución: ";
    private static String SOLUTION_COST_RESULT_TEXT   = "Costo de la solución: " ;
    private static String TIME_RESULT_TEXT            = "Tiempo de procesamiento: " ;
    private static String TIME_UNIT_RESULT_TEXT       = " ms" ;

    public static void main(String[] args) {
        // Parse parameters
        SearchStrategy searchStrategy = parseSearchStrategy(args[0]);
        Heuristic heuristic = parseHeuristic(args[1]);

        // Start run
        long timeOfProcess = System.currentTimeMillis(); //start time
        GPSEngine gpsEngine = new GPSEngine(new ProblemGridLock(new BoardGridLock()), searchStrategy, heuristic);
        gpsEngine.findSolution();
        timeOfProcess = System.currentTimeMillis() - timeOfProcess; // duration = finish time - start time

        // Start prints of results
        System.out.println(ALGORITHM_RESULT_TEXT + args[0]);
        System.out.println((heuristic == null)?NO_HEURISTIC_RESULT_TEXT:HEURISTIC_RESULT_TEXT + heuristic.toString());
        System.out.println(SUCCESS_RESULT_TEXT + (gpsEngine.isFailed()?FAILURE_TEXT:SUCCESS_TEXT));
        System.out.println(NODES_EXPANDED_RESULT_TEXT + gpsEngine.getExplosionCounter());
        System.out.println(STATES_ANALYZED_RESULT_TEXT + gpsEngine.getBestCosts().size());
        System.out.println(NODES_FRONTIER_RESULT_TEXT + gpsEngine.getOpen().size());//TODO: no sé si cuenta la solución en la fontera
        if(!gpsEngine.isFailed()) {
            LinkedList<GPSNode> path = new LinkedList<>();
            GPSNode current = gpsEngine.getSolutionNode();
            while (current.getParent() != null) {
                path.push(current);
                current = current.getParent();
            }
            System.out.println(SOLUTION_DEEP_RESULT_TEXT + path.size());
            System.out.println(SOLUTION_COST_RESULT_TEXT + gpsEngine.getSolutionNode().getCost());

            // Print the path to solution
            int step = 1;
            for (GPSNode node: path) {
                if (node.getGenerationRule() != null)
                    System.out.println("Step #" + step + ": " + node.getGenerationRule().getName());
                else
                    System.out.println("Step #" + step + ": Initial state");
                System.out.println(node.getState().getRepresentation());
                step++;
            }
            System.out.println(TIME_RESULT_TEXT + timeOfProcess + TIME_UNIT_RESULT_TEXT);
        }
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
                throw new RuntimeException("Invalid search strategy"); // TODO: no se si debería ser una excepción
        }
        return searchStrategy;
    }

    private static Heuristic parseHeuristic(String s) {
        Heuristic heuristic = null;
        switch (s) {
            case "0":
                // TODO: heuristic 1
                break;
            case "1":
                // TODO: heuristic 1
            case "2":
                return new RandomHeuristic();
            default:
                throw new RuntimeException("Invalid heuristic");// TODO: no sé si debería ser una excepción
        }
        return heuristic;
    }
}
