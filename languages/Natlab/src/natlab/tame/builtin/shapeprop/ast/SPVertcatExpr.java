package natlab.tame.builtin.shapeprop.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import natlab.tame.builtin.shapeprop.ShapePropMatch;
import natlab.tame.valueanalysis.components.shape.Shape;
import natlab.tame.valueanalysis.components.shape.ShapeFactory;
import natlab.tame.valueanalysis.value.Value;

public class SPVertcatExpr extends SPAbstractVectorExpr{
	static boolean Debug = false;
	SPVertExprArglist vl;
	public SPVertcatExpr(SPVertExprArglist vl){
		this.vl = vl;
		//System.out.println("[]");
	}
	
	public ShapePropMatch match(boolean isPatternSide, ShapePropMatch previousMatchResult, List<? extends Value<?>> argValues, int num){
		if(isPatternSide==true){
			if (Debug) System.out.println("just get into SPVertcatExpr, setIsInsideVertcat is true!");
			previousMatchResult.setIsInsideVertcat(true);
			previousMatchResult.setNumInVertcat(0);//reset it
			ShapePropMatch match = vl.match(isPatternSide, previousMatchResult, argValues, num);
			if(match.getIsError()==true){
				Shape<?> errorShape = (new ShapeFactory()).newShapeFromIntegers(null);
				errorShape.FlagItsError();
				HashMap<String, Shape<?>> uppercase = new HashMap<String, Shape<?>>();
				uppercase.put("vertcat", errorShape);
				ShapePropMatch errorMatch = new ShapePropMatch(previousMatchResult, null, uppercase);
				match.setIsInsideVertcat(false);
				match.comsumeArg();
				return errorMatch;
			}
			match.setIsInsideVertcat(false);
			match.comsumeArg();
			return match;
		}
		else{
			//a vertcat in output side, it should return a shape, and now, I think it should return an ArrayList<Integer>, then call newShapeFromIntegers...
			String[] arg = vl.toString().split(",");
			if(arg[0].equals("1")){
				ArrayList<Integer> al = new ArrayList<Integer>(2);
				al.add(1);
				try{
					al.add(Integer.parseInt(arg[1]));
					Shape<?> shape = (new ShapeFactory()).newShapeFromIntegers(al);
					previousMatchResult.addToOutput("vertcat", shape);
					return previousMatchResult;
				}
				catch(Exception e){
					if(previousMatchResult.hasValue(arg[1])){
						al.add(previousMatchResult.getValueOfVariable(arg[1]));
						Shape<?> shape = (new ShapeFactory()).newShapeFromIntegers(al);
						previousMatchResult.addToOutput("vertcat", shape);
						return previousMatchResult;
					}
					else{
						al.add(null);
						Shape<?> shape = (new ShapeFactory()).newShapeFromIntegers(al);
						previousMatchResult.addToOutput("vertcat", shape);
						return previousMatchResult;
					}
				}
			}
			if(arg[1].equals("1")){
				ArrayList<Integer> al = new ArrayList<Integer>(2);
				if(previousMatchResult.hasValue(arg[0])){
					al.add(previousMatchResult.getValueOfVariable(arg[0]));
					al.add(1);
					Shape<?> shape = (new ShapeFactory()).newShapeFromIntegers(al);
					previousMatchResult.addToOutput("vertcat", shape);
					return previousMatchResult;
				}
				else{
					al.add(null);
					al.add(1);
					Shape<?> shape = (new ShapeFactory()).newShapeFromIntegers(al);
					previousMatchResult.addToOutput("vertcat", shape);
					return previousMatchResult;
				}
			}
			else{
				//FIXME deal with the [m,k] or [m,k,j,..] kinds of output
				if (Debug) System.out.println("inside output vertcat expression!");
				ArrayList<Integer> al = new ArrayList<Integer>(arg.length);
				/**
				 * sometime, for loop can not be executed, so you can't put the return inside the for loop.
				 */
				for(String i : arg){
					if(previousMatchResult.hasValue(i)){
						al.add(previousMatchResult.getValueOfVariable(i));
					}
					else{
						al.add(null);
					}
				}
				if (Debug) System.out.println(al);
				Shape<?> shape = (new ShapeFactory()).newShapeFromIntegers(al);
				previousMatchResult.addToOutput("vertcat", shape);
				return previousMatchResult;	
			}
		}
	}
	
	public String toString(){
		return "["+vl.toString()+"]";
	}
}
