package ar.edu.itba.sia;

import java.util.*;

import static ar.edu.itba.sia.ReplaceMethod.FIRST;

public class GeneticAlgorithmEngine<C extends Chromosome<C>> {
    private int numberOfGenerationsToMakeChecks;
    private PriorityQueue<C> currentPopulation;
    private int generationNumber;
    private int maxGenerationNumber;
    private int goalFitness;
    private ReplaceMethod replaceMethod;
    private List<List<SelectionMethod<C>>> selectionMethods;
    private int quantityOfFathersToSelect;

    public GeneticAlgorithmEngine(PriorityQueue<C> initialPopulation,
                                  int numberOfGenerationsToMakeChecks,
                                  int maxGenerationNumber, int goalFitness,
                                  ReplaceMethod replaceMethod,
                                  List<List<SelectionMethod<C>>> selectionMethods,
                                  int quantityOfFathersToSelect) {
        this.currentPopulation               = initialPopulation;//TODO:order inverted
        this.generationNumber                = 0;
        this.maxGenerationNumber             = maxGenerationNumber;
        this.numberOfGenerationsToMakeChecks = numberOfGenerationsToMakeChecks;
        this.goalFitness                     = goalFitness;
        this.replaceMethod                   = replaceMethod;
        this.selectionMethods                = selectionMethods;
        this.quantityOfFathersToSelect       = quantityOfFathersToSelect;
    }

    public GeneticAlgorithmEngine(PriorityQueue<C> initialPopulation,
                                  int numberOfGenerationsToMakeChecks,
                                  int maxGenerationNumber, int goalFitness,
                                  List<List<SelectionMethod<C>>> selectionMethods) {
        this(initialPopulation, numberOfGenerationsToMakeChecks, maxGenerationNumber, goalFitness,
                FIRST, selectionMethods, initialPopulation.size());
    }

    public PriorityQueue<C> process() {
        boolean flag = true;
        PriorityQueue<C> previousPopulation = currentPopulation;//TODO:order inverted
        C currentBest = currentPopulation.peek();
        C previousBest = currentBest;
        while(currentBest.getFitness() < goalFitness &&
                generationNumber < maxGenerationNumber && flag) {
            PriorityQueue<C> newGeneration;

            switch (replaceMethod) {
                case THIRD:
                    newGeneration = handleThird();
                    break;
                default:
                    newGeneration = handleSecond(); //SET first replace method as default
                    break;
            }

            generationNumber++;
            currentPopulation = newGeneration;
            currentBest = currentPopulation.peek();
            if(generationNumber % numberOfGenerationsToMakeChecks == 0) {
                /* Content check and structural check */
                flag = previousBest.compareTo(currentBest) <= 0 && structuralCheck(currentPopulation, previousPopulation);
                previousBest = currentBest;
                previousPopulation = currentPopulation;
            }
        }
        return currentPopulation;
    }

    private PriorityQueue<C> handleSecond() {
        PriorityQueue<C> newGeneration = new PriorityQueue<>();
        ArrayList<C> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<C> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray);

        //breed
        ArrayList<C> sons = breed(fathers);

        //mutate
        for (C son:sons) {
            newGeneration.add(son.mutate());
        }

        if(currentPopulation.size() != quantityOfFathersToSelect) {
            //select N-k form population
            newGeneration.addAll(selectK(currentPopulation.size() - quantityOfFathersToSelect,
                    currentPopulationArray));
        }

        return newGeneration;
    }

    private PriorityQueue<C> handleThird() {
        PriorityQueue<C> newGeneration = new PriorityQueue<>();
        ArrayList<C> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<C> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray);

        //breed
        ArrayList<C> sons = breed(fathers);


        //select N-k from N
        ArrayList<C> newGenerationArrayPart1 = selectK(currentPopulationArray.size() -
                quantityOfFathersToSelect, currentPopulationArray);

        //mutate
        for (C son:sons) {
            currentPopulationArray.add(son.mutate());
        }

        //select k from N+K
        ArrayList<C> newGenerationArrayPart2 = selectK(quantityOfFathersToSelect, currentPopulationArray);

        newGeneration.addAll(newGenerationArrayPart1);
        newGeneration.addAll(newGenerationArrayPart2);

        return newGeneration;
    }

//    private C getBest(PriorityQueue<C> population) {//TODO:maybe order by fitness
//        C best = population.peek();
//        for (C current: population) {
//            if(best.compareTo(current) > 0) {//check is is < or >
//                best = current;
//            }
//        }
//        return best;
//    }

    private ArrayList<C> selectK(int quantity, ArrayList<C> population) {
        ArrayList<C> fathers = new ArrayList<>();
        int remaining = quantity;
        Iterator<SelectionMethod<C>> iterator = selectionMethods.get(0).iterator();
        SelectionMethod<C> s = iterator.next();
        int k;
        while(iterator.hasNext()) {
            k = (int) s.getProportion() * quantity;
            remaining = remaining - k;
            fathers.addAll(s.select(population, k));
            s = iterator.next();
        }
        fathers.addAll(s.select(population, remaining));
        return fathers;
    }

    private ArrayList<C> breed(ArrayList<C> fathers) {
        ArrayList<C> sons = new ArrayList<>();
        Iterator<C> iteratorFathers = fathers.iterator();
        C c1 = iteratorFathers.next();
        C c2 = c1;
        while (iteratorFathers.hasNext()) {
            c2 = iteratorFathers.next();
            sons.add(c2.crossover(c1));
            sons.add(c1.crossover(c2));
            if(iteratorFathers.hasNext()) {
                c1 = iteratorFathers.next();
            }
        }
        if(sons.size() != fathers.size()) { //TODO: maybe add random
            sons.add(c1.crossover(c2));
        }
        return sons;
    }

    private boolean structuralCheck(PriorityQueue<C> currentPopulation,
                                    PriorityQueue<C> previousPopulation) {
        boolean flag = true;
        int i = 0;
        for (C c:currentPopulation) {
            if(previousPopulation.contains(c)) {
                i++;
            }
        }
        if(i == currentPopulation.size()) {
            flag = false;
        }
        return flag;
    }
}

