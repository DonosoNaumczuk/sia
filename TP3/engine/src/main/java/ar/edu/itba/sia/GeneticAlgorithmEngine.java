package ar.edu.itba.sia;

import java.util.*;

import static ar.edu.itba.sia.ReplaceMethod.FIRST;

public class GeneticAlgorithmEngine {
    private int numberOfGenerationsToMakeChecks;
    private PriorityQueue<Chromosome> currentPopulation;
    private int generationNumber;
    private int maxGenerationNumber;
    private int goalFitness;
    private ReplaceMethod replaceMethod;
    private List<List<SelectionMethod>> selectionMethods;
    private int quantityOfFathersToSelect;

    public GeneticAlgorithmEngine(PriorityQueue<Chromosome> initialPopulation,
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

    public GeneticAlgorithmEngine(PriorityQueue<Chromosome> initialPopulation,
                                  int numberOfGenerationsToMakeChecks,
                                  int maxGenerationNumber, int goalFitness,
                                  List<List<SelectionMethod>> selectionMethods) {
        this(initialPopulation, numberOfGenerationsToMakeChecks, maxGenerationNumber, goalFitness,
                FIRST, selectionMethods, initialPopulation.size());
    }

    public PriorityQueue<Chromosome> process() {
        boolean flag = true;
        PriorityQueue<Chromosome> previousPopulation = currentPopulation;//TODO:order inverted
        Chromosome currentBest = currentPopulation.peek();
        Chromosome previousBest = currentBest;
        while(currentBest.getFitness() < goalFitness &&
                generationNumber < maxGenerationNumber && flag) {
            PriorityQueue<Chromosome> newGeneration;

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

    private PriorityQueue<Chromosome> handleSecond() {
        PriorityQueue<Chromosome> newGeneration = new PriorityQueue<>();
        ArrayList<Chromosome> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<Chromosome> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray);

        //breed
        ArrayList<Chromosome> sons = breed(fathers);

        //mutate
        for (Chromosome son:sons) {
            newGeneration.add(son.mutate());
        }

        if(currentPopulation.size() != quantityOfFathersToSelect) {
            //select N-k form population
            newGeneration.addAll(selectK(currentPopulation.size() - quantityOfFathersToSelect,
                    currentPopulationArray));
        }

        return newGeneration;
    }

    private PriorityQueue<Chromosome> handleThird() {
        PriorityQueue<Chromosome> newGeneration = new PriorityQueue<>();
        ArrayList<Chromosome> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<Chromosome> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray);

        //breed
        ArrayList<Chromosome> sons = breed(fathers);


        //select N-k from N
        ArrayList<Chromosome> newGenerationArrayPart1 = selectK(currentPopulationArray.size() -
                quantityOfFathersToSelect, currentPopulationArray);

        //mutate
        for (Chromosome son:sons) {
            currentPopulationArray.add(son.mutate());
        }

        //select k from N+K
        ArrayList<Chromosome> newGenerationArrayPart2 = selectK(quantityOfFathersToSelect, currentPopulationArray);

        newGeneration.addAll(newGenerationArrayPart1);
        newGeneration.addAll(newGenerationArrayPart2);

        return newGeneration
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

    private ArrayList<Chromosome> selectK(int quantity, ArrayList<Chromosome> population) {
        ArrayList<Chromosome> fathers = new ArrayList<>();
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

    private ArrayList<Chromosome> breed(ArrayList<Chromosome> fathers) {
        ArrayList<Chromosome> sons = new ArrayList<>();
        Iterator<Chromosome> iteratorFathers = fathers.iterator();
        Chromosome c1 = iteratorFathers.next();
        Chromosome c2 = c1;
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

    private boolean structuralCheck(PriorityQueue<Chromosome> currentPopulation,
                                    PriorityQueue<Chromosome> previousPopulation) {
        boolean flag = true;
        int i = 0;
        for (Chromosome c:currentPopulation) {
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

