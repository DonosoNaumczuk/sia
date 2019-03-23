import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;
import gps.api.Heuristic;
import gridlock.BoardGridLock;
import gridlock.ProblemGridLock;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static gps.SearchStrategy.*;

public class GridLockSolver {
    //TODO: I think this Strings must be in English to be consistent
    private static String ALGORITHM_RESULT_TEXT       = "Se uso el algoritmo: ";
    private static String NO_HEURISTIC_RESULT_TEXT    = "No se uso una heuristica";
    private static String HEURISTIC_RESULT_TEXT       = "La heuristica usada es: ";
    private static String SUCCESS_RESULT_TEXT         = "La busqueda fue un ";
    private static String SUCCESS_TEXT                = "exito";
    private static String FAILURE_TEXT                = "fracaso";
    private static String NODES_EXPANDED_RESULT_TEXT  = "Nodos expandidos: ";
    private static String STATES_ANALYZED_RESULT_TEXT = "Estados analisados: ";
    private static String NODES_FRONTIER_RESULT_TEXT  = "Nodos frontera: ";
    private static String SOLUTION_DEEP_RESULT_TEXT   = "Profundidad de la solucion: ";
    private static String SOLUTION_COST_RESULT_TEXT   = "Costo de la solucion: " ;
    private static String TIME_RESULT_TEXT            = "Tiempo de procesamiento: " ;
    private static String TIME_UNIT_RESULT_TEXT       = " ms" ;

    public static void main(String[] args) throws FileNotFoundException {
        new BoardGridLock("boardsJSON/board1.json");

        //Parse parameters
        SearchStrategy searchStrategy = parseSearchStrategy(args[0]);
        Heuristic heuristic = parseHeuristic(args[1]);

        //Start run
        long timeOfProcess = System.currentTimeMillis(); //star time
        GPSEngine gpsEngine = new GPSEngine(new ProblemGridLock(new BoardGridLock()), searchStrategy, heuristic);
        gpsEngine.findSolution();
        timeOfProcess = System.currentTimeMillis() - timeOfProcess; // duration = star time - finish time

        //Star prints of results
        System.out.println(ALGORITHM_RESULT_TEXT + args[0]);
        System.out.println((heuristic == null)?NO_HEURISTIC_RESULT_TEXT:HEURISTIC_RESULT_TEXT + heuristic.toString());
        System.out.println(SUCCESS_RESULT_TEXT + (gpsEngine.isFailed()?FAILURE_TEXT:SUCCESS_TEXT));
        System.out.println(NODES_EXPANDED_RESULT_TEXT + gpsEngine.getExplosionCounter());
        System.out.println(STATES_ANALYZED_RESULT_TEXT + gpsEngine.getBestCosts().size());
        System.out.println(NODES_FRONTIER_RESULT_TEXT + gpsEngine.getOpen().size());//TODO: nose si cuenta la solucion en la fontera
        if(!gpsEngine.isFailed()) {
            LinkedList<GPSNode> path = new LinkedList<>();
            GPSNode current = gpsEngine.getSolutionNode();
            while (current.getParent() != null) {
                path.push(current);
                current = current.getParent();
            }
            System.out.println(SOLUTION_DEEP_RESULT_TEXT + path.size());
            System.out.println(SOLUTION_COST_RESULT_TEXT + gpsEngine.getSolutionNode().getCost());

            // print the path to solution
            for (GPSNode node: path) {
                if(node.getGenerationRule() != null) {
                    System.out.println(node.getGenerationRule().toString());
                }
                System.out.println(node.toString()); //TODO: assumo que el toString imprime una representacion del tablero
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
                throw new RuntimeException("Invalid search strategy");//TODO: nose si deberia ser una excepcion
        }
        return searchStrategy;
    }

    private static Heuristic parseHeuristic(String s) {
        Heuristic heuristic = null;
        switch (s) {
            case "0":
                //TODO: heuristic 1
                break;
            case "1":
                //TODO: heuristic 1
                break;
            default:
                throw new RuntimeException("Invalid heuristic");//TODO: nose si deberia ser una excepcion
        }
        return heuristic;
    }
}
