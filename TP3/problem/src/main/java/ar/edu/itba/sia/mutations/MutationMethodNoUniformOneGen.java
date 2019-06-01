package ar.edu.itba.sia.mutations;

public class MutationMethodNoUniformOneGen extends MutationMethodUniformOneGen{
    int x;
    double initProbability;

    public MutationMethodNoUniformOneGen(double probability) {
        super(probability);
        initProbability = probability;
        x = 1;
    }

    @Override
    public void update() {//TODO: check
        probability = initProbability * (1 - 2 * ((1/( 1 + Math.pow(Math.E,(-1*x)))) - 0.5));
    }
}
