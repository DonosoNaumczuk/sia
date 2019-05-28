package ar.edu.itba.sia;

import java.util.*;

import static ar.edu.itba.sia.ReplaceMethod.FIRST;

public class GeneticAlgorithmEngine<T extends Chromosome<T>> {
    private int numberOfGenerationsToMakeChecks;
    private PriorityQueue<T> currentPopulation;
    private int generationNumber;
    private int maxGenerationNumber;
    private int goalFitness;
    private ReplaceMethod replaceMethod;
    private List<List<SelectionMethod>> selectionMethods;
    private int quantityOfFathersToSelect;

    public GeneticAlgorithmEngine(PriorityQueue<T> initialPopulation,
                                  int numberOfGenerationsToMakeChecks,
                                  int maxGenerationNumber, int goalFitness,
                                  ReplaceMethod replaceMethod,
                                  List<List<SelectionMethod>> selectionMethods,
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

    public GeneticAlgorithmEngine(PriorityQueue<T> initialPopulation,
                                  int numberOfGenerationsToMakeChecks,
                                  int maxGenerationNumber, int goalFitness,
                                  List<List<SelectionMethod>> selectionMethods) {
        this(initialPopulation, numberOfGenerationsToMakeChecks, maxGenerationNumber, goalFitness,
                FIRST, selectionMethods, initialPopulation.size());
    }

    public PriorityQueue<T> process() {
        boolean flag = true;
        PriorityQueue<T> previousPopulation = currentPopulation;//TODO:order inverted
        T currentBest = currentPopulation.peek();
        T previousBest = currentBest;
        while(currentBest.getFitness() < goalFitness &&
                generationNumber < maxGenerationNumber && flag) {
            PriorityQueue<T> newGeneration;

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
                if(previousBest.compareTo(currentBest) > 0) { //content check
                    flag = false;
                }
                else {
                    flag = structuralCheck(currentPopulation, previousPopulation);
                }
                previousBest = currentBest;
                previousPopulation = currentPopulation;
            }
        }
        return currentPopulation;
    }

    private PriorityQueue<T> handleSecond() {
        PriorityQueue<T> newGeneration = new PriorityQueue<>();
        ArrayList<T> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<T> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray);

        //breed
        ArrayList<T> sons = breed(fathers);

        //mutate
        for (T son:sons) {
            newGeneration.add(son.mutate());
        }

        if(currentPopulation.size() != quantityOfFathersToSelect) {
            //select N-k form population
            newGeneration.addAll(selectK(currentPopulation.size() - quantityOfFathersToSelect,
                    currentPopulationArray));
        }

        return newGeneration;
    }

    private PriorityQueue<T> handleThird() {
        PriorityQueue<T> newGeneration = new PriorityQueue<>();
        ArrayList<T> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<T> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray);

        //breed
        ArrayList<T> sons = breed(fathers);


        //select N-k from N
        ArrayList<T> newGenerationArrayPart1 = selectK(currentPopulationArray.size() -
                quantityOfFathersToSelect, currentPopulationArray);

        //mutate
        for (T son:sons) {
            currentPopulationArray.add(son.mutate());
        }

        //select k from N+K
        ArrayList<T> newGenerationArrayPart2 = selectK(quantityOfFathersToSelect, currentPopulationArray);

        newGeneration.addAll(newGenerationArrayPart1);
        newGeneration.addAll(newGenerationArrayPart2);

        return newGeneration;
    }

//    private Chromosome getBest(PriorityQueue<Chromosome> population) {//TODO:maybe order by fitness
//        Chromosome best = population.peek();
//        for (Chromosome current: population) {
//            if(best.compareTo(current) > 0) {//check is is < or >
//                best = current;
//            }
//        }
//        return best;
//    }

    private ArrayList<T> selectK(int quantity, ArrayList<T> population) {
        ArrayList<T> fathers = new ArrayList<>();
        int remaining = quantity;
        Iterator<SelectionMethod> iterator = selectionMethods.get(0).iterator();
        SelectionMethod s = iterator.next();
        int k;
        while(iterator.hasNext()) {
            k = (int)s.getProportion() * quantity;
            remaining = remaining - k;
            fathers.addAll(s.select(population,k));
            s = iterator.next();
        }
        fathers.addAll(s.select(population, remaining));
        return fathers;
    }

    private ArrayList<T> breed(ArrayList<T> fathers) {
        ArrayList<T> sons = new ArrayList<>();
        Iterator<T> iteratorFathers = fathers.iterator();
        T c1 = iteratorFathers.next();
        T c2 = c1;
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

    private boolean structuralCheck(PriorityQueue<T> currentPopulation,
                                    PriorityQueue<T> previousPopulation) {
        boolean flag = true;
        int i = 0;
        for (T c:currentPopulation) {
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

