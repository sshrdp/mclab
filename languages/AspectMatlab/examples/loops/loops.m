aspect loops

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

aIncrement : around increment
    % captures all loop invocations for increment
    varargout{1} = this.getIncrement();
end

aIteration : around iteration
    % captures all loop invocations for iteration
    varargout{1} = this.getIteration();
end

end

end