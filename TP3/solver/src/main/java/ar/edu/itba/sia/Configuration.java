package ar.edu.itba.sia;

import ar.edu.itba.sia.crossovers.AnnularCrossover;
import ar.edu.itba.sia.crossovers.DoublePointCrossover;
import ar.edu.itba.sia.crossovers.SinglePointCrossover;
import ar.edu.itba.sia.crossovers.UniformCrossover;
import ar.edu.itba.sia.interfaces.CrossoverMethod;
import ar.edu.itba.sia.interfaces.MutationMethod;
import ar.edu.itba.sia.mutations.MutationMethodNoUniformMultGen;
import ar.edu.itba.sia.mutations.MutationMethodNoUniformOneGen;
import ar.edu.itba.sia.mutations.MutationMethodUniformMultGen;
import ar.edu.itba.sia.mutations.MutationMethodUniformOneGen;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static ar.edu.itba.sia.ReplaceMethod.FIRST;
import static ar.edu.itba.sia.ReplaceMethod.SECOND;
import static ar.edu.itba.sia.ReplaceMethod.THIRD;

public class Configuration {
    private static final Long DEFAULT_SEED = -1L;
    private int numberOfGenerationsToMakeChecks;
    private PriorityQueue<CharacterChromosome> population;
    private int maxGenerationNumber;
    private int goalFitness;
    private ReplaceMethod replaceMethod;
    private List<List<SelectionMethod<CharacterChromosome>>> selectionMethods;
    private int quantityOfFathersToSelect;

    public Configuration(String path) throws FileNotFoundException {
        FileReader reader = new FileReader(path);
        JsonParser parser = new JsonParser();
        JSONConfigurationParser.JSONConfiguration jsonConfiguration = new Gson().fromJson(parser.parse(reader),
                JSONConfigurationParser.JSONConfiguration.class);
        configureRandom(jsonConfiguration);

        this.population                      = parserPopulation(jsonConfiguration);
        this.maxGenerationNumber             = jsonConfiguration.cutCriteria.maxGeneration;
        this.numberOfGenerationsToMakeChecks = jsonConfiguration.cutCriteria.quantityOfGenerationToPerformChecks;
        this.goalFitness                     = jsonConfiguration.cutCriteria.fitness;
        this.replaceMethod                   = parseReplaceMethod(jsonConfiguration.replaceMethod.type);
        this.selectionMethods                = parserSelectionsMethods(
                                                    jsonConfiguration.firstSelectionMethod,
                                                    jsonConfiguration.secondSelectionMethod,
                                                    this.replaceMethod == THIRD,
                                                    maxGenerationNumber);
        this.quantityOfFathersToSelect       = jsonConfiguration.replaceMethod.k;
    }

    private void configureRandom(JSONConfigurationParser.JSONConfiguration jsonConfiguration) {
        if (jsonConfiguration.randomSeed != null && !jsonConfiguration.randomSeed.equals(DEFAULT_SEED)) {
            RandomStatic.setSeed(jsonConfiguration.randomSeed);
        }
        RandomStatic.initialize();
    }

