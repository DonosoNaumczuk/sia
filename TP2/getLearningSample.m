function learningSample = getLearningSample(data, configPercentage)
    dataColumns    = size(data, 2);
    learningSample = [];

    sortedMatrix      = sortrows(data, 3);
    sampleFromColumnI = getSampleFromColumn(sortedMatrix, configPercentage);
    learningSample    = sampleFromColumnI;

    learningSample = unique(learningSample, 'rows');
endfunction

function sampleFromColumnI = getSampleFromColumn(matrix, configPercentage)
    matrixRows                  = size(matrix, 1);
    learningSamplePercentage    = configPercentage;
    topLimit                    = (int32)(learningSamplePercentage * matrixRows);
    bottomLimit                 = (int32)((1 - learningSamplePercentage) * matrixRows);

    topValues    = getTopRows(matrix, topLimit);
    midValues    = getMidRows(matrix, matrixRows, learningSamplePercentage);
    bottomValues = getBottomRows(matrix, bottomLimit);

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
