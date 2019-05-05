function learnNeuralNetwork()
    global counter; global learningRate; global maxError; global epoch; global isBatch;
    global beta; global gamma; global g; global gD; global betaLast; global gammaLast;
    global gLast; global gDLast; global dataLearning; global dataTest; global N;
    global NF; global weights; global weightsStart; global error; global lastError;
	global lastLastError; global lastWeights; global times; global adaptiveLearningRate;
	global lastDeltaWeights;

    initPlot();
    k 			 	 = size(N)(1);
    deltaWeights 	 = cell(k - 1, 1);
	lastDeltaWeights = cell(k - 1, 1);
    weights      	 = initWeights(N);
    weightsStart 	 = weights;
	lastError 	 	 = error;
	lastLastError	 = error;
	times		 	 = 0;
	lastWeights	 	 = weights;

    dataLearningNormalize = feval(NF, dataLearning);
    p = 0;
    while (p < epoch && max(error) > maxError)
        p++;

        order = 1:size(dataLearning);
        if (!isBatch)
            order = randperm(numel(order));
        endif

        h 							= cell(k - 1, 1);
        v 							= cell(k, 1);
        d 							= cell(k - 1, 1);
		incrementalLRDecremented 	= false;

        for i = order
			do
	           expectedValue = dataLearningNormalize(i, N(1) + 1)';
	           v{1} = [-1; dataLearningNormalize(i, 1:N(1))'];

	           [h, v] = calculateHAndV(k, h, v, weights, g, gLast);

	           d = calculateD(k, h, v, d, weights, gD, gDLast, expectedValue, N);

	           if (isBatch)
	              deltaWeights = updateDeltaWeightsBatch(deltaWeights, learningRate, d, v, k, i);
	           else
	              weights     = updateWeightsIncremental(weights, learningRate, d, v, k);
	              calculateErrors();
				  if (adaptiveLearningRate)
					  incrementalLRDecremented = updateLearningRateIncremental(i);
				  endif
	              if (max(error) <= maxError)
	                  break;
	              endif
	           endif
		   until (!incrementalLRDecremented)
		   if (!isBatch)
			   print();
		   endif
        endfor
        if (isBatch)
           weights = updateWeightsBatch(weights, deltaWeights, k);
           calculateErrors();
		   print();
		   # updateLearningRateBatch();
        endif
    endwhile
    hold off;
endfunction

function learningRateDecremented = updateLearningRateIncremental(index)
	global times;
	global timesLR;
	global incLR;
	global decLR;
	global error;
	global lastError;
	global learningRate;
	global weights;
	global lastWeights;
	global lastLastError;

	learningRateDecremented = false;

	if (error(index) == inf || lastError(index) == inf)
		return;
	endif

	deltaError = error(index) - lastError(index);

	if (deltaError < 0) # error decremented
		times++;
		if (times == timesLR)
			learningRate	= learningRate + incLR;
			times 		  	= 0;
		endif
	elseif (deltaError > 0)
		times		= 0;
		weights 	= lastWeights;
		error 		= lastError;
		lastError 	= lastLastError;

		learningRate = learningRate - learningRate * decLR;

		learningRateDecremented = true;
	else # error did not change
		times = 0;
	endif

endfunction

function updateLearningRateBatch()
endfunction

function weights = initWeights(N)
	global deltaWeights;
	global lastDeltaWeights;

	k 			= size(N)(1);
    weights     = cell(k - 1, 1);
    for i = 1:k - 1
        weights{i} 			= normrnd(0, 1 / sqrt(N(i) + 1), N(i + 1), N(i) + 1);
		deltaWeights{i}		= zeros(N(i + 1), N(i) + 1);
		lastDeltaWeights{i} = zeros(N(i + 1), N(i) + 1);
    endfor
endfunction

function d = calculateD(k, h, v, d, weights, gD, gDLast, expectedValue, N)
    d{k - 1} = feval(gDLast, h{k - 1}) * (expectedValue - v{k}(2:N(k) + 1));
    for o = 2:k - 1
      d{k - o} = feval(gD, h{k - o}) .* (weights{k - o + 1}(1:N(k - o + 2), 2:N(k - o + 1) + 1)' * d{k - o + 1});
    endfor
endfunction

function initPlot()
    clf;
    hold on;
    xlabel("time")
    ylabel("cuadratic error");
endfunction

function calculateErrors()
    global error;
	global lastError;
	global lastLastError;
    global dataLearning;
    global dataTest;
    global meanErrorEvolutionLearning;
    global maxErrorEvolutionLearning;
    global meanErrorEvolutionTest;
    global maxErrorEvolutionTest;

	lastLastError			= lastError;
	lastError				= error;
    error 					= testNeuralnetwork(dataTest);
    meanErrorEvolutionTest 	= [meanErrorEvolutionTest, mean(error)];
    maxErrorEvolutionTest  	= [maxErrorEvolutionTest, max(error)];

    error 						= testNeuralnetwork(dataLearning);
    meanErrorEvolutionLearning 	= [meanErrorEvolutionLearning, mean(error)];
    maxErrorEvolutionLearning  	= [maxErrorEvolutionLearning, max(error)];
endfunction

function print()
	global meanErrorEvolutionLearning;
	global maxErrorEvolutionLearning;
	global meanErrorEvolutionTest;
	global maxErrorEvolutionTest;
	global learningRate;
	global counter;
	counter++;

	title("Learing Rate");
	figure(1);
	plot(counter, learningRate, "color", 'r');
	figure(2);
	title("Test Error");
	hold on;
	plot(counter, meanErrorEvolutionTest(counter + 1), "color", 'k');
    plot(counter, maxErrorEvolutionTest(counter + 1), "color", 'r');
	figure(3);
	title("Learning Error");
	hold on;
	plot(counter, meanErrorEvolutionLearning(counter + 1), "color", 'k');
    plot(counter, maxErrorEvolutionLearning(counter + 1), "color", 'r');
endfunction

function weights = updateWeightsIncremental(weights, learningRate, d, v, k)
	global lastWeights;
	global lastDeltaWeights;
	global momentum;
	global momentumRate;
	lastWeights = weights;

    for o = 1:k - 1
		if (momentum)
			weights{o} 			= weights{o} + learningRate * d{o} * v{o}' + momentumRate .* lastDeltaWeights{o};
			lastDeltaWeights{o} = learningRate * d{o} * v{o}';
		else
			weights{o} = weights{o} + learningRate * d{o} * v{o}';
		endif
    endfor
endfunction

function deltaWeights = updateDeltaWeightsBatch(deltaWeights, learningRate, d, v, k, i)
	global lastDeltaWeights;

	lastDeltaWeights = deltaWeights;

    for o = 1:k - 1
        if (i == 1)
            deltaWeights{o} = learningRate * d{o} * v{o}';
        else
            deltaWeights{o} = deltaWeights{o} + learningRate * d{o} * v{o}';
        endif
    endfor
endfunction

function weights = updateWeightsBatch(weights, deltaWeights, k)
	global lastWeights;
	global lastDeltaWeights;
	global momentum;
	global momentumRate;
	lastWeights = weights;

    for o = 1:k - 1
		if (momentum)
			weights{o} = weights{o} + deltaWeights{o} + momentumRate .* lastDeltaWeights{o};
		else
        	weights{o} = weights{o} + deltaWeights{o};
		endif
    endfor
endfunction
