package aspectMatlab;

import natlab.DecIntNumericLiteralValue;
import aspectMatlab.ast.*;
import aspectMatlab.ast.List;
import aspectMatlab.ast.Properties;

import java.util.*;

public class AspectsEngine {

	static LinkedList<action> actionsList = new LinkedList<action>();
	static LinkedList<pattern> patternsList = new LinkedList<pattern>();
	static ArrayList<String> aspectList = new ArrayList<String>();

	static final public String AFTER = "after";
	static final public String BEFORE = "before";
	static final public String AROUND = "around";

	static final public String SET = "set";
	static final public String GET = "get";
	static final public String CALL = "call";
	static final public String EXECUTION = "execution";

	static final public String ARGS = "args";
	static final public String DIMS = "dims";
	static final public String NEWVAL = "newVal";
	static final public String OBJ = "obj";
	static final public String THIS = "this";
	static final public String NAME = "name";

	static final public String PROCEED_FUN_NAME = "proceed";
	static final public String GLOBAL_STRUCTURE = "AM_GLOBAL";
	static final public String AM_CF_SCRIPT = "AM_CF_Script_";

	static final public Name CF_OUTPUT = new Name("AM_retValue");
	static final public Name CF_INPUT_CASE = new Name("AM_caseNum");
	static final public Name CF_INPUT_OBJ = new Name("AM_obj");
	static final public Name CF_INPUT_AGRS = new Name("AM_args");

	static public int correspondingCount = 0;
	public static int getCorrespondingCount() { return correspondingCount; }
	private static String generateCorrespondingFunctionName(){
		return AM_CF_SCRIPT + correspondingCount++;
	}

	public static void generateCorrespondingFunction(Expr pe) {
		if(pe.getWeavability()) {
			String target = pe.FetchTargetExpr();
			for(pattern pat : patternsList) {
				if(pat.getTarget().compareTo(target) == 0 || pat.getTarget().compareTo("*") == 0) {
					ASTNode node = pe;
					while(node != null && !(node instanceof Function || node instanceof PropertyAccess || node instanceof Script))
						node = node.getParent();

					if(node != null) {
						Function corFun = null;
						IntLiteralExpr ile = null;

						if(node instanceof Function) {
							Function prog = (Function)node;
							corFun = prog.getCorrespondingFunction();

							if(corFun == null) {
								String funName = "AM_CF_" + prog.getName();
								corFun = createCorrespondingFunction(funName, false);	
								prog.addNestedFunction(corFun);
								prog.setCorrespondingFunction(corFun);
							}

							ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(prog.getCorrespondingCount())));
							prog.incCorrespondingCount();

							addSwitchCaseToCorrespondingFunction(pe, corFun, ile, false);
							insertCallToCorrespondingFunction(pe, corFun, ile, false);

						} else if(node instanceof PropertyAccess) {
							PropertyAccess prog = (PropertyAccess)node;
							corFun = prog.getCorrespondingFunction();

							if(corFun == null) {
								String funName = "AM_CF_" + prog.getAccess() + "_" + prog.getName();
								corFun = createCorrespondingFunction(funName, false);	
								prog.addNestedFunction(corFun);
								prog.setCorrespondingFunction(corFun);
							}

							ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(prog.getCorrespondingCount())));
							prog.incCorrespondingCount();

							addSwitchCaseToCorrespondingFunction(pe, corFun, ile, false);
							insertCallToCorrespondingFunction(pe, corFun, ile, false);

						} else if(node instanceof Script) {
							Script prog = (Script)node;
							corFun = prog.getCorrespondingFunction();

							if(corFun == null) {
								String funName = generateCorrespondingFunctionName();
								corFun = createCorrespondingFunction(funName, true);	
								prog.getParent().addChild(new FunctionList(new aspectMatlab.ast.List<Function>().add(corFun)));
								prog.setCorrespondingFunction(corFun);
							}

							ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(prog.getCorrespondingCount())));
							prog.incCorrespondingCount();