    private static PriorityQueue<CharacterChromosome> parserPopulation(
            JSONConfigurationParser.JSONConfiguration jsonConfiguration) {
        PriorityQueue<CharacterChromosome> population = new PriorityQueue<>();
        ItemStorage itemStorage = ItemStorage.getInstance();

        CrossoverMethod<CharacterChromosome> crossoverMethod = parserCrossoverMethod(jsonConfiguration.crossover,
                jsonConfiguration.crossProbability);
        MutationMethod<CharacterChromosome> mutationMethod   = parserMutationMethod(jsonConfiguration.mutation);
        double mult[]                                        = parserClass(jsonConfiguration.classType);

        int i = 0;
        CharacterChromosome currentCharacter;
        int rndBoots, rndBreastPlate, rndGloves, rndHelmet, rndWeapon;
        while (i < jsonConfiguration.populationQuantity) {
            rndBoots         = RandomStatic.nextInt(itemStorage.getBoots().length);
            rndBreastPlate   = RandomStatic.nextInt(itemStorage.getBreastPlates().length);
            rndGloves        = RandomStatic.nextInt(itemStorage.getGloves().length);
            rndHelmet        = RandomStatic.nextInt(itemStorage.getHelmets().length);
            rndWeapon        = RandomStatic.nextInt(itemStorage.getHelmets().length);
            currentCharacter = new CharacterChromosome(crossoverMethod, mutationMethod, jsonConfiguration.multipliers.strength,
                    jsonConfiguration.multipliers.agility, jsonConfiguration.multipliers.expertise,
                    jsonConfiguration.multipliers.resistance, jsonConfiguration.multipliers.life, mult[0], mult[1],
                    1.3 + RandomStatic.nextDouble() * (2 - 1.3), itemStorage.getBoots()[rndBoots],
                    itemStorage.getBreastPlates()[rndBreastPlate], itemStorage.getGloves()[rndGloves],
                    itemStorage.getHelmets()[rndHelmet], itemStorage.getWeapons()[rndWeapon]);
            population.add(currentCharacter);
            i++;
        }

        return population;
    }

    private static double[] parserClass(String s) {
        double[] ans = new double[2];
        switch (s) {
            case "warrior":
                ans[0] = 0.6;
                ans[1] = 0.4;
                break;
            case "archer":
                ans[0] = 0.9;
                ans[1] = 0.1;
                break;
            case "defender":
                ans[0] = 0.1;
                ans[1] = 0.9;
                break;
            case "assassin":
                ans[0] = 0.7;
                ans[1] = 0.3;
                break;
            default:
                throw new IllegalArgumentException("Wrong class type");
        }
        return ans;
    }

    private static MutationMethod<CharacterChromosome> parserMutationMethod(JSONConfigurationParser.JSONMutation mutation) {
        MutationMethod<CharacterChromosome> ans;
        if (mutation.isMultiGen && mutation.isUniform){
            ans = new MutationMethodUniformMultGen(mutation.initProb);
        }
        else if (mutation.isUniform) {
            ans = new MutationMethodUniformOneGen(mutation.initProb);
        }
        else if (mutation.isMultiGen) {
            ans = new MutationMethodNoUniformMultGen(mutation.initProb);
        }
        else {
            ans = new MutationMethodNoUniformOneGen(mutation.initProb);
        }
        return ans;
    }

    private static CrossoverMethod<CharacterChromosome> parserCrossoverMethod(String s, double probability) {
        CrossoverMethod<CharacterChromosome> ans = new SinglePointCrossover(probability);
        switch (s) {
            case "onePoint":
                ans = new SinglePointCrossover(probability);
                break;
            case "twoPoint":
                ans = new DoublePointCrossover(probability);
                break;
            case "uniform":
                ans = new UniformCrossover(probability);
                break;
            case "annular":
                ans = new AnnularCrossover(probability);
                break;
        }
        return ans;
    }

    private static ReplaceMethod parseReplaceMethod(String s) {
        ReplaceMethod ans;
        switch (s) {
            case "first":
                ans  = FIRST;
                break;
            case "second":
                ans = SECOND;
                break;
            case "third":
                ans = THIRD;
                break;
            default:
                throw new IllegalArgumentException("Replace method must be \"first\", \"second\" " +
                        "or \"third\" strings");
        }
        return ans;
    }

