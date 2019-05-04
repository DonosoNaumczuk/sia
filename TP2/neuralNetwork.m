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
global meanErrorEvolutionLearning;
global maxErrorEvolutionLearning;
global meanErrorEvolutionTest;
global maxErrorEvolutionTest;

initConfiguration();
learnNeuralNetwork();
error = testNeuralnetwork(dataTest)

x  = dataTest(1:size(dataTest),1);
y  = dataTest(1:size(dataTest),2);
z1 = dataTest(1:size(dataTest),3);
z2 = evalNeuralnetwork(dataTest);
clf;
hold on;
scatter3(x,y,z1, 'r',"filled");
scatter3(x,y,z2, 'b',"filled");
hold off;