							addSwitchCaseToCorrespondingFunction(pe, corFun, ile, true);
							insertCallToCorrespondingFunction(pe, corFun, ile, true);
						}
					}

					return;
				}
			}
		}
	}

	private static Function createCorrespondingFunction(String funName, boolean isScript){
		aspectMatlab.ast.List<Name> output = new aspectMatlab.ast.List<Name>();
		output.add(CF_OUTPUT);

		aspectMatlab.ast.List<Name> input = new aspectMatlab.ast.List<Name>();
		input.add(CF_INPUT_CASE);
		if(isScript)
			input.add(CF_INPUT_OBJ);
		input.add(CF_INPUT_AGRS);

		SwitchStmt ss = new SwitchStmt(new NameExpr(CF_INPUT_CASE), new aspectMatlab.ast.List<SwitchCaseBlock>(), new Opt<DefaultCaseBlock>());
		aspectMatlab.ast.List<Stmt> sl = new aspectMatlab.ast.List<Stmt>();
		sl.add(ss);

		return new Function(output, funName, input, new aspectMatlab.ast.List<HelpComment>(), sl, new aspectMatlab.ast.List<Function>());
	}

	private static void addSwitchCaseToCorrespondingFunction(Expr pe, Function corFun, IntLiteralExpr ile, boolean isScript){
		Expr exp = (Expr) pe.copy();
		Expr tmp = (Expr) pe.copy();
		aspectMatlab.ast.List<Expr> input = new aspectMatlab.ast.List<Expr>();
		
		if(exp instanceof ParameterizedExpr || exp instanceof CellIndexExpr) {
			int size = 0;

			if(exp instanceof ParameterizedExpr) {
				size = ((ParameterizedExpr)exp).getArgs().getNumChild();
				tmp = ((ParameterizedExpr)exp).getTarget();
			} else if(exp instanceof CellIndexExpr) {
				size = ((CellIndexExpr)exp).getArgs().getNumChild();
				tmp = ((CellIndexExpr)exp).getTarget();
			}

			for(int i=1; i<=size; i++)
				input.add(new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new aspectMatlab.ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));

			if(exp instanceof ParameterizedExpr)
				exp = new ParameterizedExpr(((ParameterizedExpr)exp).getTarget(), input);
			else if(exp instanceof CellIndexExpr)
				exp = new CellIndexExpr(((CellIndexExpr)exp).getTarget(), input);
		}

		aspectMatlab.ast.List<Stmt> scsl = new aspectMatlab.ast.List<Stmt>();

		AssignStmt tmpStmt = new AssignStmt();
		if(isScript) {
			Expr exp1 = new NameExpr(new Name("AM_tmp_" + tmp.FetchTargetExpr()));
			tmpStmt = new AssignStmt(exp1, new NameExpr(CF_INPUT_OBJ));
			tmpStmt.setOutputSuppressed(true);
			tmpStmt.setWeavability(false, true);
			scsl.add(tmpStmt);
			
			if(exp instanceof ParameterizedExpr)
				exp = new ParameterizedExpr(exp1, input);
			else if(exp instanceof CellIndexExpr)
				exp = new CellIndexExpr(exp1, input);
			else
				exp = exp1;
		}

		Expr lhs = new NameExpr(CF_OUTPUT);
		lhs.setWeavability(false);
		exp.setWeavability(true);
		
		AssignStmt stmt = new AssignStmt(lhs, exp);
		stmt.setOutputSuppressed(true);
		scsl.add(stmt);

		SwitchCaseBlock scb = new SwitchCaseBlock(ile, scsl);
		SwitchStmt ss = (SwitchStmt) corFun.getStmt(0);
		ss.addSwitchCaseBlock(scb);
	}

	private static void insertCallToCorrespondingFunction(Expr pe, Function corFun, IntLiteralExpr ile, boolean isScript){
		aspectMatlab.ast.List<Expr> input = new aspectMatlab.ast.List<Expr>().add(ile);
		aspectMatlab.ast.List<Expr> args = new aspectMatlab.ast.List<Expr>();

		Expr tmp = (Expr) pe.copy();

		if(pe instanceof ParameterizedExpr) {
			args = ((ParameterizedExpr)pe).getArgs();
			tmp = ((ParameterizedExpr)pe).getTarget();
		} else if(pe instanceof CellIndexExpr) {
			args = ((CellIndexExpr)pe).getArgs();
			tmp = ((CellIndexExpr)pe).getTarget();
		}

		IfStmt ifstmt = new IfStmt();

		if(isScript) {
			aspectMatlab.ast.List<Expr> lst = new aspectMatlab.ast.List<Expr>().add(new StringLiteralExpr(tmp.FetchTargetExpr()));
			lst.add(new StringLiteralExpr("var"));
			ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), lst);
			BinaryExpr cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));

			AssignStmt eas = new AssignStmt(tmp, new StringLiteralExpr("AM_tmp_" + tmp.FetchTargetExpr()));
			eas.setOutputSuppressed(true);
			eas.setWeavability(false, true);
			IfBlock ib = new IfBlock(cond, new aspectMatlab.ast.List<Stmt>().add(eas));
			ifstmt = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

			ASTNode node = pe;
			while(node != null && !(node instanceof Stmt))
				node = node.getParent();

			if(node != null) {
				int ind = node.getParent().getIndexOfChild(node);
				node.getParent().insertChild(ifstmt, ind);
			}

			input.add(tmp);
		}

		CellArrayExpr values = new CellArrayExpr();
		values.addRow(new Row(args));
		input.add(values);

		ParameterizedExpr call = new ParameterizedExpr(new NameExpr(new Name(corFun.getName())), input);
		call.setWeavability(false);

		int ind = pe.getParent().getIndexOfChild(pe);
		pe.getParent().setChild(call, ind);
	}

	private static String generateActionName(String aspect, String action){
		return aspect + "_" + action;
	}

	public static void fetchAspectInfo(Program prog)
	{ 	
		Aspect aspect = (Aspect) prog;
		aspectList.add(aspect.getName());

		for(Patterns patterns : aspect.getPatterns())
			for(Pattern pattern : patterns.getPatterns())
			{
				String name = pattern.getName();
				PatternDesignator pd = (PatternDesignator) pattern.getPD();
				String type = pd.getName();

				String variable = pd.getArgs().getChild(pd.getArgs().getNumChild()-1).getID();
				variable = variable.substring(variable.lastIndexOf('.')+1);

				String target = variable;
				String dims = "0";
				boolean more = false;

				if(variable.contains("$")) {
					target = variable.substring(0, variable.lastIndexOf('$'));
					dims = variable.substring(variable.lastIndexOf('$')+1);

					if(dims.contains("+")) {
						more = true;
						dims = dims.substring(0, dims.lastIndexOf('+'));
					}
				}

				patternsList.add(new pattern(name, type, target, dims, more));
			}

		for(Actions lst : aspect.getActions())
			for(AspectAction action : lst.getAspectActions())
			{
				String name = action.getName();
				String type = action.getType();
				String pattern = action.getPattern();
				String modifiedName = generateActionName(aspect.getName(), name);

				pattern pat = null;
				for(int i = 0; i<patternsList.size(); i++) {
					pattern tmp = patternsList.get(i);
					if(tmp.getName().compareTo(pattern) == 0) {
						pat = tmp;
						break;
					}
				}

				Function fun = new Function();
				if(type.compareTo(AROUND) != 0)
					fun = new Function(new aspectMatlab.ast.List<Name>(), modifiedName, action.getSelectors(), new aspectMatlab.ast.List<HelpComment>(), action.getStmts(), action.getNestedFunctions());
				else
					fun = createAroundFunction(modifiedName, action, true);

				actionsList.add(new action(modifiedName, type, pat, fun, aspect.getName()));
			}
	}

	public static ClassDef convertToClass(Program prog)
	{ 	
		Aspect aspect = (Aspect) prog;
		ClassDef out = new ClassDef();

		out.setName(aspect.getName());
		SuperClass sc = new SuperClass();
		sc.setName("handle");
		out.setSuperClass(sc, 0);

		for(Properties propertys : aspect.getPropertys())
			out.addProperty(propertys);

		for(Methods methods : aspect.getMethods())
			out.addMethod(methods);

		Methods methods = new Methods();
		for(Actions actions : aspect.getActions())
			for(AspectAction action : actions.getAspectActions())
			{
				String name = action.getName();
				String type = action.getType();

				String modifiedName = generateActionName(aspect.getName(), name);

				if(type.compareTo(AROUND) != 0) {
					aspectMatlab.ast.List<Name> input = action.getSelectors();
					input.insertChild(new Name(THIS), 0);
					methods.addFunction(new Function(new aspectMatlab.ast.List<Name>(), modifiedName, input, new aspectMatlab.ast.List<HelpComment>(), action.getStmts(), action.getNestedFunctions()));
				} else
					methods.addFunction(createAroundFunction(modifiedName, action, false));
			}

		out.addMethod(methods);

		return out;
	}

	private static Function createAroundFunction(String modifiedName, AspectAction action, boolean isConvertProceed){
		aspectMatlab.ast.List<Name> output = new aspectMatlab.ast.List<Name>();
		output.add(CF_OUTPUT);

		aspectMatlab.ast.List<Name> input = action.getSelectors();	

		aspectMatlab.ast.List<Function> nf = action.getNestedFunctions();

		if(isConvertProceed) {
			input.add(CF_INPUT_CASE);
			input.add(CF_INPUT_OBJ);
			input.add(CF_INPUT_AGRS);

			convertProceedCalls(action.getStmts());
			Function pf = createCorrespondingFunction(PROCEED_FUN_NAME, true);
			pf.setOutputParamList(new List<Name>());
			nf.add(pf);
		} else
			input.insertChild(new Name(THIS), 0);

		return new Function(output, modifiedName, input, new aspectMatlab.ast.List<HelpComment>(), action.getStmts(), nf);
	}

	private static void convertProceedCalls(aspectMatlab.ast.List<Stmt> stmts){
		for(Stmt stmt : stmts) {
			stmt.ProceedTransformation();
		}
	}

	public static void transformProceedCall(Expr pe){
		if(pe.FetchTargetExpr().compareTo("proceed") == 0) {
			aspectMatlab.ast.List<Expr> input = new aspectMatlab.ast.List<Expr>();
			input.add(new NameExpr(CF_INPUT_CASE));
			input.add(new NameExpr(CF_INPUT_OBJ));
			input.add(new NameExpr(CF_INPUT_AGRS));

			ParameterizedExpr call = new ParameterizedExpr(new NameExpr(new Name(PROCEED_FUN_NAME)), input);
			int ind = pe.getParent().getIndexOfChild(pe);
			pe.getParent().setChild(call, ind);
		}
	}

	/*
	private static int matchAndWeave(aspectMatlab.ast.List<Stmt> stmts, int s, String type, String target, AssignStmt context) {
		Expr rhs = context.getRHS();
		Expr lhs = context.getLHS();
		int count = 0;

		for(int j=0; j<actionsList.size(); j++)
		{
			action act = actionsList.get(j);
			pattern pat = act.getPattern();

			if(pat.getType().compareTo(type) == 0 && pat.getTarget().compareTo(target) == 0){
				Function fun = act.getFunction();
				NameExpr funcName = new NameExpr(new Name(act.getName()));
				aspectMatlab.ast.List<Expr> lstExpr = new aspectMatlab.ast.List<Expr>();

				for(Name param : fun.getInputParams()) {
					if(param.getID().compareTo(NEWVAL) == 0) {
						lstExpr.add(getNewVal(rhs));
					} else if(param.getID().compareTo(ARGS) == 0) {
						if(pat.getType().compareTo(CALL) == 0)
							lstExpr.add(getDims(rhs));
						else
							lstExpr.add(new CellArrayExpr());
					} else if(param.getID().compareTo(DIMS) == 0) {
						if(pat.getType().compareTo(SET) == 0)
							lstExpr.add(getDims(lhs));
						else if(pat.getType().compareTo(GET) == 0)
							lstExpr.add(getDims(rhs));
						else
							lstExpr.add(new CellArrayExpr());
					} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						lstExpr.add(ile);
					} else if(param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
						lstExpr.add(new NameExpr(new Name(target)));
					} else if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
						if(pat.getType().compareTo(SET) == 0)
							lstExpr.add(rhs);
						else //get, call
							lstExpr.add(getDims(rhs));
					} else
						lstExpr.add(new CellArrayExpr());
				}

				ParameterizedExpr pe = new ParameterizedExpr(funcName, lstExpr);
				Stmt action;
				if(act.getType().compareTo(AROUND) == 0)
					action = new AssignStmt(lhs, pe);
				else
					action = new ExprStmt(pe);

				Stmt call = action;
				action.setOutputSuppressed(true);

				if(!(pat.getDims().compareTo("0") == 0)) {
					BinaryExpr cond = null; 
					if(!pat.getDimsAndMore())
						cond = new EQExpr(new NameExpr(new Name("numel("+target+")")), new NameExpr(new Name(pat.getDims())));
					else
						cond = new GEExpr(new NameExpr(new Name("numel("+target+")")), new NameExpr(new Name(pat.getDims())));

					IfBlock ib = new IfBlock(cond, new aspectMatlab.ast.List<Stmt>().add(action));
					call = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

					if(act.getType().compareTo(AROUND) == 0) {					
						AssignStmt eas = new AssignStmt(lhs, rhs);
						eas.setOutputSuppressed(true);
						ElseBlock eb = new ElseBlock(new aspectMatlab.ast.List<Stmt>().add(eas));
						call = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
					}
				}

				if(act.getType().compareTo(BEFORE) == 0) {
					stmts.insertChild(call, s);
					count += 1;
				} else if(act.getType().compareTo(AFTER) == 0) {
					stmts.insertChild(call, s+1);
					count += 1;
				} else if(act.getType().compareTo(AROUND) == 0) {
					IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
					addSwitchCaseToAroundCorrespondingFunction(rhs, fun.getNestedFunction(0), ile, type);
					fun.incCorrespondingCount();
					//context.setRHS(pe);
					stmts.setChild(call, s);
				}
			}
		}

		return count;
	}
	 */

	private static int setMatchAndWeave(aspectMatlab.ast.List<Stmt> stmts, int s, String target, AssignStmt context) {
		Expr rhs = context.getRHS();
		Expr lhs = context.getLHS();
		int acount = 0, bcount = 0;

		for(int j=0; j<actionsList.size(); j++)
		{
			action act = actionsList.get(j);
			pattern pat = act.getPattern();

			if(pat.getType().compareTo(SET) == 0 && (pat.getTarget().compareTo(target) == 0 || pat.getTarget().compareTo("*") == 0)){
				Function fun = act.getFunction();
				aspectMatlab.ast.List<Expr> lstExpr = new aspectMatlab.ast.List<Expr>();

				for(Name param : fun.getInputParams()) {
					if(param.getID().compareTo(NEWVAL) == 0) {
						//TODO: more type of new values
						lstExpr.add(getNewVal(rhs));
					} else if(param.getID().compareTo(DIMS) == 0) {
						lstExpr.add(getDims(lhs));
					} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						lstExpr.add(ile);
					} else if(param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
						lstExpr.add(new NameExpr(new Name(target)));
					} else if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
						lstExpr.add(rhs);
					} else if(param.getID().compareTo(THIS) == 0) {
						//do nothing
					} else if(param.getID().compareTo(NAME) == 0) {
						lstExpr.add(new StringLiteralExpr(target));
					} else if(param.getID().compareTo(OBJ) == 0) {
						lstExpr.add(new NameExpr(new Name(target)));
					} else
						lstExpr.add(new CellArrayExpr());
				}

				Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
				Expr de2 = new DotExpr(de1, new Name(act.getName()));
				ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);

				Stmt action;
				if(act.getType().compareTo(AROUND) == 0)
					action = new AssignStmt(lhs, pe);
				else
					action = new ExprStmt(pe);

				Stmt call = action;
				action.setOutputSuppressed(true);

				if(!(pat.getDims().compareTo("0") == 0)) {
					BinaryExpr cond = null; 
					aspectMatlab.ast.List<Expr> lst = new aspectMatlab.ast.List<Expr>().add(new NameExpr(new Name(target)));
					ParameterizedExpr numel = new ParameterizedExpr(new NameExpr(new Name("numel")), lst);

					if(!pat.getDimsAndMore())
						cond = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));
					else
						cond = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));

					IfBlock ib = new IfBlock(cond, new aspectMatlab.ast.List<Stmt>().add(action));
					call = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

					if(act.getType().compareTo(AROUND) == 0) {					
						AssignStmt eas = new AssignStmt(lhs, rhs);
						eas.setOutputSuppressed(true);
						ElseBlock eb = new ElseBlock(new aspectMatlab.ast.List<Stmt>().add(eas));
						call = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
					}
				}

				if(act.getType().compareTo(BEFORE) == 0) {
					stmts.insertChild(call, s);
					bcount += 1;
				} else if(act.getType().compareTo(AFTER) == 0) {
					stmts.insertChild(call, s+bcount+acount+1);
					acount += 1;
				} else if(act.getType().compareTo(AROUND) == 0) {
					IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
					addSwitchCaseToAroundCorrespondingFunction(rhs, fun.getNestedFunction(0), ile, SET);
					fun.incCorrespondingCount();
					stmts.setChild(call, s+bcount);
				}
			}
		}

		return acount + bcount;
	}

	private static int getOrCallMatchAndWeave(aspectMatlab.ast.List<Stmt> stmts, int s, String target, AssignStmt context) {
		Expr rhs = context.getRHS();
		Expr lhs = context.getLHS();
		int acount = 0, bcount = 0;

		for(int j=0; j<actionsList.size(); j++)
		{
			action act = actionsList.get(j);
			pattern pat = act.getPattern();

			if((pat.getType().compareTo(GET) == 0 || pat.getType().compareTo(CALL) == 0) 
					&& (pat.getTarget().compareTo(target) == 0 || pat.getTarget().compareTo("*") == 0)){
				Function fun = act.getFunction();
				aspectMatlab.ast.List<Expr> lstExpr = new aspectMatlab.ast.List<Expr>();

				for(Name param : fun.getInputParams()) {
					if(param.getID().compareTo(ARGS) == 0) {
						if(pat.getType().compareTo(CALL) == 0)
							lstExpr.add(getDims(rhs));
						else
							lstExpr.add(new CellArrayExpr());
					} else if(param.getID().compareTo(DIMS) == 0) {
						if(pat.getType().compareTo(GET) == 0)
							lstExpr.add(getDims(rhs));
						else
							lstExpr.add(new CellArrayExpr());
					} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						lstExpr.add(ile);
					} else if(param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
						if(pat.getType().compareTo(CALL) == 0)
							lstExpr.add(new FunctionHandleExpr(new Name(target)));
						else
							lstExpr.add(new NameExpr(new Name(target)));
					} else if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
						lstExpr.add(getDims(rhs));
					} else if(param.getID().compareTo(THIS) == 0) {
						//do nothing
					} else if(param.getID().compareTo(NAME) == 0) {
						lstExpr.add(new StringLiteralExpr(target));
					} else if(param.getID().compareTo(OBJ) == 0) {
						if(pat.getType().compareTo(GET) == 0)
							lstExpr.add(new NameExpr(new Name(target)));
						else
							lstExpr.add(new CellArrayExpr());
					} else
						lstExpr.add(new CellArrayExpr());
				}

				Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
				Expr de2 = new DotExpr(de1, new Name(act.getName()));
				ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);

				Stmt action;
				if(act.getType().compareTo(AROUND) == 0)
					action = new AssignStmt(lhs, pe);
				else
					action = new ExprStmt(pe);

				Stmt call = action;
				action.setOutputSuppressed(true);
				BinaryExpr cond;

				ASTNode node = stmts;
				while(node != null && !(node instanceof Function))
					node = node.getParent();
				boolean isScriptCF = false;
				ParameterizedExpr strcmp = new ParameterizedExpr();
				
				if(node != null && ((Function)node).getName().startsWith(AM_CF_SCRIPT)) {
					isScriptCF = true;
					aspectMatlab.ast.List<Expr> lst = new aspectMatlab.ast.List<Expr>().add(new NameExpr(CF_INPUT_OBJ));
					lst.add(new StringLiteralExpr("AM_tmp_" + target));
					strcmp = new ParameterizedExpr(new NameExpr(new Name("strcmp")), lst);
				}
				
				aspectMatlab.ast.List<Expr> nlst = new aspectMatlab.ast.List<Expr>().add(new NameExpr(new Name(target)));
				ParameterizedExpr numel = new ParameterizedExpr(new NameExpr(new Name("numel")), nlst);
				aspectMatlab.ast.List<Expr> elst = new aspectMatlab.ast.List<Expr>().add(new StringLiteralExpr(target));
				elst.add(new StringLiteralExpr("var"));
				ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);

				if(pat.getType().compareTo(GET) == 0) {
					if(!(pat.getDims().compareTo("0") == 0)) {
						BinaryExpr cond2;

						if(!pat.getDimsAndMore())
							cond2 = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));
						else
							cond2 = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));

						IfBlock ib = new IfBlock(cond2, new aspectMatlab.ast.List<Stmt>().add(action));
						call = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

						if(act.getType().compareTo(AROUND) == 0) {					
							AssignStmt eas = new AssignStmt(lhs, rhs);
							eas.setOutputSuppressed(true);
							ElseBlock eb = new ElseBlock(new aspectMatlab.ast.List<Stmt>().add(eas));
							call = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
						}
					}

					if(isScriptCF)
						cond = new NEExpr(strcmp, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
					else
						cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
				} else {
					if(isScriptCF)
						cond = new EQExpr(strcmp, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
					else
					cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
				}

				IfBlock ib = new IfBlock(cond, new aspectMatlab.ast.List<Stmt>().add(call));
				Stmt outerIf = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

				if(act.getType().compareTo(AROUND) == 0) {					
					AssignStmt eas = new AssignStmt(lhs, rhs);
					eas.setOutputSuppressed(true);
					ElseBlock eb = new ElseBlock(new aspectMatlab.ast.List<Stmt>().add(eas));
					outerIf = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
				}

				if(act.getType().compareTo(BEFORE) == 0) {
					stmts.insertChild(outerIf, s);
					bcount += 1;
				} else if(act.getType().compareTo(AFTER) == 0) {
					stmts.insertChild(outerIf, s+bcount+acount+1);
					acount += 1;
				} else if(act.getType().compareTo(AROUND) == 0) {
					IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
					addSwitchCaseToAroundCorrespondingFunction(rhs, fun.getNestedFunction(0), ile, pat.getType());
					fun.incCorrespondingCount();
					stmts.setChild(outerIf, s+bcount);
				}
			}
		}

		return acount + bcount;
	}

	private static void addSwitchCaseToAroundCorrespondingFunction(Expr pe, Function corFun, IntLiteralExpr ile, String type){
		Expr exp = (Expr) pe.copy();
		Expr tmp = (Expr) pe.copy();
		aspectMatlab.ast.List<Expr> input = new aspectMatlab.ast.List<Expr>();

		if(exp instanceof ParameterizedExpr || exp instanceof CellIndexExpr) {
			int size = 0;

			if(exp instanceof ParameterizedExpr) {
				size = ((ParameterizedExpr)exp).getArgs().getNumChild();
				tmp = ((ParameterizedExpr)exp).getTarget();
			} else if(exp instanceof CellIndexExpr) {
				size = ((CellIndexExpr)exp).getArgs().getNumChild();
				tmp = ((CellIndexExpr)exp).getTarget();
			}

			for(int i=1; i<=size; i++)
				input.add(new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new aspectMatlab.ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));

			if(exp instanceof ParameterizedExpr)
				exp = new ParameterizedExpr(((ParameterizedExpr)exp).getTarget(), input);
			else if(exp instanceof CellIndexExpr)
				exp = new CellIndexExpr(((CellIndexExpr)exp).getTarget(), input);
		}

		aspectMatlab.ast.List<Stmt> scsl = new aspectMatlab.ast.List<Stmt>();

		AssignStmt tmpStmt = new AssignStmt();
		if(type.compareTo(SET) == 0) {
			exp = new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new aspectMatlab.ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1)))));
		} else if(type.compareTo(EXECUTION) == 0 || type.compareTo(CALL) == 0) {
			exp = new ParameterizedExpr(new NameExpr(CF_INPUT_OBJ), input);
		} else {
			tmpStmt = new AssignStmt(tmp, new NameExpr(CF_INPUT_OBJ));
			tmpStmt.setOutputSuppressed(true);
			tmpStmt.setWeavability(false, true);
			scsl.add(tmpStmt);
		}

		AssignStmt stmt = new AssignStmt(new NameExpr(CF_OUTPUT), exp);
		stmt.setOutputSuppressed(true);
		scsl.add(stmt);

		SwitchCaseBlock scb = new SwitchCaseBlock(ile, scsl);
		SwitchStmt ss = (SwitchStmt) corFun.getStmt(0);
		ss.addSwitchCaseBlock(scb);
	}

	private static void transformForStmt(aspectMatlab.ast.List<Stmt> stmts)
	{
		int stmtCount = stmts.getNumChild();

		for(int s=0; s<stmtCount; s++)
		{
			if(stmts.getChild(s) instanceof ForStmt) {
				ForStmt fs = (ForStmt) stmts.getChild(s);
				if( !fs.isAspectTransformed() ) {
					AssignStmt as_old = fs.getAssignStmt();
					Expr lhs = as_old.getLHS();
					String tmpAS = "AM_" + "tmpAS_";
					String tmpFS = "AM_" + "tmpFS_";

					if(lhs instanceof NameExpr){
						NameExpr ne = (NameExpr) lhs;
						tmpAS += ne.getName().getID();
						tmpFS += ne.getName().getID();
					}

					AssignStmt as_out = new AssignStmt();
					as_out.setRHS(as_old.getRHS());
					as_out.setLHS(new NameExpr(new Name(tmpAS)));
					as_out.setOutputSuppressed(true);
					as_out.getLHS().setWeavability(false);

					AssignStmt as_for = new AssignStmt();
					as_for.setRHS(new RangeExpr(new IntLiteralExpr(new DecIntNumericLiteralValue("1")), new Opt<Expr>(), new ParameterizedExpr(new NameExpr(new Name("numel")), new aspectMatlab.ast.List<Expr>().add(as_out.getLHS()))));
					as_for.setLHS(new NameExpr(new Name(tmpFS)));
					as_for.setWeavability(false, true);

					AssignStmt as_in = new AssignStmt();
					as_in.setRHS(new ParameterizedExpr(new NameExpr(new Name(tmpAS)), new aspectMatlab.ast.List<Expr>().add(new NameExpr(new Name(tmpFS)))));
					as_in.setLHS(as_old.getLHS());
					as_in.setOutputSuppressed(true);
					as_in.getRHS().setWeavability(false);

					aspectMatlab.ast.List<Stmt> lstFor = new aspectMatlab.ast.List<Stmt>();
					lstFor.add(as_in);
					for(Stmt stmt : fs.getStmts())
						lstFor.add(stmt);

					ForStmt fs_new = new ForStmt();
					fs_new.setAssignStmt(as_for);
					fs_new.setStmtList(lstFor);
					fs_new.setAspectTransformed(true);

					stmts.removeChild(s);
					stmts.insertChild(as_out, s);
					stmts.insertChild(fs_new, s+1);
					s++;
					stmtCount++;
				}
			}
		}
	}

	public static void weaveGlobalStructure(CompilationUnits cu) {
		aspectMatlab.ast.List<Stmt> stmts = new aspectMatlab.ast.List<Stmt>();
		GlobalStmt gs = new GlobalStmt(new aspectMatlab.ast.List<Name>().add(new Name(GLOBAL_STRUCTURE)));
		gs.setOutputSuppressed(true);
		stmts.add(gs);

		for(String s : aspectList){
			Expr rhs = new NameExpr(new Name(s));
			Expr lhs = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(s));
			AssignStmt as = new AssignStmt(lhs, rhs);
			as.setOutputSuppressed(true);
			as.setWeavability(false, true);

			UnaryExpr cond = new NotExpr(new NameExpr(new Name("isfield("+GLOBAL_STRUCTURE+", '"+s+"')")));
			IfBlock ib = new IfBlock(cond, new aspectMatlab.ast.List<Stmt>().add(as));
			Stmt is = new IfStmt(new aspectMatlab.ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

			stmts.add(is);
		}

		for(int i=0; i < cu.getNumProgram(); i++) {
			Program p = cu.getProgram(i);
			p.weaveGlobalStructure(stmts);
		}
	}

	public static void weaveStmts(aspectMatlab.ast.List<Stmt> stmts)
	{
		transformForStmt(stmts);

		int stmtCount = stmts.getNumChild();

		for(int s=0; s<stmtCount; s++)
		{
			Stmt stmt = stmts.getChild(s);
			if(stmt instanceof ExprStmt) {
				//TODO: just get n call
			} else if(stmt instanceof AssignStmt) {
				AssignStmt as = (AssignStmt) stmt;

				if(as.getWeavability()) {
					Expr lhs = as.getLHS();
					Expr rhs = as.getRHS();

					int count = 0;
					String varName = "";

					if(lhs.getWeavability()) {
						varName = lhs.FetchTargetExpr();
						if(varName.compareTo("") != 0) {
							StringTokenizer st = new StringTokenizer(varName, ",");
							while (st.hasMoreTokens()) {
								count += setMatchAndWeave(stmts, s, st.nextToken(), as);
							}
						}
					}

					if(rhs.getWeavability()) {
						varName = rhs.FetchTargetExpr();
						if(varName.compareTo("") != 0)  {
							StringTokenizer st = new StringTokenizer(varName, ",");
							while (st.hasMoreTokens()) {
								count += getOrCallMatchAndWeave(stmts, s, st.nextToken(), as);
							}
						}
					}

					s += count;
					stmtCount += count;
				}
			} else {
				stmt.aspectsWeave();
			}
		}
	}

	public static void weaveFunction(Function func)
	{
		for(int j=0; j<actionsList.size(); j++)
		{
			action act = actionsList.get(j);
			pattern pat = act.getPattern();

			if(pat.getType().compareTo(EXECUTION) == 0 && (pat.getTarget().compareTo(func.getName()) == 0 || pat.getTarget().compareTo("*") == 0)){
				Function fun = act.getFunction();
				NameExpr funcName = new NameExpr(new Name(act.getName()));

				aspectMatlab.ast.List<Expr> lstExpr = new aspectMatlab.ast.List<Expr>();

				for(Name param : fun.getInputParams()) {
					if(param.getID().compareTo(ARGS) == 0 || param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
						CellArrayExpr args = new CellArrayExpr();
						Row row = new Row();
						for(Name arg : func.getInputParams()) {
							row.addElement(new NameExpr(arg));
						}
						args.addRow(row);
						lstExpr.add(args);
					} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						lstExpr.add(ile);
					} else if(param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
						lstExpr.add(new FunctionHandleExpr(new Name(generateHandleName(func.getName()))));
					} else if(param.getID().compareTo(THIS) == 0) {
						//do nothing
					} else if(param.getID().compareTo(NAME) == 0) {
						lstExpr.add(new StringLiteralExpr(func.getName()));
					} else if(param.getID().compareTo(OBJ) == 0) {
						//TODO: ???
						//lstExpr.add(new FunctionHandleExpr(new Name(func.getName())));
					} else
						lstExpr.add(new CellArrayExpr());
				}

				Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
				Expr de2 = new DotExpr(de1, new Name(act.getName()));
				ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);

				Stmt call = new ExprStmt(pe);
				call.setOutputSuppressed(true);

				if(act.getType().compareTo(BEFORE) == 0) {
					func.getStmts().insertChild(call, 0);
				} else if(act.getType().compareTo(AFTER) == 0) {
					func.getStmts().addChild(call);
				} else if(act.getType().compareTo(AROUND) == 0) {
					IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
					convertToHandleFunction(func);
					aspectMatlab.ast.List<Expr> tmp = new aspectMatlab.ast.List<Expr>();
					for(Name arg : func.getInputParams()) {
						tmp.add(new NameExpr(arg));
					}
					ParameterizedExpr tmp_pe = new ParameterizedExpr(funcName, tmp);
					addSwitchCaseToAroundCorrespondingFunction(tmp_pe, fun.getNestedFunction(0), ile, EXECUTION);
					fun.incCorrespondingCount();
					func.getStmts().addChild(call);
				}
			}
		}
	}

	private static String generateHandleName(String func){
		return "AM_Handle_" + func;
	}

	private static void convertToHandleFunction(Function func){
		Function handle = new Function(func.getOutputParams(), generateHandleName(func.getName()), func.getInputParams(), new aspectMatlab.ast.List<HelpComment>(), func.getStmts(), func.getNestedFunctions());
		func.setStmtList(new aspectMatlab.ast.List<Stmt>());
		func.setNestedFunctionList(new aspectMatlab.ast.List<Function>());
		func.addNestedFunction(handle);
	}

	private static CellArrayExpr getNewVal(Expr exp){
		CellArrayExpr nv = new CellArrayExpr();

		//TODO: more kinds of newVal???
		if(exp instanceof IntLiteralExpr || exp instanceof FPLiteralExpr || exp instanceof StringLiteralExpr) {
			nv.addRow(new Row(new aspectMatlab.ast.List<Expr>().add(exp)));
		}

		return nv;
	}

	private static CellArrayExpr getDims(Expr exp){
		CellArrayExpr dims = new CellArrayExpr();

		if(exp instanceof ParameterizedExpr) {
			ParameterizedExpr pe = (ParameterizedExpr) exp;
			dims.addRow(new Row(pe.getArgs()));
		}

		return dims;
	}
}

