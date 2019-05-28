package ar.edu.itba.sia;

import java.util.LinkedList;

public class GeneticAlgorithmSolver {

    public static void main(String args[]) {
        //TODO: remove, just testing if can see engine module from solver module
        GeneticAlgorithmEngine engine = new GeneticAlgorithmEngine();
    }

    //mutacion no uniforme->como cambia la probabilidad//la decidimos nosotros
    //mutacion uniforme->la probabilidad se sac de la configuracion
    //SELECCION: A*(metodo1) + (1-A)*(metodo2) ->A es configurable?
    //REEMPLAZO: B*(m ́etodo3) + (1-B)*(m ́etodo4) ->B es configurable?
    //Como elegir la poblacion inicial? cuantos individuos usaria?
    //Al recombinar los padres mueren?->metodo de reemplazo 1, osea no se pueden repetir
    //METODO 3 de reemplazo: se quedan los N-k que no se modificaron y
    // luego de los N+k se eligen k ->se pueden repetir ESTARA BIEN?
    //l y r es rmandom en cada cruza angular
}
