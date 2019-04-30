global counter;
global learningFactor;
global maxError;
global epoch;
global isBatch;
global beta;
global gamma;
global g;
global gD;
global betaLast;
global gammaLast;
global gLast;
global gDLast;
global dataLearning;
global dataTest;
global N;
global NF;
global weigths;
global weigthsStart;
global error;

initConfiguration();
learnNeuralNetwork();
error = testNeuralnetwork()
