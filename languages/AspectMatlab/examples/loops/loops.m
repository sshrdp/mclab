
aspect loops
% Description: modified version of loops aspect(T. Aslam), count the number of
% iteration in a loops(i) and it's subloop (j) using a stack.
% Status:Non-working, error EException in thread "main" java.lang.ClassCastException: ast.Function
%	at ast.PatternDesignator.ShadowMatch_compute(Unknown Source)
%in files loops.m, caused by patern loop(i) and loop(j) (Can't seems to
 %pattern on specific iterative var.
% Compile with: java -jar amc.jar loops/loops.m -m -main loops/test.m
properties
    stack = {};
    top = 1;
end

methods

function push(this, s)
    if(numel(s) > 0)
          this.stack{this.top}.lbound = s(1);
          this.stack{this.top}.ubound = s(numel(s));
          this.stack{this.top}.increment = this.increment(s);
      else
          this.stack{this.top}.lbound = NaN;
          this.stack{this.top}.ubound = NaN;
          this.stack{this.top}.increment = NaN;
      end
    this.stack{this.top}.iteration = 0;
    this.top = this.top + 1;
end

function pop(this)
    this.top = this.top - 1;
end

function lb = getLBound(this)
    lb = this.stack{this.top-1}.lbound;
end

function ub = getUBound(this)
    ub = this.stack{this.top-1}.ubound;
end

function inc = getIncrement(this)
    inc = this.stack{this.top-1}.increment;
end

function iteration = getIteration(this)
    iteration = this.stack{this.top-1}.iteration;
end

function update(this, iteration)
    this.stack{this.top-1}.iteration = iteration;
end

function inc = increment(this, s)
    size = numel(s);
    first = s(1);
    last = s(size);
    step = (last-first)/(size-1);
    if(s(1):step:s(size) == s)
        inc = step;
    else
        inc = NaN;
    end
end

end

patterns
    ploophead : loophead(*);
    ploopbody : loopbody(*);
    ploop : loop(*);
    
    lbound : call(lBound) & within(loops,*);
    ubound : call(uBound) & within(loops,*);
    increment : call(increment) & within(loops,*);
    iteration : call(iteration) & within(loops,*);
    ploopbodyI : loopbody(i); %NonWorking
    ploopbodyJ : loopbody(j); %NonWorking
end


actions


aLoopHead : after ploophead : (newVal)
    this.push(newVal);
end

aLoopBody : before ploopbody : (counter)
    this.update(counter);
    end

aLoop : after ploop
    this.pop();
    end

aLBound : around lbound
    % captures all loop invocations for lBound
    varargout{1} = this.getLBound();
end

aUBound : around ubound
    % captures all loop invocations for uBound
    varargout{1} = this.getUBound();
end

aIncrement : before ploopbody
    % captures all loop invocations for increment
    varargout{1} = this.getIncrement();
end
aIterationJ : before ploobodyJ
        disp({'Iteration J: ', this.getIteration()});
        end
aIterationI : before ploopbodyI
        disp({'Iteration I: ',this.getIteration()});
end

end

end