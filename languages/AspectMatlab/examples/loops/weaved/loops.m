classdef loops < handle
  properties 
    stack = {};
    top = 1;
  end
  methods 
    function  [] = push(this, s)
      if (numel(s) > 0)
        this.stack{this.top}.lbound = s(1);
        this.stack{this.top}.ubound = s(numel(s));
        this.stack{this.top}.increment = this.increment(s);
      else 
        this.stack{this.top}.lbound = NaN;
        this.stack{this.top}.ubound = NaN;
        this.stack{this.top}.increment = NaN;
      end
      this.stack{this.top}.iteration = 0;
      this.top = (this.top + 1);
    end
    function  [] = pop(this)
      this.top = (this.top - 1);
    end
    function  [lb] = getLBound(this)
      lb = this.stack{(this.top - 1)}.lbound;
    end
    function  [ub] = getUBound(this)
      ub = this.stack{(this.top - 1)}.ubound;
    end
    function  [inc] = getIncrement(this)
      inc = this.stack{(this.top - 1)}.increment;
    end
    function  [iteration] = getIteration(this)
      iteration = this.stack{(this.top - 1)}.iteration;
    end
    function  [] = update(this, iteration)
      this.stack{(this.top - 1)}.iteration = iteration;
    end
    function  [inc] = increment(this, s)
      size = numel(s);
      first = s(1);
      last = s(size);
      step = ((last - first) / (size - 1));
      if ((s(1) : step : s(size)) == s)
        inc = step;
      else 
        inc = NaN;
      end
    end
  end
  methods 
    function  [] = loops_aLoopHead(this, newVal)
      this.push(newVal);
    end
    function  [] = loops_aLoopBody(this, counter)
      this.update(counter);
    end
    function  [] = loops_aLoop(this)
      this.pop();
    end
    function  [varargout] = loops_aLBound(this, AM_caseNum, AM_obj, AM_args, args, counter, file, line, loc, name, newVal, obj)
      global AM_GLOBAL;
% captures all loop invocations for lBound
      varargout{1} = this.getLBound();
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj();
          case 1
            varargout{1} = AM_obj();
        end
      end
    end
    function  [varargout] = loops_aUBound(this, AM_caseNum, AM_obj, AM_args, args, counter, file, line, loc, name, newVal, obj)
      global AM_GLOBAL;
% captures all loop invocations for uBound
      varargout{1} = this.getUBound();
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj();
          case 1
            varargout{1} = AM_obj();
        end
      end
    end
    function  [varargout] = loops_aIncrement(this, AM_caseNum, AM_obj, AM_args, args, counter, file, line, loc, name, newVal, obj)
      global AM_GLOBAL;
% captures all loop invocations for increment
      varargout{1} = this.getIncrement();
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj();
          case 1
            varargout{1} = AM_obj();
        end
      end
    end
    function  [varargout] = loops_aIteration(this, AM_caseNum, AM_obj, AM_args, args, counter, file, line, loc, name, newVal, obj)
      global AM_GLOBAL;
% captures all loop invocations for iteration
      varargout{1} = this.getIteration();
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj();
          case 1
            varargout{1} = AM_obj();
        end
      end
    end
  end
end