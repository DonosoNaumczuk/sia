function initConfiguration()
    global learningRateEvolution;
    learningRateEvolution = 0;
    global counter;
    counter = 0;
    global meanErrorEvolutionLearning;
    meanErrorEvolutionLearning = [0];
    global maxErrorEvolutionLearning;
    maxErrorEvolutionLearning  = [0];
    global meanErrorEvolutionTest;
    meanErrorEvolutionTest = [0];
    global maxErrorEvolutionTest;
    maxErrorEvolutionTest  = [0];
    configuration 	= importdata('configuration.data', ' ');
    auxIndex 		= 0;
    index    		= 1;
    global learningRate;
    learningRate = configuration.data(index++);

	global adaptiveLearningRate;
	adaptiveLearningRate = configuration.data(index++);

	global timesLR;
	timesLR = configuration.data(index++);

	global incLR;
	incLR = configuration.data(index++);

	global decLR;
	decLR = configuration.data(index++);

	global momentum;
	momentum = configuration.data(index++);

	global momentumRate;
	momentumRate = configuration.data(index++);
	global momentumRateBackUp;
	momentumRateBackUp = momentumRate;

    global maxError;
    maxError = configuration.data(index++);

    global epoch;
    epoch = configuration.data(index++);

	global debugTimes;
	debugTimes = configuration.data(index++);

    global isBatch;
    auxIndex++;
    if (strcmp(configuration.textdata{auxIndex + index++}, 'batch'))
        isBatch = true;
    else
        isBatch = false;
    endif

    global beta;
    beta  = configuration.data(index++);
    global gamma;
    gamma = configuration.data(index++);

    global g;
    global gD;
    auxIndex++;
    functionString1 = configuration.textdata{auxIndex + index++};
    if (strcmp(functionString1, 'exp'))
        g  	= @(x) 1 ./ (1 + exp(-2 .* beta .* x));
        gD 	= @(x) (2 * beta) .* (feval(g,x) .* (1 - feval(g,x)));
        a 	= -6 * (1 / beta);
		b 	= 6 * (1 / beta);
    elseif (strcmp(functionString1, 'linear'))
        g  	= @(x) beta * x + gamma;
        gD 	= @(x) beta;
    else
        g  	= @(x) tanh(beta .* x);
        gD 	= @(x) beta .* (1 - feval(g,x) .^ 2);
        a 	= -6 * (1 / beta);
		b 	= 6 * (1 / beta);
    endif

    global betaLast;
    betaLast  = configuration.data(index++);
    global gammaLast;
    gammaLast = configuration.data(index++);

    global gLast;
    global gDLast;
    auxIndex++;
    functionString = configuration.textdata{auxIndex + index++};
    if (strcmp(functionString, 'exp'))
        gLast  	= @(x) 1 ./ (1 + exp(-2 .* betaLast .* x));
        gDLast 	= @(x) (2 * betaLast) .* (feval(gLast,x) .* (1 - feval(g,x)));
    elseif (strcmp(functionString, 'linear'))
        gLast  	= @(x) betaLast * x + gammaLast;
        gDLast 	= @(x) betaLast;
    else
        gLast  	= @(x) tanh(beta .* x);
        gDLast 	= @(x) betaLast .* (1 - feval(gLast,x) .^ 2);
    endif

    global learningSample;
    auxIndex++;
    data = importdata(configuration.textdata{auxIndex + index++}, ' ').data;
    learningSample = getLearningSample(data);

    global testingSample
    auxIndex++;
    testingSample 	= importdata(configuration.textdata{auxIndex + index++}, ' ').data;

    global N;
    N 			= configuration.data(index:size(configuration.data));
    A 			= min(testingSample(1:size(testingSample)(1), N(1) + 1));
    B 			= max(testingSample(1:size(testingSample)(1), N(1) + 1));

    global NF;
    if(strcmp(functionString1, 'linear'))
    	NF = @(x) x;
    else
      #NF = @(x) x;
    	NF = @(x) a + ((x - A) .* (b - a) ./ (B - A)); #TODO: check divide by the norm
    endif

	global error;
	error = ones(size(learningSample)(1), 1) .* inf;
endfunction
