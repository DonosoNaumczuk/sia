global counter;			           # Index for graphics, independendent variable
global learningFactor;	           # Learning factor
global maxError;		           # Epsilon, target error to reach. All errors have to be below this value
global epoch;			           # Epoch quantity
global isBatch;			           # 1 for batch, 0 for incremental
global beta;			           #
global gamma;			           #
global g;				           # Activation function for hidden layers
global gD;				           # Activation function Derivative for hidden layers
global betaLast;		           #
global gammaLast;		           #
global gLast;			           # Activation function for last layer
global gDLast;			           # Activation function Derivative for last layer
global dataLearning;	           # Matrix with learning data for training the neural network
global dataTest;		           # Matrix with test data for calculating neural network output errors
global N;				           # Array with quantity of neurons per layer
global NF;				           # Normalizer function to avoid saturation
global weigths;			           # Array with weights
global weigthsStart;	           # Array with initial weights, in case we need to reproduce the last execution
global error;			           # TODO: we must have testErrors and learningErrors
global meanErrorEvolutionLearning; # Array containing evolution of the mean of the error of the learning data
global maxErrorEvolutionLearning;  # Array containing evolution of the max of the error of the learning data
global meanErrorEvolutionTest;     # Array containing evolution of the mean of the error of the test data
global maxErrorEvolutionTest;      # Array containing evolution of the max of the error of the test data

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
