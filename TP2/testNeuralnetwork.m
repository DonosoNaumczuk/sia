function retval = testNeuralnetwork()
    global weigths;
    global N;
    global g;
    global gLast;
    global dataTest;
    global NF;
    
    k 		= size(N)(1);
    retval 	= zeros(size(dataTest), 1);
    h = cell(k - 1, 1);
    v = cell(k, 1);
    for i = 1:size(dataTest)
      expectedValue = dataTest(i, N(1) + 1:N(1) + N(k))';
      v{1} = [-1; feval(NF, dataTest(i, 1:N(1)))'];
      [h, v] = calculateHAndV(k, h, v, weigths, g, gLast);
      retval(i) = 0.5 * sum((expectedValue - v{k}(2:N(k) + 1)) .^ 2);
   endfor
endfunction