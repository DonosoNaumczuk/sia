package ar.edu.itba.sia;

import ar.edu.itba.sia.equipment.*;
import ar.edu.itba.sia.interfaces.CrossoverMethod;
import ar.edu.itba.sia.interfaces.MutationMethod;

import java.util.ArrayList;

public class CharacterChromosome extends Chromosome<CharacterChromosome> {
    private double     strength;
    private double     agility;
    private double     expertise;
    private double     resistance;
    private double     life;

    private double     atkMult;
    private double     defMult;

    private double     strengthP;
    private double     agilityP;
    private double     expertiseP;
    private double     resistanceP;
    private double     lifeP;

    /* Strength, Agility, Expertise, Resistance, Life */
    private ArrayList<Double> traits = new ArrayList<>();

    /* Attack Multiplier, Defense Multiplier */
    private ArrayList<Double> multipliers = new ArrayList<>();

    /* Height, Boots, BreastPlate, Gloves, Helmet, Weapon */
    private ArrayList<Object> alleles = new ArrayList<>();

    public CharacterChromosome(CrossoverMethod<CharacterChromosome> crossoverMethod,
                               MutationMethod<CharacterChromosome> mutationMethod, ArrayList<Double> traits,
                               ArrayList<Double> multipliers, ArrayList<Object> alleles) {
        super(crossoverMethod, mutationMethod);

        this.traits.addAll(traits);
        this.multipliers.addAll(multipliers);
        this.alleles.addAll(alleles);

        calculateFitness();
    }

    /*
    public CharacterChromosome(CrossoverMethod<CharacterChromosome> crossoverMethod, MutationMethod<CharacterChromosome> mutationMethod,
                               double strength, double agility, double expertise, double resistance, double life, double atkMult,
                               double defMult, double height, Boots boots, BreastPlate breastPlate, Gloves gloves, Helmet helmet,
                               Weapon weapon) {
        super(crossoverMethod, mutationMethod);

        this.strength   = strength;
        this.agility    = agility;
        this.expertise  = expertise;
        this.resistance = resistance;
        this.life       = life;

        this.atkMult    = atkMult;
        this.defMult    = defMult;

        this.alleles = new ArrayList<>();
        alleles.add(height);
        alleles.add(boots);
        alleles.add(breastPlate);
        alleles.add(gloves);
        alleles.add(helmet);
        alleles.add(weapon);

        calculateFitness();
    }
     */

    /*
    public CharacterChromosome(CharacterChromosome other, ArrayList<Object> alleles) {
        super(other);

        this.strength   = other.strength;
        this.agility    = other.agility;
        this.expertise  = other.expertise;
        this.resistance = other.resistance;
        this.life       = other.life;

        this.atkMult    = other.atkMult;
        this.defMult    = other.defMult;

        this.alleles = alleles;

        calculateFitness();
    }
     */

    @Override
    public CharacterChromosome mutate() {
        return super.getMutationMethod().mutate(this);
    }

    @Override
    public void updateMutation() {
        super.getMutationMethod().update();
    }

    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome chromosome) {
        return super.getCrossoverMethod().crossover(this, chromosome);
    }

    @Override
    public double calculateFitness() {
        initializePValues();
        return atkMult * this.calculateAttack() + defMult * calculateDefense();
    }

    private double calculateAttack() {
        return (agilityP + expertiseP) * strengthP * atkMultFromHeight();
    }

    private double calculateDefense() {
        return (resistanceP + expertiseP) * lifeP * defMultFromHeight();
    }

    private double atkMultFromHeight() {
        double height = (double)alleles.get(0);//TODO:check
        return 0.5 - Math.pow((3 * height - 5), 4) + Math.pow((3 * height - 5), 2) + height / 2;
    }

    private double defMultFromHeight() {
        double height = (double)alleles.get(0);//TODO:check
        return 2 + Math.pow((3 * height - 5), 4) - Math.pow((3 * height - 5), 2) - height / 2;
    }

    private  void initializePValues() {
        this.strengthP      = 0;
        this.agilityP       = 0;
        this.expertiseP     = 0;
        this.resistanceP    = 0;
        this.lifeP          = 0;

        int i = 1;
        Item currentItem;
        while (i != alleles.size()) {
            currentItem = (Item)alleles.get(i);
            this.strengthP      += currentItem.getStrength();
            this.agilityP       += currentItem.getAgility();
            this.expertiseP     += currentItem.getExpertise();
            this.resistanceP    += currentItem.getResilience();
            this.lifeP          += currentItem.getVitality();
            i++;
        }
        this.strengthP      = Math.tanh(0.01 * strength * strengthP);
        this.agilityP       = Math.tanh(0.01 * agility * agilityP);
        this.expertiseP     = Math.tanh(0.01 * expertise * expertiseP);
        this.resistanceP    = Math.tanh(0.01 * resistance* resistanceP);
        this.lifeP          = Math.tanh(0.01 * life * lifeP);
    }

    public ArrayList<Double> getTraits() { return traits; }

    public ArrayList<Double> getMultipliers() { return multipliers; }

    public ArrayList<Object> getAlleles() {
        return alleles;
    }
}
