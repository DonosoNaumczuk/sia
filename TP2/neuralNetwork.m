global counter;			           	# Index for graphics, independendent variable
global learningRate;	           	# Learning Rate
global adaptiveLearningRate;	   	# 1 for enabled, 0 for disabled
global timesLR;					   	# learningRate will be incremented if error is decremented timesLR times consecutively
global incLR;					   	# learningRate increment constant (+ incLR) for adaptiveLearningRate
global decLR;					   	# learningRate decrement rate (- learningRate * decLR) for adaptiveLearningRate
global times;						# numbers of times that error is decremented consecutively, for adaptive learning rate
global momentum;					# 1 for enabled, 0 for disabled
global momentumRate;				#
global momentumRateBackUp;			#
global maxError;		          	# Epsilon, target error to reach. All errors have to be below this value
global epoch;			          	# Epoch quantity
global isBatch;			          	# 1 for batch, 0 for incremental
global beta;			          	#
global gamma;			          	#
global g;				          	# Activation function for hidden layers
global gD;				          	# Activation function Derivative for hidden layers
global betaLast;		          	#
global gammaLast;		          	#
global gLast;			          	# Activation function for last layer
global gDLast;			          	# Activation function Derivative for last layer
global learningSample;	          	# Matrix with learning data for training the neural network
global testingSample;		        # Matrix with test data for calculating neural network output errors
global N;				          	# Array with quantity of neurons per layer
global NF;				          	# Normalizer function to avoid saturation
global weights;			          	# Array with weights
global lastWeights;					# Backup of previous weights
global lastDeltaWeights;			# Previous deltaWeights for momentum
global weightsStart;	          	# Array with initial weights, in case we need to reproduce the last execution
global error;			          	# Array with cuadratic error of learning data
global lastError;					# To calculate change in error
global meanErrorEvolutionLearning;	# Array containing evolution of the mean of the error of the learning data
global maxErrorEvolutionLearning; 	# Array containing evolution of the max of the error of the learning data
global meanErrorEvolutionTest;    	# Array containing evolution of the mean of the error of the test data
global maxErrorEvolutionTest;     	# Array containing evolution of the max of the error of the test data
global learningRateEvolution;
global debugTimes;					# Set breakpoint enabled when mod(counter, debugTimes) equals zero

initConfiguration();

dbstop in learnNeuralNetwork at 24 if (isBatch && mod(counter, debugTimes) == 0);
dbstop in learnNeuralNetwork at 39 if (!isBatch && mod(counter, debugTimes) == 0);

x  = testingSample(1:size(testingSample),1);
y  = testingSample(1:size(testingSample),2);
z1 = testingSample(1:size(testingSample),3);

figure(4, 'position', [0,400,450,400]);
title("Surface real");
clf;
hold on;
view([-37.5,30]);
scatter3(x,y,z1, 'r',"filled");
legend("real");
hold off;

learnNeuralNetwork();

z2 = evalNeuralnetwork(testingSample);
figure(5, 'position', [450,400,450,400]);
title("Surface aproximation");
clf;
hold on;
view([-37.5,30]);
scatter3(x,y,z2, 'b',"filled");
legend("aproximation");
hold off;

figure(6, 'position', [900,400,450,400]);
title("Surfaces");
clf;
hold on;
view([-37.5,30]);
scatter3(x,y,z1, 'r',"filled");
scatter3(x,y,z2, 'b',"filled");
legend("real","aproximation");
hold off;

error = testNeuralnetwork(testingSample);
