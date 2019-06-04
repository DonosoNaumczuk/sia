package ar.edu.itba.sia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static ar.edu.itba.sia.ReplaceMethod.FIRST;
import static ar.edu.itba.sia.ReplaceMethod.THIRD;

@SuppressWarnings("ALL")
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
        this.currentPopulation               = new PriorityQueue<>(Comparator.reverseOrder());
        this.currentPopulation.addAll(initialPopulation);
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

    public PriorityQueue<C> process() throws FileNotFoundException{
        boolean flag = true;
        PriorityQueue<C> previousPopulation = currentPopulation;
        C currentBest = currentPopulation.peek();
        C previousBest = currentBest;
        PrintWriter writer = new PrintWriter(new File("output.csv"));
        String s = "#generation,bestFitness,diversity,meanFitness,worstFitness,probability" + '\n';
        writer.write(s);
        printPopulation(writer);
        while (currentBest.getFitness() < goalFitness &&
                generationNumber < maxGenerationNumber && flag) {
            PriorityQueue<C> newGeneration;

            if (replaceMethod == THIRD)
                newGeneration = handleThird();
            else
                newGeneration = handleSecond(); //SET first replace method as default

            generationNumber++;
            currentPopulation = newGeneration;
            currentBest = currentPopulation.peek();
            printPopulation(writer);
            if (generationNumber % numberOfGenerationsToMakeChecks == 0) {
                /* Content check and structural check */
                flag = previousBest.compareTo(currentBest) <= 0 && structuralCheck(currentPopulation, previousPopulation);
                previousBest = currentBest;
                previousPopulation = currentPopulation;
            }
        }
        writer.close();
        return currentPopulation;
    }

    private PriorityQueue<C> handleSecond() {
        PriorityQueue<C> newGeneration = new PriorityQueue<>(Comparator.reverseOrder());
        ArrayList<C> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<C> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray, 0);

        //breed
        ArrayList<C> sons = breed(fathers);

        //mutate
        for (C son:sons) {
            newGeneration.add(son.mutate());
        }

        if (currentPopulation.size() != quantityOfFathersToSelect) {
            //select N-k form population
            newGeneration.addAll(selectK(currentPopulation.size() - quantityOfFathersToSelect,
                    currentPopulationArray, 1));
        }

        newGeneration.peek().updateMutation();

        return newGeneration;
    }

    private PriorityQueue<C> handleThird() {
        PriorityQueue<C> newGeneration = new PriorityQueue<>(Comparator.reverseOrder());

        ArrayList<C> currentPopulationArray = new ArrayList<>(new ArrayList<>(currentPopulation));

        //select k fathers
        ArrayList<C> fathers = selectK(quantityOfFathersToSelect, currentPopulationArray, 0);

        //breed
        ArrayList<C> sons = breed(fathers);


        //select N-k from N
        ArrayList<C> newGenerationArrayPart1 = selectK(currentPopulationArray.size() -
                quantityOfFathersToSelect, currentPopulationArray, 1);

        //mutate
        for (C son:sons) {
            currentPopulationArray.add(son.mutate());
        }

        //select k from N+K
        ArrayList<C> newGenerationArrayPart2 = selectK(quantityOfFathersToSelect, currentPopulationArray, 1);

        newGeneration.addAll(newGenerationArrayPart1);
        newGeneration.addAll(newGenerationArrayPart2);

        newGeneration.peek().updateMutation();

        return newGeneration;
    }

    private ArrayList<C> selectK(int quantity, ArrayList<C> population, int type) {
        ArrayList<C> fathers = new ArrayList<>();
        int remaining = quantity;
        Iterator<SelectionMethod<C>> iterator = selectionMethods.get(type).iterator();
        SelectionMethod<C> s = iterator.next();
        int k;
        while (iterator.hasNext()) {
            k = (int) (s.getProportion() * quantity);
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
            sons.addAll(c2.crossover(c1));
            if (iteratorFathers.hasNext()) {
                c1 = iteratorFathers.next();
            }
        }
        if (sons.size() != fathers.size()) {
            sons.add(c1.crossover(c2).get(0));
        }
        return sons;
    }

    private boolean structuralCheck(PriorityQueue<C> currentPopulation,
                                    PriorityQueue<C> previousPopulation) {
        boolean flag = true;
        int i = 0;
        for (C c:currentPopulation) {
            if (previousPopulation.contains(c)) {
                i++;
            }
        }
        if (i >= currentPopulation.size() / 2) {
            flag = false;
        }
        return flag;
    }

    private void printPopulation(PrintWriter writer) {
        HashSet<C> classesOfPopulation  = new HashSet<>(currentPopulation);
        double mean                     = 0;
        C last                          = null;

        for (C current : currentPopulation) {
            mean += current.getFitness();
            last = current;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(generationNumber);
        sb.append(',');
        sb.append(currentPopulation.peek().getFitness());
        sb.append(',');
        sb.append(classesOfPopulation.size());
        sb.append(',');
        sb.append(mean / currentPopulation.size());
        sb.append(',');
        sb.append(last.getFitness());
        sb.append(';');
        sb.append(currentPopulation.peek().getMutationMethod().getProbability());
        sb.append('\n');

        writer.write(sb.toString());
    }
}

