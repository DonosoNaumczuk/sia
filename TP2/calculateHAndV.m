function [h, v] = calculateHAndV(k, h, v, weights, g, gLast)
    for o = 1:k - 2
      h{o} 		= weights{o} * v{o};
      v{o + 1} = [-1; feval(g, h{o})];
    endfor
    h{k - 1} = weights{k - 1} * v{k - 1};
    v{k} 	  = [-1; feval(gLast, h{k - 1})];
endfunction