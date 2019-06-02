package ar.edu.itba.sia.mutations;

public class MutationMethodNoUniformMultGen extends MutationMethodUniformMultGen{
    int x;
    double initProbability;

    public MutationMethodNoUniformMultGen(double probability) {
        super(probability);
        initProbability = probability;
        x = 1;
    }

    @Override
    public void update() {//TODO: check
        probability = initProbability * (1 - 2 * ((1/( 1 + Math.pow(Math.E,(-0.01 * x)))) - 0.5));
        x++;
    }
}
