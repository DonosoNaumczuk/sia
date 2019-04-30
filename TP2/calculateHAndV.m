function [h, v] = calculateHAndV(k, h, v, weigths, g, gLast)
    for o = 1:k - 2
      h{o} 		= weigths{o} * v{o};
      v{o + 1} = [-1; feval(g, h{o})];
    endfor
    h{k - 1} = weigths{k - 1} * v{k - 1};
    v{k} 	  = [-1; feval(gLast, h{k - 1})];
endfunction