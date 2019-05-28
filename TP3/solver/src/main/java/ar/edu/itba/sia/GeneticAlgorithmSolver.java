package ar.edu.itba.sia;

import java.util.LinkedList;

public class GeneticAlgorithmSolver {

    public static void main(String args[]) {
        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(2); list1.add(3); list1.add(4);
        LinkedList<Integer> list2 = new LinkedList<>(list1);
        list1.remove();

        LinkedList<Integer> list = list1;
        for (int p = 0; p < 2; p++) {
            for (Integer i : list) {
                System.out.println(i);
                System.out.println("-------------");
            }
            list = list2;
        }
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