class pattern {
	private String name;
	private String type; //TODO: complicated patterns
	private String target;
	private String dims;
	private boolean dimsAndMore;

	public pattern(String nam, String typ, String tar, String dim, boolean mor) {
		name = nam;
		type = typ;
		target = tar;
		dims = dim;
		dimsAndMore = mor;
	}

	public void setName(String nam) { name = nam; }
	public void setType(String typ) { type = typ; }
	public void setTarget(String tar) { target = tar; }
	public void setDims(String dim) { dims = dim; }
	public void setDimsAndMore(boolean mor) { dimsAndMore = mor; }

	public String getName() { return name; }
	public String getType() { return type; }
	public String getTarget() { return target; }
	public String getDims() { return dims; }
	public boolean getDimsAndMore() { return dimsAndMore; }
}

class action {
	private String name;
	private String type;
	private pattern patt;
	private Function func;
	private String className;

	public action(String nam, String typ, pattern pat, Function fun, String cName) {
		name = nam;
		type = typ;
		patt = pat;
		func = fun;
		className = cName;
	}

	public void setName(String nam) { name = nam; }
	public void setType(String typ) { type = typ; }
	public void setPattern(pattern pat) { patt = pat; }
	public void setFunction(Function fun) { func = fun; }
	public void setClassName(String name) { className = name; }

	public String getName() { return name; }
	public String getType() { return type; }
	public pattern getPattern() { return patt; }
	public Function getFunction() { return func; }
	public String getClassName() { return className; }
}