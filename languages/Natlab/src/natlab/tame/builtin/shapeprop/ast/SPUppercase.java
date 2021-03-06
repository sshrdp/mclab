package natlab.tame.builtin.shapeprop.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import natlab.tame.builtin.shapeprop.ShapePropMatch;
import natlab.tame.valueanalysis.value.Value;

import natlab.tame.valueanalysis.components.shape.HasShape;
import natlab.tame.valueanalysis.components.shape.Shape;
import natlab.tame.valueanalysis.components.shape.ShapeFactory;

import natlab.tame.valueanalysis.components.constant.HasConstant;
import natlab.tame.valueanalysis.components.constant.Constant;

public class SPUppercase extends SPAbstractVectorExpr{
	static boolean Debug = false;
	String s;
	public SPUppercase (String s){
		this.s = s;
		//System.out.println(s);
	}
	
	public ShapePropMatch match(boolean isPatternSide, ShapePropMatch previousMatchResult, List<? extends Value<?>> argValues, int num){
		if(isPatternSide==true){
			if(previousMatchResult.isInsideAssign()==true){
				previousMatchResult.saveLatestMatchedUppercase(s);
				return previousMatchResult;
			}
			if(argValues.get(previousMatchResult.getNumMatched())!=null){
				//get indexing current Matrix Value from args
				//get shape info from current Matrix Value
				Shape<?> argumentShape = ((HasShape)argValues.get(previousMatchResult.getNumMatched())).getShape();
				Constant argumentConstant =((HasConstant)argValues.get(previousMatchResult.getNumMatched())).getConstant();
				if(argumentConstant!=null){
					if (Debug) System.out.println("it's a constant!");
					previousMatchResult.setIsError();
					return previousMatchResult;
				}
				//check whether or not current uppercase already in the previousMatchResult
				try{
					if(previousMatchResult.getLatestMatchedUppercase().equals(s)){
						//cases like (M,M->M), those M should be definitely the same!!! if not, return error information, interesting!
						List<Integer> l = new ArrayList<Integer>();
						l = previousMatchResult.getShapeOfVariable(previousMatchResult.getLatestMatchedUppercase()).getDimensions();
						Shape<?> oldShape = (new ShapeFactory()).newShapeFromIntegers(l);
						//Shape<AggrValue<BasicMatrixValue>> newShape = argumentShape.merge(oldShape); this is wrong at all! see last comment!
						if(argumentShape.getDimensions().equals(oldShape.getDimensions())==false){
							//FIXME really weird, cannot call equals method in Shape class, the problem is still generic problem,
							//cannot cast from Shape<?> to Shape<V>
							if (Debug) System.out.println("MATLAB syntax error!");
							//Shape<AggrValue<BasicMatrixValue>> errorShape = (new ShapeFactory<AggrValue<BasicMatrixValue>>(previousMatchResult.factory)).newShapeFromIntegers(null);
							Shape<?> errorShape = (new ShapeFactory()).newShapeFromIntegers(null);
							errorShape.FlagItsError();
							HashMap<String, Integer> lowercase = new HashMap<String, Integer>();
							lowercase.put(s, null);
							HashMap<String, Shape<?>> uppercase = new HashMap<String, Shape<?>>();
							uppercase.put(s, errorShape);
							ShapePropMatch match = new ShapePropMatch(previousMatchResult, lowercase, uppercase);
							match.comsumeArg();
							match.saveLatestMatchedUppercase(s);
							match.setIsError();//this is important!! break from matching algorithm.
							//System.out.println(match.getValueOfVariable(s));
							if (Debug) System.out.println("the shape of "+s+" is "+match.getShapeOfVariable(s));
							if (Debug) System.out.println("matched matrix expression "+match.getLatestMatchedUppercase());
							return match;
						}
						//if new shape and old shape are equals, just return this shape!
						HashMap<String, Integer> lowercase = new HashMap<String, Integer>();
						lowercase.put(s, null);
						HashMap<String, Shape<?>> uppercase = new HashMap<String, Shape<?>>();
						uppercase.put(s, argumentShape);
						ShapePropMatch match = new ShapePropMatch(previousMatchResult, lowercase, uppercase);
						match.comsumeArg();
						match.saveLatestMatchedUppercase(s);
						//System.out.println(match.getValueOfVariable(s));
						if (Debug) System.out.println("the shape of "+s+" is "+match.getShapeOfVariable(s));
						if (Debug) System.out.println("mathcing "+match.getLatestMatchedUppercase());
						return match;
					}
					
				}catch (Exception e){}
				HashMap<String, Integer> lowercase = new HashMap<String, Integer>();
				lowercase.put(s, null);
				HashMap<String, Shape<?>> uppercase = new HashMap<String, Shape<?>>();
				uppercase.put(s, argumentShape);
				ShapePropMatch match = new ShapePropMatch(previousMatchResult, lowercase, uppercase);
				match.comsumeArg();
				match.saveLatestMatchedUppercase(s);
				//System.out.println(match.getValueOfVariable(s));
				if (Debug) System.out.println("the shape of "+s+" is "+match.getShapeOfVariable(s));
				if (Debug) System.out.println("mathcing "+match.getLatestMatchedUppercase());
				return match;
			}
			//FIXME if index pointing empty, means not match, do something
			return previousMatchResult;
		}
		else{
			if (Debug) System.out.println("inside output uppercase "+s);
			//default, which means in the pattern match side, there is no Uppercase matched.
			if(previousMatchResult.getShapeOfVariable(s)==null){
				if(previousMatchResult.getOutputVertcatExpr().size()==0){
					if(previousMatchResult.getLatestMatchedUppercase().equals("$")){
						previousMatchResult.addToOutput(s, previousMatchResult.getShapeOfVariable("$"));
						return previousMatchResult;
					}
					previousMatchResult.addToOutput(s, null);
					return previousMatchResult;
				}
				else if(previousMatchResult.getOutputVertcatExpr().size()==1){
					previousMatchResult.addToVertcatExpr(previousMatchResult.getOutputVertcatExpr().get(0));
					previousMatchResult.copyVertcatToOutput(s);
					return previousMatchResult;
				}
				else {
					previousMatchResult.copyVertcatToOutput(s);
					return previousMatchResult;
				}
			}
			else{
				previousMatchResult.addToOutput(s, previousMatchResult.getShapeOfVariable(s));
				return previousMatchResult;
			}	
		}
	}
	
	public String toString(){
		return s.toString();
	}
}
