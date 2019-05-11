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
    while (p < epoch && mean(error) > maxError)
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
					  incrementalLRDecremented = updateLearningRateIncremental();
				  endif
	              if (mean(error) <= maxError)
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
    endPlot();
endfunction

function learningRateDecremented = updateLearningRateIncremental()
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
  global momentumRate;
  global momentumRateBackUp;

  momentumRate = momentumRateBackUp; #reset momentum rate
	learningRateDecremented = false;

  times++;
	if (mean(error) == inf || mean(lastError) == inf)
		return;
	endif

	deltaError = mean(error) - mean(lastError);

  if (times == timesLR)
    if (deltaError < 0) # error decremented
        learningRate	= learningRate + incLR;        
    elseif (deltaError > 0)
      momentumRate = 0;
      weights 	= lastWeights;
      error 		= lastError;
      lastError 	= lastLastError;

      learningRate = learningRate - learningRate * decLR;

      learningRateDecremented = true;
    endif    
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
	  figure(1, 'position', [900,0,450,400]);
    clf;
    title("Learing Rate");
    xlabel("time")
    ylabel("Learning Rate");
    hold on;
    figure(2, 'position', [0,0,450,400]);
    clf;
	  title("Test Error");
    xlabel("time")
    ylabel("Cuadratic error mean");
    hold on;
    figure(3, 'position', [450,0,450,400]);
    clf;
	  title("Learning Error");
    hold on;
    xlabel("time")
    ylabel("Cuadratic error mean");
endfunction

function endPlot()
	  figure(1);
    hold off;
    figure(2);
	  hold off;
    figure(3);
	  hold off;
endfunction

function calculateErrors()
    global times;
    global timesLR;
    global error;
	  global lastError;
	  global lastLastError;
    global dataLearning;
    global dataTest;
    global meanErrorEvolutionLearning;
    global maxErrorEvolutionLearning;
    global meanErrorEvolutionTest;
    global maxErrorEvolutionTest;

    if (times == timesLR)
      lastLastError		= lastError;
      lastError				= error;
      times = 0;
    endif
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
  global learningRateEvolution;
	global learningRate;
	global counter;
	counter++;

	figure(1);
  if(learningRateEvolution == 0)
     learningRateEvolution = [learningRate];
  else
     learningRateEvolution = [learningRateEvolution, learningRate];
  endif
	plot(counter, learningRate, '*r');
  line(1:size(learningRateEvolution)(2), learningRateEvolution);
  #axis ("tight");
	figure(2);
  
	plot(counter, meanErrorEvolutionTest(counter + 1), "color", 'k');
   # plot(counter, maxErrorEvolutionTest(counter + 1), "color", 'r');
	figure(3);
	plot(counter, meanErrorEvolutionLearning(counter + 1), "color", 'k');
   # plot(counter, maxErrorEvolutionLearning(counter + 1), "color", 'r');
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
