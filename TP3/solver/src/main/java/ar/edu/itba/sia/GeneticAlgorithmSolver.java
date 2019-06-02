package ar.edu.itba.sia;

public class GeneticAlgorithmSolver {

    public static void main(String args[]) throws Exception {
        ItemCreator.createAllFromFiles();
        Configuration configuration = new Configuration("./data/configuration.json");//TODO: parameter of program

        GeneticAlgorithmEngine<CharacterChromosome> engine;
        if(configuration.getReplaceMethod() == ReplaceMethod.FIRST) {
            engine = new GeneticAlgorithmEngine<CharacterChromosome>(configuration.getPopulation(),
                    configuration.getNumberOfGenerationsToMakeChecks(), configuration.getMaxGenerationNumber(),
                    configuration.getGoalFitness(), configuration.getSelectionMethods());
        }
        else {
            engine = new GeneticAlgorithmEngine<CharacterChromosome>(configuration.getPopulation(),
                    configuration.getNumberOfGenerationsToMakeChecks(), configuration.getMaxGenerationNumber(),
                    configuration.getGoalFitness(), configuration.getReplaceMethod(), configuration.getSelectionMethods(),
                    configuration.getQuantityOfFathersToSelect());
        }

        engine.process();
    }
}
