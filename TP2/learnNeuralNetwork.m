function learnNeuralNetwork()
    global counter; global learningRate; global maxError; global epoch; global isBatch;
    global beta; global gamma; global g; global gD; global betaLast; global gammaLast;
    global gLast; global gDLast; global dataLearning; global dataTest; global N;
    global NF; global weigths; global weigthsStart; global error;

    initPlot();
    k 			     = size(N)(1);
    deltaWeight  = cell(k - 1, 1);
    weigths      = initWeights(N);
    weigthsStart = weigths;

    dataLearningNormalize = feval(NF, dataLearning);
    p = 0;
    while (p < epoch && max(error) > maxError)
        p++;

        order = 1:size(dataLearning);
        if (!isBatch)
            order = randperm(numel(order));
        endif

        h = cell(k - 1, 1);
        v = cell(k, 1);
        d = cell(k - 1, 1);
        for i = order
           expectedValue = dataLearningNormalize(i, N(1) + 1)';
           v{1} = [-1; dataLearningNormalize(i, 1:N(1))'];

           [h, v] = calculateHAndV(k, h, v, weigths, g, gLast);

           d = calculateD(k, h, v, d, weigths, gD, gDLast, expectedValue, N);

           if (isBatch)
              deltaWeight = updateDeltaWeigthsBatch(deltaWeight, learningRate, d, v, k, i);
           else
              weigths     = updateWeightsIncremental(weigths, learningRate, d, v, k);
              print();
              if (max(error) <= maxError)
                  break;
              endif
           endif
        endfor
        if (isBatch)
           weigths = updateWeightsBatch(weigths, deltaWeight, k);
           print();
        endif
    endwhile
    hold off;
endfunction

function weigths = initWeights(N)
    k 			    = size(N)(1);
    weigths     = cell(k - 1, 1);
    for i = 1:k - 1
        weigths{i} = normrnd(0, 1 / sqrt(N(i) + 1), N(i + 1), N(i) + 1);
    endfor
endfunction

function d = calculateD(k, h, v, d, weigths, gD, gDLast, expectedValue, N)
    d{k - 1} = feval(gDLast, h{k - 1}) * (expectedValue - v{k}(2:N(k) + 1));
    for o = 2:k - 1
      d{k - o} = feval(gD, h{k - o}) .* (weigths{k - o + 1}(1:N(k - o + 2), 2:N(k - o + 1) + 1)' * d{k - o + 1});
    endfor
endfunction

function initPlot()
    clf;
    hold on;
    xlabel("time")
    ylabel("cuadratic error");
endfunction

function print()
    global counter;
    global error;
    global dataLearning;
    global dataTest;
    global meanErrorEvolutionLearning;
    global maxErrorEvolutionLearning;
    global meanErrorEvolutionTest;
    global maxErrorEvolutionTest;
    counter++;

    error = testNeuralnetwork(dataTest);
    meanErrorEvolutionTest = [meanErrorEvolutionTest, mean(error)];
    maxErrorEvolutionTest  = [maxErrorEvolutionTest, max(error)];
    plot(counter, meanErrorEvolutionTest(counter + 1), "color", 'g');
    plot(counter, maxErrorEvolutionTest(counter + 1), "color", 'b');

    error = testNeuralnetwork(dataLearning);
    meanErrorEvolutionLearning = [meanErrorEvolutionLearning, mean(error)];
    maxErrorEvolutionLearning  = [maxErrorEvolutionLearning, max(error)];
    plot(counter, meanErrorEvolutionLearning(counter + 1), "color", 'k');
    plot(counter, maxErrorEvolutionLearning(counter + 1), "color", 'r');
endfunction

function weigths = updateWeightsIncremental(weigths, learningRate, d, v, k)
    for o = 1:k - 1
        weigths{o} = weigths{o} + learningRate * d{o} * v{o}';
    endfor
endfunction

function deltaWeight = updateDeltaWeigthsBatch(deltaWeight, learningRate, d, v, k, i)
    for o = 1:k - 1
        if (i == 1)
            deltaWeight{o} = learningRate * d{o} * v{o}';
        else
            deltaWeight{o} = deltaWeight{o} + learningRate * d{o} * v{o}';
        endif
    endfor
endfunction

function weigths = updateWeightsBatch(weigths, deltaWeight, k)
    for o = 1:k - 1
        weigths{o} = weigths{o} + deltaWeight{o};
    endfor
endfunction
