function learningSample = getLearningSample(data)
    dataColumns    = size(data, 2);
    learningSample = [];

    #for i=1:dataColumns
        sortedMatrix      = sortrows(data, 3);
        sampleFromColumnI = getSampleFromColumn(sortedMatrix);
        learningSample    = sampleFromColumnI;
    #end

    learningSample = unique(learningSample, 'rows');
endfunction

function sampleFromColumnI = getSampleFromColumn(matrix)
    matrixRows               = size(matrix, 1);
    learningSamplePercentage = 0.2;
    limit                    = (int32)(learningSamplePercentage * matrixRows);

    topValues    = getTopRows(matrix, matrixRows);
    midValues    = getMidRows(matrix, matrixRows, learningSamplePercentage);
    bottomValues = getBottomRows(matrix, matrixRows);

    sampleFromColumnI = joinSamples(topValues, midValues, bottomValues);
endfunction

function topValues = getTopRows(matrix, limit)
    topValues = matrix([1:limit],:);
endfunction

function midValues = getMidRows(matrix, rows, learningSamplePercentage)
    limit      = learningSamplePercentage * rows;
	lowerLimit = (int32)(rows/2 - limit/2);
	upperLimit = (int32)(rows/2 + limit/2);
    midValues  = matrix([lowerLimit:upperLimit],:);
endfunction

function bottomValues = getBottomRows(matrix, limit)
    bottomValues = matrix([limit:end],:);
endfunction

function sampleFromColumnI = joinSamples(topValues, midValues, bottomValues)
    sampleFromColumnI = [topValues; midValues];
    sampleFromColumnI = [sampleFromColumnI; bottomValues];
endfunction