    private static List<List<SelectionMethod<CharacterChromosome>>> parserSelectionsMethods(
            JSONConfigurationParser.JSONSelectionMethod[] s1, JSONConfigurationParser.JSONSelectionMethod[] s2,
            boolean isThirdReplacementMethod, int maxGenerationNumber) {
        List<List<SelectionMethod<CharacterChromosome>>> ans = new ArrayList<>();
        boolean isSecondSelectionMethod = false;

        int i = 0;
        ArrayList<SelectionMethod<CharacterChromosome>> selectionMethods = new ArrayList<>();
        JSONConfigurationParser.JSONSelectionMethod[] selectionMethodsJson = s1;
        while (i < 2) {
            i++;
            for (JSONConfigurationParser.JSONSelectionMethod selectionString : selectionMethodsJson) {
                if (i == 2)
                    isSecondSelectionMethod = true;
                SelectionMethod<CharacterChromosome> selectionMethod = parserSelectionsMethod(selectionString.selectionType, isSecondSelectionMethod,
                        isThirdReplacementMethod, maxGenerationNumber);
                selectionMethod.setProportion(selectionString.proportion);
                selectionMethods.add(selectionMethod);
            }
            ans.add(selectionMethods);
            selectionMethodsJson = s2;
            selectionMethods = new ArrayList<>();
        }
        return ans;
    }

    private static SelectionMethod<CharacterChromosome> parserSelectionsMethod(String s, boolean isSecondSelectionMethod,
                                                          boolean isThirdReplacementMethod, int maxGenerationNumber) {
        SelectionMethod<CharacterChromosome> selectionMethod;
        switch (s) {
            case "elite":
                selectionMethod = new EliteSelection<>();
                break;
            case "roulette":
                selectionMethod = new RouletteSelection<>();
                break;
            case "universal":
                selectionMethod = new UniversalSelection<>();
                break;
            case "boltzmann":
                if (isThirdReplacementMethod && isSecondSelectionMethod)
                    selectionMethod = new BoltzmannSelection<>(true, maxGenerationNumber);
                else
                    selectionMethod = new BoltzmannSelection<>(false, maxGenerationNumber);
                break;
            case "tournament":
                selectionMethod = new DeterministicTournamentSelection<>();
                break;
            case "tournamentProb":
                selectionMethod = new ProbabilisticTournamentSelection<>();
                break;
            case "ranking":
                selectionMethod = new RankSelection<>();
                break;
            default:
                throw new IllegalArgumentException("Wrong selection method");
        }
        return selectionMethod;
    }

    public int getNumberOfGenerationsToMakeChecks() {
        return numberOfGenerationsToMakeChecks;
    }

    public PriorityQueue<CharacterChromosome> getPopulation() {
        return population;
    }

    public int getMaxGenerationNumber() {
        return maxGenerationNumber;
    }

    public int getGoalFitness() {
        return goalFitness;
    }

    public ReplaceMethod getReplaceMethod() {
        return replaceMethod;
    }

    public List<List<SelectionMethod<CharacterChromosome>>> getSelectionMethods() {
        return selectionMethods;
    }

    public int getQuantityOfFathersToSelect() {
        return quantityOfFathersToSelect;
    }

    /*
     * A class just to make JSON parsing easier
     */
    public static class JSONConfigurationParser {
        public static class JSONConfiguration {
            String classType;
            JSONMultiplier multipliers;
            int populationQuantity;
            JSONCutCriteria cutCriteria;
            String crossover;
            double crossProbability;
            JSONMutation mutation;
            JSONReplaceMethod replaceMethod;
            JSONSelectionMethod[] firstSelectionMethod;
            JSONSelectionMethod[] secondSelectionMethod;
            Long randomSeed;
        }

        public static class JSONMultiplier {
            double strength;
            double agility;
            double expertise;
            double resistance;
            double life;
        }

        public static class JSONCutCriteria {
            int maxGeneration;
            int quantityOfGenerationToPerformChecks;
            int fitness;
        }

        public static class JSONMutation {
            boolean isMultiGen;
            boolean isUniform;
            double initProb;
        }

        public static class JSONReplaceMethod {
            String type;
            int k;
        }

        public static  class JSONSelectionMethod {
            String selectionType;
            double proportion;
        }
    }
}
