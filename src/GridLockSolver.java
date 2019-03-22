import gps.GPSEngine;
import gps.GPSNode;
import gridlock.BoardGridLock;
import gridlock.ProblemGridLock;

import java.util.LinkedList;

import static gps.SearchStrategy.*;

public class GridLockSolver {
    public static void main(String[] args) {
        //TODO: Initialize GPSEngine from args
        long timeOfProcess = System.currentTimeMillis();
        GPSEngine gpsEngine = new GPSEngine(new ProblemGridLock(new BoardGridLock()), BFS, null);
        gpsEngine.findSolution();
        timeOfProcess = System.currentTimeMillis() - timeOfProcess;

        //TODO: print parameters
        System.out.println("La busqueda fue un " + (gpsEngine.isFailed()?"fracaso":"exito"));
        System.out.println("Nodos expandidos: " + gpsEngine.getExplosionCounter());
        //TODO: cantidad de estados analizados se sacan repetidos
        System.out.println("Nodos frontera: " + gpsEngine.getOpen().size());//TODO: nose si cuenta la solucion en la fontera
        if(!gpsEngine.isFailed()) {
            LinkedList<GPSNode> path = new LinkedList<>();
            GPSNode current = gpsEngine.getSolutionNode();
            while (current.getParent() != null) {
                path.push(current);
                current = current.getParent();
            }
            System.out.println("Profundidad de la solucion: " + path.size());
            System.out.println("Costo de la solucion: " + gpsEngine.getSolutionNode().getCost());
            for (GPSNode node: path) { // print the path to solution
                if(node.getGenerationRule() != null) {
                    System.out.println(node.getGenerationRule().toString());
                }
                System.out.println(node.toString());
            }
            System.out.println("Tiempo de procesamiento: " + timeOfProcess + " ms");
        }
    }
}
