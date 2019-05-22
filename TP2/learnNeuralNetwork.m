function learnNeuralNetwork()
    global counter; global learningRate; global maxError; global epoch; global isBatch;
    global beta; global gamma; global g; global gD; global betaLast; global gammaLast;
    global gLast; global gDLast; global learningSample; global testingSample; global N;
    global NF; global weights; global weightsStart; global error; global lastError;
	global lastLastError; global lastWeights; global times; global adaptiveLearningRate;
	global lastDeltaWeights; global debugTimes; global timesLR; global meanErrorEvolutionLearning;
    global meanErrorEvolutionTest; global learningRateEvolution; global nextLearningRate;

    initPlot();
    k 			 	 = size(N)(1);
    deltaWeights 	 = cell(k - 1, 1);
	lastDeltaWeights = cell(k - 1, 1);
    weights      	 = initWeights(N);
    weightsStart 	 = weights;
    calculateErrors();
	lastError 	 	 = error;
	lastLastError	 = error;
    learningRateEvolution = [learningRate];
	times		 	 = 1;
	lastWeights	 	 = weights;
    nextLearningRate = learningRate;
    orderBefore      = 1:size(learningSample);
    orderBefore      = randperm(numel(orderBefore));
    incrementalLRDecremented 	= false;
    batchLRDecremented          = false;

    learningSampleNormalize = feval(NF, learningSample(1:size(learningSample),1:N(1)));
    p = 0;
    while (p < epoch && mean(error) > maxError)
        p++;

        order = 1:size(learningSample);
        if (!isBatch)
            if(incrementalLRDecremented)
                order = orderBefore;
            else
                orderBefore = order;
                order = randperm(numel(order));
            endif
        endif

        h 							= cell(k - 1, 1);
        v 							= cell(k, 1);
        d 							= cell(k - 1, 1);
    	incrementalLRDecremented 	= false;
        batchLRDecremented          = false;

        index = 0;
        while(index < size(order)(2))
            index++;
            expectedValue = learningSample(order(index), N(1)+1:size(learningSample)(2))';
            v{1} = [-1; learningSampleNormalize(order(index), 1:N(1))'];

            [h, v] = calculateHAndV(k, h, v, weights, g, gLast);

            d = calculateD(k, h, v, d, weights, gD, gDLast, expectedValue, N);

            if (isBatch)
              deltaWeights = updateDeltaWeightsBatch(deltaWeights, learningRate, d, v, k, order(index));
            else
              weights     = updateWeightsIncremental(weights, learningRate, d, v, k);
              if (adaptiveLearningRate)
                  incrementalLRDecremented = updateLearningRate();
                  if(incrementalLRDecremented)
                        index = index - timesLR;
                        if(index < 0)
                            index = size(order)(2) + index;
                            meanErrorEvolutionTest(end)     = [];
                            meanErrorEvolutionLearning(end) = [];
                            learningRateEvolution(end)      = [];
                            p--;
                            order = orderBefore;
                        endif
                  endif
              endif
              if (mean(error) <= maxError)
                  break;
              endif
              if(index < size(order)(2))
                  learningRate = nextLearningRate;
              endif
            endif
        endwhile
        if (isBatch)
           weights = updateWeightsBatch(weights, deltaWeights, k);
           if (adaptiveLearningRate)
               batchLRDecremented = updateLearningRate();
           endif
        endif
        if (!batchLRDecremented)
            calculateErrors();
            print(true);
        else
            p = p - timesLR;
            meanErrorEvolutionTest     = meanErrorEvolutionTest(:,1:end - (timesLR-1));
            meanErrorEvolutionLearning = meanErrorEvolutionLearning(:,1:end - (timesLR-1));
            learningRateEvolution      = learningRateEvolution(:,1:end - (timesLR-1));
        endif
        learningRate = nextLearningRate;
    endwhile
    print(false);
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
    global nextLearningRate;
    global weights;
    global lastWeights;
    global lastLastError;
    global momentumRate;
    global momentumRateBackUp;
    global learningSample;

    if (times == timesLR)
      lastLastError = lastError;
      lastError		= error;
      times = 0;
      error = testNeuralnetwork(learningSample);
    endif

    learningRateDecremented = false;

    times++;
    if (mean(error) == inf || mean(lastError) == inf)
        return;
    endif

    if (times == timesLR)
        deltaError = mean(error) - mean(lastError);
        if (deltaError < 0) # error decremented
            nextLearningRate = learningRate + incLR;
            momentumRate = momentumRateBackUp; #reset momentum rate
        elseif (deltaError > 0)
            momentumRate = 0;
            weights 	 = lastWeights;
            error     = lastError;
            lastError = lastLastError;

            nextLearningRate = learningRate - learningRate * decLR;

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
    global learningRateEvolution;
    global learningRate;


    learningRateEvolution = [learningRateEvolution, learningRate];


    error2 					= testNeuralnetwork(testingSample);
    meanErrorEvolutionTest 	= [meanErrorEvolutionTest, mean(error2)];
    maxErrorEvolutionTest  	= [maxErrorEvolutionTest, max(error2)];

    error2 						= testNeuralnetwork(learningSample);
    meanErrorEvolutionLearning 	= [meanErrorEvolutionLearning, mean(error2)];
    maxErrorEvolutionLearning  	= [maxErrorEvolutionLearning, max(error2)];
endfunction

function print(flag)
	global meanErrorEvolutionLearning;
	global maxErrorEvolutionLearning;
	global meanErrorEvolutionTest;
	global maxErrorEvolutionTest;
  global learningRateEvolution;
	global learningRate;
    global isBatch;
    global timesLR;

    delay = 0;
    if(flag)
        if(isBatch)
            delay = timesLR;
        else
            delay = 1;
        endif
    endif
  figure(1);
	plot(0:size(learningRateEvolution)(2)-delay-1, learningRateEvolution(:,1:size(learningRateEvolution)(2)-delay))
  title("Learing Rate");                # 'hold on'
  xlabel("time")                        # destroy
  ylabel("Learning Rate");              # the
  figure(2, 'position', [0,0,450,400]); # graph

	figure(2);
	plot(0:size(meanErrorEvolutionTest)(2)-2-delay, meanErrorEvolutionTest(1,2:size(meanErrorEvolutionTest)(2)-delay), '-k');
	figure(3);
	plot(0:size(meanErrorEvolutionLearning)(2)-2-delay, meanErrorEvolutionLearning(1,2:size(meanErrorEvolutionLearning)(2)-delay), '-k');
endfunction

function weights = updateWeightsIncremental(weights, learningRate, d, v, k)
	global lastWeights;
	global lastDeltaWeights;
	global momentum;
	global momentumRate;
    global times;
    global timesLR;

    if(times == timesLR)
       lastWeights = weights;
    endif

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
    global times;
    global timesLR;

    if(times == timesLR)
	   lastWeights = weights;
    endif

    for o = 1:k - 1
		if (momentum)
			weights{o} = weights{o} + deltaWeights{o} + momentumRate .* lastDeltaWeights{o};
		else
        	weights{o} = weights{o} + deltaWeights{o};
		endif
    endfor
endfunction
