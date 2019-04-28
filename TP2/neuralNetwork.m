global aux;
function [learningFactor, epoch, beta, g, gD, isBatch, dataLearning, dataTest, N, NF, NFI] = initConfiguration()
    configuration = importdata('configuration.data',' ');
    auxIndex = 0;
    index    = 1;
    learningFactor = configuration.data(index++);
    epoch          = configuration.data(index++);
    auxIndex++;
    if (strcmp(configuration.textdata{auxIndex+index++}, 'batch'))
        isBatch = true;
    else
        isBatch = false;
    endif
    beta           = configuration.data(index++);
    auxIndex++;
    if (strcmp(configuration.textdata{auxIndex+index++}, 'exp'))
        g  = @(x) 1./(1+exp(-2*beta*x));
        gD = @(x) (2*beta).*(feval(g,x).*(1-feval(g,x)));
        a = 0; b = 1;
    else
        g  = @(x) tanh(x);
        gD = @(x) beta.*(1-feval(g,x).^2);
        a = -1; b = 1;
    endif
    auxIndex++;
    dataLearning   = importdata(configuration.textdata{auxIndex+index++}, ' ').data;
    auxIndex++;
    dataTest       = importdata(configuration.textdata{auxIndex+index++}, ' ').data;
    N = configuration.data(index:size(configuration.data));
    A = min(dataTest(1:size(dataTest)(1),N(1)+1));
    B = max(dataTest(1:size(dataTest)(1),N(1)+1));
    NF  = @(x) a + ((x-A).*(b-a)./(B-A));
    NFI = @(y) A + ((y-a).*(B-A)./(b-a));
endfunction

function weigths = learnNeuralNetwork(learningFactor,epoch,beta,g,gD,isBatch,dataLearning,dataTest,N,NF,NFI) 
    clf;
    hold on;
    xlabel("epoch")
    ylabel("cuadratic error");
    colors = {'k'; 'r'; 'g'; 'b'; 'y'; 'm'; 'c'; 'w'};
    k = size(N)(1);
    weigths     = cell(k-1,1);
    deltaWeight = cell(k-1,1);
    for i = 1:k-1 
        weigths{i} = normrnd(0, 1/sqrt(N(i)+1), N(i+1),N(i)+1);
    endfor
    
    dataLearningNormalize = feval(NF, dataLearning);
    for p = 1:epoch
        order = 1:size(dataLearning);
        if (!isBatch)
            order = randperm(numel(order));
        endif 
        
        h = cell(k-1,1);
        v = cell(k,1);
        d = cell(k-1,1);
        for i = order
           expectedValue = dataLearningNormalize(i,N(1)+1)';
           v{1} = [-1;dataLearningNormalize(i,1:N(1))'];
           
           for o = 1:k-1
             h{o} = weigths{o} * v{o};
             v{o+1} = [-1;feval(g,h{o})];
           endfor
           
           d{k-1} = feval(gD,h{k-1})*(expectedValue-v{k}(2:N(k)+1));
           for o = 2:k-1
             d{k-o} = feval(gD,h{k-o}).*(weigths{k-o+1}(1:N(k-o+2),2:N(k-o+1)+1)'*d{k-o+1});
           endfor
           
           for o = 1:k-1 
             if (isBatch)
                if (i == 1)
                    deltaWeight{o} = learningFactor*d{o}*v{o}';
                else
                    deltaWeight{o} = deltaWeight{o}+learningFactor*d{o}*v{o}';
                endif
             else
                weigths{o} = weigths{o}+learningFactor*d{o}*v{o}';
             endif  
           endfor
        endfor
        if (isBatch)
            for o = 1:k-1  
                weigths{o} = weigths{o}+deltaWeight{o};
            endfor 
        endif
        error = testNeuralnetwork(weigths,N,g,dataTest,NF,NFI);
        sizeaux = min([size(error)(1), 8]);
        for r = 1:sizeaux
            plot(p,error(r),"color",colors{r});
        endfor
    endfor
    hold off;
endfunction

function retval = testNeuralnetwork(weigths,N,g,dataTest,NF,NFI)
    k = size(N)(1);
    retval = zeros(size(dataTest),1);
    for i = 1:size(dataTest)
      expectedValue = dataTest(i,N(1)+1:N(1)+N(k))';
      v{1} = [-1;feval(NF, dataTest(i, 1:N(1)))'];
      for o = 1:k-1
        h{o} = weigths{o} * v{o};
        v{o+1} = [-1;feval(g,h{o})];
      endfor
      retval(i) = 0.5*sum((expectedValue - feval(NFI,v{k}(2:N(k)+1))).^2);
   endfor
endfunction
  
[learningFactor,epoch,beta,g,gD,isBatch,dataLearning,dataTest,N,NF,NFI] = initConfiguration();
weigths = learnNeuralNetwork(learningFactor,epoch,beta,g,gD,isBatch,dataLearning,dataTest,N,NF,NFI);
testNeuralnetwork(weigths,N,g,dataTest,NF,NFI)