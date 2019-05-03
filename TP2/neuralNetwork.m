global counter;			# Index for graphics, independendent variable
global learningFactor;	# Learning factor
global maxError;		# Epsilon, target error to reach. All errors have to be below this value
global epoch;			# Epoch quantity
global isBatch;			# 1 for batch, 0 for incremental
global beta;			#
global gamma;			#
global g;				# Activation function for hidden layers
global gD;				# Activation function Derivative for hidden layers
global betaLast;		#
global gammaLast;		#
global gLast;			# Activation function for last layer
global gDLast;			# Activation function Derivative for last layer
global dataLearning;	# Matrix with learning data for training the neural network
global dataTest;		# Matrix with test data for calculating neural network output errors
global N;				# Array with quantity of neurons per layer
global NF;				# Normalizer function to avoid saturation
global weigths;			# Array with weights
global weigthsStart;	# Array with initial weights, in case we need to reproduce the last execution
global error;			# TODO: we must have testErrors and learningErrors

initConfiguration();
learnNeuralNetwork();
error = testNeuralnetwork()
