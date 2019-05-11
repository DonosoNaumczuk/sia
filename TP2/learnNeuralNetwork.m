function learnNeuralNetwork()
    global counter; global learningRate; global maxError; global epoch; global isBatch;
    global beta; global gamma; global g; global gD; global betaLast; global gammaLast;
    global gLast; global gDLast; global learningSample; global testingSample; global N;
    global NF; global weights; global weightsStart; global error; global lastError;
	global lastLastError; global lastWeights; global times; global adaptiveLearningRate;
	global lastDeltaWeights; global debugTimes;

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

    learningSampleNormalize = feval(NF, learningSample);
    p = 0;
    while (p < epoch && mean(error) > maxError)
        do
            p++;

            order = 1:size(learningSample);
            if (!isBatch)
                order = randperm(numel(order));
            endif

            h 							= cell(k - 1, 1);
            v 							= cell(k, 1);
            d 							= cell(k - 1, 1);
    		incrementalLRDecremented 	= false;
            batchLRDecremented          = false;

            for i = order
    			do
    	           expectedValue = learningSampleNormalize(i, N(1) + 1)';
    	           v{1} = [-1; learningSampleNormalize(i, 1:N(1))'];

    	           [h, v] = calculateHAndV(k, h, v, weights, g, gLast);

    	           d = calculateD(k, h, v, d, weights, gD, gDLast, expectedValue, N);

    	           if (isBatch)
    	              deltaWeights = updateDeltaWeightsBatch(deltaWeights, learningRate, d, v, k, i);
    	           else
    	              weights     = updateWeightsIncremental(weights, learningRate, d, v, k);
    	              calculateErrors();
    				  if (adaptiveLearningRate)
    					  incrementalLRDecremented = updateLearningRate();
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
               if (adaptiveLearningRate)
                   batchLRDecremented = updateLearningRate();
               endif
            endif
        until (!batchLRDecremented)
    if (isBatch)
        print();
    endif
    endwhile
    endPlot()
endfunction

function learningRateDecremented = updateLearningRate()
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
            learningRate = learningRate + incLR;
        elseif (deltaError > 0)
            momentumRate = 0;
            weights 	 = lastWeights;
            error 		 = lastError;
            lastError 	 = lastLastError;

            learningRate = learningRate - learningRate * decLR;

            learningRateDecremented = true;
        endif
    endif
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
    #hold on;  #destroy plot
    title("Learing Rate");
    xlabel("time")
    ylabel("Learning Rate");
    figure(2, 'position', [0,0,450,400]);
    clf;
    hold on;
	  title("Test Error");
    xlabel("time")
    ylabel("Cuadratic error mean");
    figure(3, 'position', [450,0,450,400]);
    clf;
    hold on;
	  title("Learning Error");
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
    global learningSample;
    global testingSample;
    global meanErrorEvolutionLearning;
    global maxErrorEvolutionLearning;
    global meanErrorEvolutionTest;
    global maxErrorEvolutionTest;

    if (times == timesLR)
      lastLastError		= lastError;
      lastError				= error;
      times = 0;
    endif
    error 					= testNeuralnetwork(testingSample);
    meanErrorEvolutionTest 	= [meanErrorEvolutionTest, mean(error)];
    maxErrorEvolutionTest  	= [maxErrorEvolutionTest, max(error)];

    error 						= testNeuralnetwork(learningSample);
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

  if(learningRateEvolution == 0)
     learningRateEvolution = [learningRate];
  else
     learningRateEvolution = [learningRateEvolution, learningRate];
  endif

  figure(1);
	plot(1:size(learningRateEvolution)(2), learningRateEvolution)
  title("Learing Rate");                # 'hold on'
  xlabel("time")                        # destroy
  ylabel("Learning Rate");              # the
  figure(2, 'position', [0,0,450,400]); # graph

	figure(2);
	plot(1:size(meanErrorEvolutionTest)(2)-1, meanErrorEvolutionTest(1,2:size(meanErrorEvolutionTest)(2)), '-k');
   # plot(counter, maxErrorEvolutionTest(counter + 1), "color", 'r');
	figure(3);
	plot(1:size(meanErrorEvolutionLearning)(2)-1, meanErrorEvolutionLearning(1,2:size(meanErrorEvolutionLearning)(2)), '-k');
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
