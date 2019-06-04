package ar.edu.itba.sia;

public class GeneticAlgorithmSolver {

    public static void main(String args[]) throws Exception {
        System.out.println();
        System.out.println("Loading configuration...");
        System.out.println("It can take a few seconds.");
        System.out.println();
        ItemCreator.createAllFromFiles(args[0]);
        Configuration configuration = new Configuration(args[1]);

        GeneticAlgorithmEngine<CharacterChromosome> engine;
        if (configuration.getReplaceMethod() == ReplaceMethod.FIRST) {
            engine = new GeneticAlgorithmEngine<>(configuration.getPopulation(),
                    configuration.getNumberOfGenerationsToMakeChecks(), configuration.getMaxGenerationNumber(),
                    configuration.getGoalFitness(), configuration.getSelectionMethods());
        }
        else {
            engine = new GeneticAlgorithmEngine<>(configuration.getPopulation(),
                    configuration.getNumberOfGenerationsToMakeChecks(), configuration.getMaxGenerationNumber(),
                    configuration.getGoalFitness(), configuration.getReplaceMethod(), configuration.getSelectionMethods(),
                    configuration.getQuantityOfFathersToSelect());
        }

        engine.process();
    }
}
