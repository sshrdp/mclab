
/*
Aspect engine class
Author: Toheed Aslam
Date: May 01, 2010
*/

package aspectMatlab;

import natlab.DecIntNumericLiteralValue;
import natlab.toolkits.analysis.varorfun.*;

import ast.*;
import ast.List;
import ast.Properties;
import beaver.Symbol;

import java.util.*;

public class AspectsEngine {

	/*
	 * Maps used for name resolution
	 */
	static Map<ASTNode, VFFlowset<String, FunctionVFDatum>> vfaMap;
	static Map<ASTNode, VFFlowset<String, VFDatum>> sfaMap;

	/*
	 * Structures used to keep aspect info
	 */
	static public LinkedList<ActionInfo> actionsList = new LinkedList<ActionInfo>();
	//static LinkedList<pattern> patternsList = new LinkedList<pattern>();
	static public Map<String, Expr> patternsListNew = new HashMap<String, Expr>();
	static public ArrayList<String> aspectList = new ArrayList<String>();

	/*
	 * Action Types
	 */
	static final public String AFTER = "after";
	static final public String BEFORE = "before";
	static final public String AROUND = "around";

	/*
	 * Patterns
	 */
	static final public String SET = "set";
	static final public String GET = "get";
	static final public String CALL = "call";
	static final public String EXECUTION = "execution";
	static final public String MAINEXECUTION = "mainexecution";
	static final public String OPERATORS = "op";
	static final public String LOOP = "loop";
	static final public String LOOPBODY = "loopbody";
	static final public String LOOPHEAD = "loophead";

	static final public String GETORCALL = "getorcall";
	static final public String GETORCALLSIMPLE = "getorcallsimple";

	/*
	 * Selectors
	 */
	static final public String ARGS = "args";
	//static final public String DIMS = "dims";
	static final public String NEWVAL = "newVal";
	static final public String OBJ = "obj";
	static final public String THIS = "this";
	static final public String NAME = "name";
	static final public String LINE = "line";
	static final public String COUNTER = "counter";
	static final public String LOC = "loc";
	static final public String FILE = "file";
	static final public String PAT = "pat";
	//Added for copy analysis
	static final public String AOBJ = "aobj";
	static final public String AINPUT = "ainput";
	static final public String AOUTPUT = "aoutput";

	/*
	 * Around Advice Arguments
	 */
	static final public Name CF_OUTPUT = new Name("varargout");
	static final public Name CF_INPUT_CASE = new Name("AM_caseNum");
	static final public Name CF_INPUT_OBJ = new Name("AM_obj");
	static final public Name CF_INPUT_AGRS = new Name("AM_args");

	/*
	 * Misc
	 */
	static final public String PROCEED_FUN_NAME = "proceed";
	static final public String GLOBAL_STRUCTURE = "AM_GLOBAL";
	static final public String LOCAL_CHECK = "AM_EntryPoint_";
	static final public String AM_CF_SCRIPT = "AM_CF_Script_";
	static final public String AM_CF_VAR = "AM_CVar_";

	////////////////////////////////////////////////////////////////////////

	/*
	 * Temp Variables Generation
	 */
	static public int correspondingCount = 0;
	public static int getCorrespondingCount() { return correspondingCount; }

	private static String generateCorrespondingVariableName(){
		return AM_CF_VAR + correspondingCount++;
	}

	/*
	 * Entry Point Variables Generation
	 */
	static public int entrypointCount = 0;
	private static String generateEntrypointCountVariableName(){
		return LOCAL_CHECK + entrypointCount++;
	}

	/*
	 * Action name generation
	 */
	private static String generateActionName(String aspect, String action){
		return aspect + "_" + action;
	}

	///////////////////////////////////////////////////////////////////////

	/*
	 * Flow Analysis
	 */
	public static void flowAnalysis(ASTNode cu){
		VFStructuralForwardAnalysis sfa = new VFStructuralForwardAnalysis(cu);
		sfa.analyze();
		sfaMap = sfa.getInFlowSets();
		//System.out.println(sfa.getInFlowSets().toString());
	}

	/*
	 * For every stmt node, check if var is function or variable
	 */
	public static VFDatum checkVarOrFunNew(Stmt node, String target){
		java.util.List<ValueDatumPair<String, VFDatum>> fset = sfaMap.get(node).toList();

		for(ValueDatumPair<String, VFDatum> vdp : fset){
			if(vdp.getValue().compareTo(target) == 0)
				return vdp.getDatum();
		}

		return new ScriptVFDatum();
	}

	///////////////////////////////////////////////////////////////////////

	/*
	 * Fetch aspect info from an aspect file
	 * and populate different structures
	 */
	//TODO
	public static void fetchAspectInfo(Program prog)
	{ 	
		Aspect aspect = (Aspect) prog;
		aspectList.add(aspect.getName());

		for(Patterns patterns : aspect.getPatterns())
			for(Pattern pattern : patterns.getPatterns())
			{
				patternsListNew.put(pattern.getName(), pattern.getPD());
			}

		for(Actions lst : aspect.getActions())
			for(AspectAction action : lst.getAspectActions())
			{
				String name = action.getName();
				String type = action.getType();
				String pattern = action.getPattern();
				String modifiedName = generateActionName(aspect.getName(), name);

				Function fun = new Function();
				if(type.compareTo(AROUND) != 0)
					fun = new Function(new ast.List<Name>(), modifiedName, action.getSelectors(), new ast.List<HelpComment>(), action.getStmts(), action.getNestedFunctions());
				else
					fun = createAroundFunction(modifiedName, action, true);

				actionsList.add(new ActionInfo(modifiedName, type, pattern, fun, aspect.getName()));
			}
	}

	/*
	 * Conversion of an aspect file to corresponding class file
	 */
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
					ast.List<Name> input = action.getSelectors();
					input.insertChild(new Name(THIS), 0);
					methods.addFunction(new Function(new ast.List<Name>(), modifiedName, input, new ast.List<HelpComment>(), action.getStmts(), action.getNestedFunctions()));
				} else
					methods.addFunction(createAroundFunction(modifiedName, action, false));
			}

		out.addMethod(methods);

		return out;
	}

	///////////////////////////////////////////////////////////////////////

	/*
	 * Expression simplification
	 * complex expressions are divided into multiple simple expressions
	 */
	public static void generateCorrespondingStmt(Expr pe) {
		if(pe.getWeavability() && !pe.getCFStmtDone()) {
			String target = pe.FetchTargetExpr();

			for(Expr pat : patternsListNew.values()) {
				if(pat.ShadowMatch(target, GETORCALLSIMPLE, -1, pe)) {
					ASTNode node = pe;
					while(node != null && !(node instanceof Stmt))
						node = node.getParent();

					//No need for ExprStmt
					if(pe.getParent() instanceof ExprStmt)
						return;

					//Multiple values return from function
					if(pe.getParent() instanceof AssignStmt){
						Expr exp = ((AssignStmt)node).getLHS();
						if(exp instanceof MatrixExpr){
							//exp.setWeavability(false);
							return;
						}

						//no need to simplify name expr as RHS
						if(pe instanceof NameExpr)
						{
							pe.setCFStmtDone(true);
							return;
						}
					}
					
					//AssignStmt inside ForStmt header
					if(node != null)
						if(node instanceof AssignStmt && node.getParent() instanceof ForStmt)
							node = node.getParent();


					if(node != null) {
						String var = generateCorrespondingVariableName();
						NameExpr lhs = new NameExpr(new Name(var));
						lhs.setWeavability(false);
						pe.setCFStmtDone(true);

						//saving the original object name
						if(pe instanceof NameExpr)
							lhs.setInputParamName(target);

						AssignStmt stmt = new AssignStmt((Expr) lhs.copy(), (Expr) pe.copy());
						stmt.setOutputSuppressed(true);
						stmt.setLineNum(((Stmt)node).getLineNum());

						int ind = pe.getParent().getIndexOfChild(pe);
						pe.getParent().setChild(lhs, ind);

						ind = node.getParent().getIndexOfChild(node);
						if(ind == -1){
							
						}else{
							node.getParent().insertChild(stmt, ind);
						}
						
						
						
						//Condition of WhileStmt
						if(node instanceof WhileStmt){
							WhileStmt ws = (WhileStmt)node;
							ws.getStmts().add(stmt);
							ws.WeaveLoopStmts(stmt, true);
						}
					}			
					return;
				}
			}

			//No match
			pe.setWeavability(false);
		}
	}

	/*
	 * Assignment statements generation for function input params
	 */
	//public static void generateCorrespondingStmt(List<Stmt> stmts, List<Name> input) {
	public static void generateCorrespondingStmt(Function func) {
		
		List<Stmt> stmts = func.getStmts();
		List<Name> input = func.getInputParams();

		func.setInputParamNames(input.copy());

		for(int i = input.getNumChild()-1; i>= 0; i--) {
			Name pe = input.getChild(i);
			String target = pe.getID();

			for(Expr pat : patternsListNew.values()) {
				if(pat.ShadowMatch(target, SET, -1, stmts)) {
					String var = generateCorrespondingVariableName();
					Expr rhs = new NameExpr(new Name(var));
					rhs.setWeavability(false);

					AssignStmt stmt = new AssignStmt(new NameExpr(pe), rhs);
					stmt.setOutputSuppressed(true);
					stmt.setLineNum(Symbol.getLine(input.getStart()));

					stmts.insertChild(stmt, 0);
					input.setChild(new Name(var), i);
					break;
				}
			}
		}
	}

	/*
	 * Generate a function corresponding to an around action
	 */
	private static Function createAroundFunction(String modifiedName, AspectAction action, boolean isConvertProceed){
		ast.List<Name> output = new ast.List<Name>();
		output.add(CF_OUTPUT);

		ast.List<Name> input = action.getSelectors();	
		ast.List<Function> nf = action.getNestedFunctions();
		ast.List<Stmt> stmts = action.getStmts();

		if(isConvertProceed) {
			input = new List<Name>();
			input.add(CF_INPUT_CASE);
			input.add(CF_INPUT_OBJ);
			input.add(CF_INPUT_AGRS);

			input.add(new Name(ARGS));
			input.add(new Name(COUNTER));
			input.add(new Name(FILE));
			input.add(new Name(LINE));
			input.add(new Name(LOC));
			input.add(new Name(NAME));
			input.add(new Name(NEWVAL));
			input.add(new Name(OBJ));
			input.add(new Name(PAT));

			input.add(new Name(AOBJ));
			input.add(new Name(AINPUT));
			input.add(new Name(AOUTPUT));

			action.setSelectorList(input);

			convertProceedCalls(action.getStmts());
			Function pf = createCorrespondingFunction(PROCEED_FUN_NAME, true);
			pf.setOutputParamList(new List<Name>());
			nf.add(pf);

			GlobalStmt gs = new GlobalStmt(new ast.List<Name>().add(new Name(GLOBAL_STRUCTURE)));
			gs.setOutputSuppressed(true);
			stmts.insertChild(gs, 0);
		} else
			input.insertChild(new Name(THIS), 0);

		return new Function(output, modifiedName, input, new ast.List<HelpComment>(), stmts, nf);
	}

	private static Function createCorrespondingFunction(String funName, boolean isScript){
		ast.List<Name> output = new ast.List<Name>();
		output.add(CF_OUTPUT);

		ast.List<Name> input = new ast.List<Name>();
		input.add(CF_INPUT_CASE);
		if(isScript)
			input.add(CF_INPUT_OBJ);
		input.add(CF_INPUT_AGRS);

		SwitchStmt ss = new SwitchStmt(new NameExpr(CF_INPUT_CASE), new ast.List<SwitchCaseBlock>(), new Opt<DefaultCaseBlock>());
		ast.List<Stmt> sl = new ast.List<Stmt>();
		sl.add(ss);

		return new Function(output, funName, input, new ast.List<HelpComment>(), sl, new ast.List<Function>());
	}

	/*
	 * proceed calls in around advice are transformed
	 */
	private static void convertProceedCalls(ast.List<Stmt> stmts){
		for(Stmt stmt : stmts) {
			stmt.ProceedTransformation();
		}
	}

	public static void transformProceedCall(Expr pe){
		if(pe.FetchTargetExpr().compareTo("proceed") == 0) {
			ast.List<Expr> input = new ast.List<Expr>();
			input.add(new NameExpr(CF_INPUT_CASE));
			input.add(new NameExpr(CF_INPUT_OBJ));
			input.add(new NameExpr(CF_INPUT_AGRS));

			ParameterizedExpr call = new ParameterizedExpr(new NameExpr(new Name(PROCEED_FUN_NAME)), input);
			int ind = pe.getParent().getIndexOfChild(pe);
			pe.getParent().setChild(call, ind);
		}
	}


	/*
	 * For each join point of an around action,
	 * the join point is moved into a switch statement.
	 * Switch statement is part of around action proceed.
	 */
	private static Expr addSwitchCaseToAroundCorrespondingFunction(Expr lhs, Expr pe, Function corFun, IntLiteralExpr ile, String type, boolean aroundExist, Expr prevCase, String classname){
		ast.List<Stmt> scsl = new ast.List<Stmt>();
		ast.List<Expr> input = new ast.List<Expr>();
		Expr exp = null;
		
		if(!aroundExist){
			exp = (Expr) pe.copy();
			Expr tmp = (Expr) pe.copy();

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
					input.add(new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));

				if(exp instanceof ParameterizedExpr)
					exp = new ParameterizedExpr(((ParameterizedExpr)exp).getTarget(), input);
				else if(exp instanceof CellIndexExpr)
					exp = new CellIndexExpr(((CellIndexExpr)exp).getTarget(), input);
			}

			AssignStmt tmpStmt = new AssignStmt();
			if(type.compareTo(SET) == 0 || type.compareTo(LOOPHEAD) == 0) {
				exp = new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1)))));
			} else if(type.compareTo(EXECUTION) == 0 || type.compareTo(CALL) == 0) {
				exp = new ParameterizedExpr(new NameExpr(CF_INPUT_OBJ), input);
			} else if(type.compareTo(GET) == 0 || type.compareTo(OPERATORS) == 0) {
				tmpStmt = new AssignStmt(tmp, new NameExpr(CF_INPUT_OBJ));
				tmpStmt.setOutputSuppressed(true);
				tmpStmt.setWeavability(false, true);
				scsl.add(tmpStmt);
			}
		} else {
			input.add(new NameExpr(new Name(THIS)));
			input.add(ile);
			input.add(new NameExpr(CF_INPUT_OBJ));
			input.add(new NameExpr(CF_INPUT_AGRS));

			input.add(new NameExpr(new Name(ARGS)));
			input.add(new NameExpr(new Name(COUNTER)));
			input.add(new NameExpr(new Name(FILE)));
			input.add(new NameExpr(new Name(LINE)));
			input.add(new NameExpr(new Name(LOC)));
			input.add(new NameExpr(new Name(NAME)));
			input.add(new NameExpr(new Name(NEWVAL)));
			input.add(new NameExpr(new Name(OBJ)));
			input.add(new NameExpr(new Name(PAT)));

			input.add(new NameExpr(new Name(AOBJ)));
			input.add(new NameExpr(new Name(AINPUT)));
			input.add(new NameExpr(new Name(AOUTPUT)));

			Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(classname));
			Expr de2 = new DotExpr(de1, new Name(((Function)(corFun.getParent().getParent())).getName()));
			exp = new ParameterizedExpr(de2, input); 
		}
		Expr out = null;
		if(lhs instanceof MatrixExpr){
			MatrixExpr me = (MatrixExpr)lhs;
			int size = me.getRow(0).getNumElement();
			List<Expr> lst = new List<Expr>();

			for(int i=1; i<=size; i++)
				lst.add(new CellIndexExpr(new NameExpr(CF_OUTPUT), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));
			out = new MatrixExpr(new List<Row>().add(new Row(lst)));
		} else {
			out = new CellIndexExpr(new NameExpr(CF_OUTPUT), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1)))));
		}

		if(aroundExist){
			int ind = prevCase.getParent().getIndexOfChild(prevCase);
			Expr tmp = (Expr) prevCase.copy();
			prevCase.getParent().setChild(exp.copy(), ind);
			exp = tmp;
		}
		Stmt stmt = new AssignStmt(out, exp);
		if(lhs == null) //ExprStmt case
			stmt = new ExprStmt(exp);
		stmt.setOutputSuppressed(true);
		scsl.add(stmt);
		SwitchCaseBlock scb = new SwitchCaseBlock(ile, scsl);
		SwitchStmt ss = (SwitchStmt) corFun.getStmt(0);
		ss.addSwitchCaseBlock(scb);
		return exp;
	}

	/*
	 * FOR statement transformation
	 * assignment statement in the header is moved out of loop 
	 */
	public static void transformForStmt(ast.List<Stmt> stmts)
	{
		int stmtCount = stmts.getNumChild();

		for(int s=0; s<stmtCount; s++)
		{
			if(stmts.getChild(s) instanceof ForStmt) {
				ForStmt fs = (ForStmt) stmts.getChild(s);
				if( !fs.isAspectTransformed() ) {
					AssignStmt as_old = fs.getAssignStmt();
					Expr lhs = as_old.getLHS();
					Expr rhs = as_old.getRHS();

					String tmpAS = "AM_" + "tmpAS_";
					String tmpFS = "AM_" + "tmpFS_";

					if(lhs instanceof NameExpr){
						NameExpr ne = (NameExpr) lhs;
						tmpAS += ne.getName().getID();
						tmpFS += ne.getName().getID();
					}

					AssignStmt as_out = new AssignStmt();
					as_out.setRHS(rhs);
					as_out.setLHS(new NameExpr(new Name(tmpAS)));
					as_out.setOutputSuppressed(true);
					as_out.getLHS().setWeavability(false);
					as_out.setLineNum(fs.getLineNum());

					AssignStmt as_for = new AssignStmt();
					//as_for.setRHS(new RangeExpr(new IntLiteralExpr(new DecIntNumericLiteralValue("1")), new Opt<Expr>(), new ParameterizedExpr(new NameExpr(new Name("numel")), new ast.List<Expr>().add(as_out.getLHS()))));
					as_for.setRHS(new RangeExpr(new IntLiteralExpr(new DecIntNumericLiteralValue("1")), new Opt<Expr>(), new ParameterizedExpr(new NameExpr(new Name("length")), new ast.List<Expr>().add(new ParameterizedExpr(as_out.getLHS(), new ast.List<Expr>().add(new ColonExpr()).add(new ColonExpr()))))));
					as_for.setLHS(new NameExpr(new Name(tmpFS)));
					as_for.setWeavability(false, true);

					AssignStmt as_in = new AssignStmt();
					//as_in.setRHS(new ParameterizedExpr(new NameExpr(new Name(tmpAS)), new ast.List<Expr>().add(new NameExpr(new Name(tmpFS)))));
					as_in.setRHS(new ParameterizedExpr(new NameExpr(new Name(tmpAS)), new ast.List<Expr>().add(new ColonExpr()).add(new NameExpr(new Name(tmpFS)))));
					as_in.setLHS(as_old.getLHS());
					as_in.setOutputSuppressed(true);
					as_in.getRHS().setWeavability(false);
					as_in.setLineNum(fs.getLineNum());

					ast.List<Stmt> lstFor = new ast.List<Stmt>();
					lstFor.add(as_in);
					for(Stmt stmt : fs.getStmts())
						lstFor.add(stmt);

					ForStmt fs_new = new ForStmt();
					fs_new.setAssignStmt(as_for);
					fs_new.setStmtList(lstFor);
					fs_new.setAspectTransformed(true);
					fs_new.setLineNum(fs.getLineNum());

					fs_new.setLoopVar(lhs.FetchTargetExpr());
					as_out.setLoopBound(true);
					as_out.setBoundLoop(fs_new);
					fs_new.setLoopHead(as_out);

					stmts.removeChild(s);
					stmts.insertChild(as_out, s);
					stmts.insertChild(fs_new, s+1);
					s++;
					stmtCount++;
				}
			}
		}
	}
	public static void simplifyBinaryExpr(BinaryExpr current,ast.List<Stmt> stmtList,int stmtPos){
		//make the temp expression
		String tmpBE = "AM_" + "tmpBE_";
		tmpBE+= current.getId();
	
	
	
		NameExpr tmpBEnExpr = new NameExpr(new Name(tmpBE));

	
		ASTNode<ASTNode> parentNode = current.getParent();
	
	
		AssignStmt parentaStmt;
	
		//Determine the nature of the parent of the current BinaryExpr, and take the appropriate measure to simplify the later.
		if(parentNode instanceof AssignStmt){
			parentaStmt = (AssignStmt)parentNode;

			parentaStmt.setRHS(tmpBEnExpr);

			stmtList.setChild(parentaStmt, stmtPos);

		}else if(parentNode instanceof ast.List){
			ast.List<ASTNode> parentaList = (ast.List<ASTNode>)parentNode;
			parentaList.setChild(tmpBEnExpr,parentaList.getIndexOfChild(current) );
		}else{
			if(parentNode instanceof BinaryExpr){
				parentNode.setChild(tmpBEnExpr,parentNode.getIndexOfChild(current));
			}
		
		}//if-else
	
		AssignStmt tmpBEaStmt = new AssignStmt();
	
		tmpBEaStmt.setLHS(tmpBEnExpr);
		tmpBEaStmt.setRHS(current);
		stmtList.insertChild(tmpBEaStmt,stmtPos);
		return;
	}
	
	
	
	
	
	public static void weaveBinaryExpr(BinaryExpr current,ActionInfo act,ast.List<Stmt> stmtList,int stmtPos){
		boolean aroundExist = false;
		int acount = 0, bcount = 0, tcount= 0;
		Expr prevCase = null;
		Function fun = act.getFunction();
		ast.List<Expr> lstExpr = new ast.List<Expr>();
		
		//We assume the simplification left us with a simple Bexpr
		Expr lhs = (Expr)current.getLHS();
		Expr rhs = (Expr)current.getRHS();
		

		for(Name param : fun.getInputParams()) {
			if(param.getID().compareTo(ARGS) == 0) {
				lstExpr.add(getDims(rhs));
			} else if(param.getID().compareTo(THIS) == 0) {
				//do nothing
			} else if(param.getID().compareTo(NAME) == 0) {
				lstExpr.add(new StringLiteralExpr(act.getName()));
			} else if(param.getID().compareTo(OBJ) == 0) {
				//name of the variable passed.
				CellArrayExpr lstobjarg = new CellArrayExpr();
				Row nameRow = new Row();
				nameRow.addElement(new StringLiteralExpr(lhs.getPrettyPrintedLessComments()));
				nameRow.addElement(new StringLiteralExpr(rhs.getPrettyPrintedLessComments()));
				lstobjarg.addRow(nameRow);
				lstExpr.add(lstobjarg);
				
			} else if(param.getID().compareTo(LINE) == 0) {
				lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(stmtPos))));
			} else if(param.getID().compareTo(LOC) == 0) {
				lstExpr.add(new StringLiteralExpr(getLocation(stmtList.getChild(stmtPos))));
			} else if(param.getID().compareTo(FILE) == 0) {
				lstExpr.add(new StringLiteralExpr(getFileName(stmtList.getChild(stmtPos))));
			} else if(param.getID().compareTo(PAT) == 0) {
				lstExpr.add(new StringLiteralExpr(OPERATORS));
			} else if(param.getID().compareTo(AINPUT) == 0) {
				CellArrayExpr lstargs = new CellArrayExpr();
				Row row = new Row();
				
				row.addElement(lhs);
				row.addElement(rhs);
				

				lstargs.addRow(row);
				lstExpr.add(lstargs);
			} else
				lstExpr.add(new CellArrayExpr());
		}

		Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
		Expr de2 = new DotExpr(de1, new Name(act.getName()));
		ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
		pe.setWeavability(false);

		Stmt action = new ExprStmt(pe);
		action.setOutputSuppressed(true);

		Stmt call = action;

		if(act.getType().equals(BEFORE)) {
			stmtList.insertChild(call, stmtPos-1);
			stmtPos++;
			
		} else if(act.getType().equals(AFTER)) {
			stmtList.insertChild(call, stmtPos);
		} else if(act.getType().equals(AROUND)) {
			IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
			prevCase = addSwitchCaseToAroundCorrespondingFunction(null, rhs, fun.getNestedFunction(0), ile, OPERATORS, aroundExist, prevCase, act.getClassName());
			fun.incCorrespondingCount();
			stmtList.setChild(call, stmtPos-1);
			aroundExist = true;
		}
	}
	/*
	 * Match,simplify and weave binary expression.
	 * 
	 */
	public static void transformBinaryExpr(BinaryExpr current,ast.List<Stmt> stmtList,int stmtPos){
		//only if matched.
	for(int j=0; j<actionsList.size(); j++){
		ActionInfo act = actionsList.get(j);
		/*
		System.err.println("Name: "+act.getName());
		System.err.println("Pattern: "+act.getPattern());
		System.err.println("Type"+act.getType());
		System.err.println("Class"+act.getClass());
		System.err.println("Classname"+act.getClassName());
		*/
		
		 if(patternsListNew.containsKey(act.getPattern())){
			PatternDesignator thisIsNew = (PatternDesignator)patternsListNew.get(act.getPattern());
			
			if("op".contentEquals(thisIsNew.getName())){

				String unparsedClass = current.getClass().toString();
		 		String parsedClass = unparsedClass.replaceAll("class ast.","");
				for(Name foo:thisIsNew.getArgList()){

					if(foo.getPrettyPrinted().contentEquals(parsedClass) || foo.getPrettyPrinted().contentEquals("opall") ){
						
						if(current.getLHS() instanceof BinaryExpr && !act.getType().equals(AFTER))
							simplifyBinaryExpr((BinaryExpr)current.getLHS(),stmtList,stmtPos++);
						if(current.getRHS() instanceof BinaryExpr && !act.getType().equals(AFTER))
							simplifyBinaryExpr((BinaryExpr)current.getRHS(),stmtList,stmtPos++);			
						simplifyBinaryExpr(current,stmtList,stmtPos++);
						weaveBinaryExpr(current,act,stmtList,stmtPos);
						
						}
					}
				}

				
			}
			
		}
	
	}
	
	
	
	/*
	 * WHILE statement transformation
	 * Condition of loop is moved out of loop
	 * Additionally, condition is placed on all the edges back into header
	 */
	public static void transformWhileStmt(ast.List<Stmt> stmts)
	{
		int stmtCount = stmts.getNumChild();

		for(int s=0; s<stmtCount; s++)
		{
			if(stmts.getChild(s) instanceof WhileStmt) {
				WhileStmt ws = (WhileStmt) stmts.getChild(s);
				if( !ws.isAspectTransformed() ) {
					String var = generateCorrespondingVariableName();

					Expr rhs = ws.getExpr();
					Expr lhs = new NameExpr(new Name(var));

					ws.setLoopVars(rhs.FetchTargetExpr());

					if(!(rhs instanceof NameExpr))
						rhs.aspectsCorrespondingFunctions();

					AssignStmt as = new AssignStmt(lhs, rhs);
					as.setOutputSuppressed(true);
					lhs.setWeavability(false);
					as.setLineNum(ws.getLineNum());

					ws.setExpr(lhs);
					stmts.insertChild(as, stmts.getIndexOfChild(ws));

					as.setLoopBound(true);
					as.setBoundLoop(ws);
					ws.setLoopHead(as);

					ws.getStmtList().add(ws.getLoopHead());
					ws.WeaveLoopStmts(ws.getLoopHead(), true);

					ws.setAspectTransformed(true);
					s++;
					stmtCount++;
				}
			}
		}
	}

	/*
	 * Post-processing phase
	 * a global structure containing aspect objects
	 * is placed at top of all files with appropriate conditions
	 */
	public static void weaveGlobalStructure(CompilationUnits cu) {
		for(int i=0; i < cu.getNumProgram(); i++) {
			ast.List<Stmt> stmts = new ast.List<Stmt>();
			GlobalStmt gs = new GlobalStmt(new ast.List<Name>().add(new Name(GLOBAL_STRUCTURE)));
			gs.setOutputSuppressed(true);
			stmts.add(gs);

			ast.List<Stmt> lstbefore = new ast.List<Stmt>();
			for(String s : aspectList){
				Expr rhs = new NameExpr(new Name(s));
				Expr lhs = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(s));
				AssignStmt as = new AssignStmt(lhs, rhs);
				as.setOutputSuppressed(true);
				as.setWeavability(false, true);

				lstbefore.add(as);
			}

			ast.List<Stmt> lstafter = new ast.List<Stmt>();

			Program p = cu.getProgram(i);
			weaveMain(p, lstbefore, lstafter);

			Expr nrhs = new MatrixExpr();
			Expr nlhs = new NameExpr(new Name(GLOBAL_STRUCTURE));
			AssignStmt nas = new AssignStmt(nlhs, nrhs);
			nas.setOutputSuppressed(true);
			nas.setWeavability(false, true);
			lstafter.add(nas);

			if(!cu.getEntryPoint()) {
				String var = generateEntrypointCountVariableName();
				Expr oirhs = new IntLiteralExpr(new DecIntNumericLiteralValue("1"));
				Expr oerhs = new IntLiteralExpr(new DecIntNumericLiteralValue("0"));
				Expr olhs = new NameExpr(new Name(var));
				AssignStmt oias = new AssignStmt(olhs, oirhs);
				AssignStmt oeas = new AssignStmt(olhs, oerhs);
				oias.setOutputSuppressed(true);
				oias.setWeavability(false, true);
				oeas.setOutputSuppressed(true);
				oeas.setWeavability(false, true);

				ast.List<Expr> elst = new ast.List<Expr>().add(new NameExpr(new Name(GLOBAL_STRUCTURE)));
				ParameterizedExpr ocond = new ParameterizedExpr(new NameExpr(new Name("isempty")), elst);
				IfBlock oib = new IfBlock(ocond, lstbefore.add(oias));
				ElseBlock oeb = new ElseBlock(new ast.List<Stmt>().add(oeas));
				Stmt ois = new IfStmt(new ast.List<IfBlock>().add(oib), new Opt<ElseBlock>(oeb));
				stmts.add(ois);

				Expr ncond = new NameExpr(new Name(var));
				IfBlock nib = new IfBlock(ncond, lstafter);
				Stmt nis = new IfStmt(new ast.List<IfBlock>().add(nib), new Opt<ElseBlock>());

				p.weaveGlobalStructure(stmts, nis);
			} else {
				if(p.getEntryPoint()){
					p.weaveGlobalStructureWithEntry(gs, lstbefore, lstafter);
				} else {
					p.weaveGlobalStructureWithEntry(gs, new ast.List<Stmt>(), new ast.List<Stmt>());
				}
			}
		}
	}

	/*
	 * Matching & Weaving
	 * Expression, Assignment, FOR and WHILE statements
	 */ 
	public static void weaveStmts(ast.List<Stmt> stmts)
	{
		ASTNode node = stmts;
		while(node != null && !(node instanceof Script))
			node = node.getParent();

		int stmtCount = stmts.getNumChild();

		for(int s=0; s<stmtCount; s++)
		{
			Stmt stmt = stmts.getChild(s);
			if(stmt instanceof ExprStmt) {
				ExprStmt es = ((ExprStmt)stmt);
				Expr rhs = es.getExpr();
				int count = 0;
				String varName = "";

				if(rhs.getWeavability()) {
					varName = rhs.FetchTargetExpr();
					if(varName.compareTo("") != 0)  {
						StringTokenizer st = new StringTokenizer(varName, ",");
						while (st.hasMoreTokens()) {
							String target = st.nextToken();
							count += exprMatchAndWeave(stmts, stmts.getIndexOfChild(stmt), target, es, checkVarOrFunNew(es, target), node != null ? true:false);
						}
					}
				}

				s += count;
				stmtCount += count;
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
							//StringTokenizer st = new StringTokenizer(varName, ",");
							//while (st.hasMoreTokens()) {
							//	count += setMatchAndWeave(stmts, stmts.getIndexOfChild(as), st.nextToken(), as);
							//}
							count += setMatchAndWeave(stmts, stmts.getIndexOfChild(as), varName, as);
						}
					}

					s += count;
					stmtCount += count;
					count = 0;

					if(rhs.getWeavability()) {
						varName = rhs.FetchTargetExpr();
						if(varName.compareTo("") != 0)  {
							StringTokenizer st = new StringTokenizer(varName, ",");
							while (st.hasMoreTokens()) {
								
								String target = st.nextToken();
								
								count += getOrCallMatchAndWeave(stmts, stmts.getIndexOfChild(as), target, as, checkVarOrFunNew(as, target), node != null ? true:false);
							}
						}
					}

					s += count;
					stmtCount += count;
				}
			} else if(stmt instanceof ForStmt || stmt instanceof WhileStmt){

				stmt.aspectsWeave();

				int count = weaveLoops(stmt);
				s += count;
				stmtCount += count;

			} else {
				stmt.aspectsWeave();
			}
		}
	}

	/*
	 * Matching & Weaving
	 * SET: lhs of assignment statement
	 * 
	 */
	private static int setMatchAndWeave(ast.List<Stmt> stmts, int s, String varName, AssignStmt context) {
		Expr rhs = context.getRHS();
		Expr lhs = context.getLHS();
		int args = lhs.FetchArgsCount();
		int acount = 0, bcount = 0, tcount= 0;
		boolean aroundExist = false;
		Expr prevCase = null;

		StringTokenizer st = new StringTokenizer(varName, ",");
		int tokens = st.countTokens();
		
		if(rhs instanceof BinaryExpr){
			bExprMatchAndWeave(stmts, s , varName,(BinaryExpr) rhs, checkVarOrFunNew(context,"target") , false);
			
		}
		while (st.hasMoreTokens()) {
			String target = st.nextToken();

			for(int j=0; j<actionsList.size(); j++)
			{
				ActionInfo act = actionsList.get(j);
				 if(patternsListNew.containsKey(act.getPattern()) 
						&& patternsListNew.get(act.getPattern()).ShadowMatch(target, SET, args, context)
				){
					Function fun = act.getFunction();

					if(act.getType().compareTo(AROUND) == 0 && tokens > 1) {
						System.out.println("Skipped Action "+act.getName()+": Around actions on setting the matrix expression are not allowed!");
						continue;
					}

					if(act.getType().compareTo(AROUND) == 0 && aroundExist) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(lhs, rhs, fun.getNestedFunction(0), ile, SET, aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						continue;
					}

					ast.List<Expr> lstExpr = new ast.List<Expr>();
					Expr nv = null;
					boolean objExist = false;

					for(Name param : fun.getInputParams()) {
						if(param.getID().compareTo(NEWVAL) == 0 || param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
							if(nv == null){
								if(rhs instanceof IntLiteralExpr || rhs instanceof FPLiteralExpr || rhs instanceof StringLiteralExpr) {
									nv = rhs;
									//} else if(rhs.getWeavability() && !rhs.getCFStmtDone()) {
								} else if(rhs.getWeavability()) {
									String var = generateCorrespondingVariableName();
									Expr tmp = new NameExpr(new Name(var));

									AssignStmt stmt = new AssignStmt((Expr) tmp.copy(), (Expr) rhs.copy());
									stmt.setOutputSuppressed(true);

									tmp.setWeavability(false);
									rhs.setWeavability(false);
									rhs.setCFStmtDone(true);

									int ind = context.getIndexOfChild(rhs);
									context.setChild(tmp, ind);

									ind = context.getParent().getIndexOfChild(context);
									context.getParent().insertChild(stmt, ind);

									nv = tmp;
									tcount = 1;
								} else if(!rhs.getWeavability()){
									nv = rhs;
								}
							}

							if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0){
								CellArrayExpr cae = new CellArrayExpr();
								Row row = new Row();
								row.addElement(nv);
								cae.addRow(row);
								lstExpr.add(cae);
							} else
								lstExpr.add(nv);
						} else if(param.getID().compareTo(ARGS) == 0) {
							lstExpr.add(getDims(lhs));
						} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
							IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
							lstExpr.add(ile);
						} else if(param.getID().compareTo(OBJ) == 0 || param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
							objExist = true;
							lstExpr.add(new NameExpr(new Name(target)));
						} else if(param.getID().compareTo(THIS) == 0) {
							//do nothing
						} else if(param.getID().compareTo(NAME) == 0) {
							lstExpr.add(new StringLiteralExpr(target));
						} else if(param.getID().compareTo(LINE) == 0) {
							lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(context.getLineNum()))));
						} else if(param.getID().compareTo(LOC) == 0) {
							lstExpr.add(new StringLiteralExpr(getLocation(context)));
						} else if(param.getID().compareTo(FILE) == 0) {
							lstExpr.add(new StringLiteralExpr(getFileName(context)));
						} else if(param.getID().compareTo(PAT) == 0) {
							lstExpr.add(new StringLiteralExpr(SET));
						} else if(param.getID().compareTo(AOBJ) == 0) {
							String aobj = "";
							if(rhs.getWeavability() && rhs instanceof NameExpr)
								aobj = ((NameExpr)rhs).getName().getID();
							lstExpr.add(new StringLiteralExpr(aobj));
						} else
							lstExpr.add(new CellArrayExpr());
					}

					Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
					Expr de2 = new DotExpr(de1, new Name(act.getName()));
					ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
					pe.setWeavability(false);

					Stmt action;
					if(act.getType().compareTo(AROUND) == 0)
						action = new AssignStmt(lhs, pe);
					else
						action = new ExprStmt(pe);

					Stmt call = action;
					action.setOutputSuppressed(true);

					Stmt existCheck = null;
					if(objExist){
						ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
						elst.add(new StringLiteralExpr("var"));
						ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);

						BinaryExpr cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
						AssignStmt tmp = new AssignStmt(new NameExpr(new Name(target)), new MatrixExpr());
						tmp.setWeavability(false, true);
						tmp.setOutputSuppressed(true);
						IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(tmp));

						existCheck = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());
					}

					if(act.getType().compareTo(BEFORE) == 0) {
						stmts.insertChild(call, s+tcount+bcount);
						if(existCheck != null){
							stmts.insertChild(existCheck, s+tcount+bcount);
							tcount += 1;
						}
						bcount += 1;
					} else if(act.getType().compareTo(AFTER) == 0) {
						stmts.insertChild(call, s+acount+bcount+tcount+1);
						acount += 1;
					} else if(act.getType().compareTo(AROUND) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(lhs, rhs, fun.getNestedFunction(0), ile, SET, aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						stmts.setChild(call, s+bcount+tcount);
						if(existCheck != null){
							stmts.insertChild(existCheck, s+bcount+tcount);
							tcount += 1;
						}

						aroundExist = true;
					}
				}
			}
		} 

		return acount + bcount + tcount;
	}

	/*
	 * Matching & Weaving
	 * GETorCALL: rhs of assignment statement
	 */
	private static int getOrCallMatchAndWeave(ast.List<Stmt> stmts, int s, String target, AssignStmt context, VFDatum varOrFun, boolean isScript) {
		Expr rhs = context.getRHS();
		
		Expr lhs = context.getLHS();
		int acount = 0, bcount = 0, tcount = 0;
		int args = rhs.FetchArgsCount();
		boolean aroundExist = false;
		Expr prevCase = null;
		boolean isExistCheck = false;
		Expr existObj = null;
		String pattern = "";
		for(int j=0; j<actionsList.size(); j++)
		{
			
			ActionInfo act = actionsList.get(j);

			boolean isGet = false, isCall = false, isOp = false;
			
			if(patternsListNew.containsKey(act.getPattern())) {
				Expr pat = patternsListNew.get(act.getPattern());
				 if(varOrFun.isVariable() && !(rhs instanceof BinaryExpr)) { //only match if target is variable\
					isGet = pat.ShadowMatch(target, GET, args, context);
					pattern = GET;
				} else if(varOrFun.isFunction() || (varOrFun.isBottom() && !isScript)) {
					isCall = pat.ShadowMatch(target, CALL, args, context);
					pattern = CALL;
				} else if(varOrFun.isBottom() && isScript) {
					isGet = pat.ShadowMatch(target, GET, args, context);
					isCall = pat.ShadowMatch(target, CALL, args, context);

					if(isGet && (isCall || isOp) && !isExistCheck) {
						ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
						elst.add(new StringLiteralExpr("var"));
						ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);
						BinaryExpr cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));

						String var = generateCorrespondingVariableName();
						existObj = new NameExpr(new Name(var));

						AssignStmt ias = new AssignStmt(existObj, new NameExpr(new Name(target)));
						ias.setOutputSuppressed(true);
						ias.setWeavability(false, true);
						AssignStmt eas = new AssignStmt(existObj, new ParameterizedExpr(new NameExpr(new Name("eval")), new List<Expr>().add(new StringLiteralExpr("@"+target))));
						eas.setOutputSuppressed(true);
						eas.setWeavability(false, true);

						IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(ias));
						ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
						Stmt existCheck = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));

						int ind = context.getParent().getIndexOfChild(context);
						context.getParent().insertChild(existCheck, ind);

						isExistCheck = true;
						tcount = 1;
					}
				} //else


				if(isGet || isCall ||isOp ) {
					Function fun = act.getFunction();
					ast.List<Expr> lstExpr = new ast.List<Expr>();

					if(act.getType().compareTo(AROUND) == 0 && aroundExist) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(lhs, rhs, fun.getNestedFunction(0), ile, isGet ? GET:CALL, aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						continue;
					}

					for(Name param : fun.getInputParams()) {
						if(param.getID().compareTo(ARGS) == 0) {
							lstExpr.add(getDims(rhs));
						} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
							IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
							lstExpr.add(ile);
						} else if(param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
							if(isCall && !isGet){
								if(!isScript)
									lstExpr.add(new FunctionHandleExpr(new Name(target)));
								else if(isScript)	
									lstExpr.add(new ParameterizedExpr(new NameExpr(new Name("eval")), new List<Expr>().add(new StringLiteralExpr("@"+target))));
							}
							else if(isGet && !isCall)
								lstExpr.add(new NameExpr(new Name(target)));
							else if(isGet && isCall)
								lstExpr.add(existObj);
						} else if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
							lstExpr.add(getDims(rhs));
						} else if(param.getID().compareTo(THIS) == 0) {
							//do nothing
						} else if(param.getID().compareTo(NAME) == 0) {
							lstExpr.add(new StringLiteralExpr(target));
						} else if(param.getID().compareTo(OBJ) == 0) {
							if(isGet && !isCall)
								lstExpr.add(new NameExpr(new Name(target)));
							else
								lstExpr.add(new MatrixExpr());
						} else if(param.getID().compareTo(LINE) == 0) {
							lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(context.getLineNum()))));
						} else if(param.getID().compareTo(LOC) == 0) {
							lstExpr.add(new StringLiteralExpr(getLocation(context)));
						} else if(param.getID().compareTo(FILE) == 0) {
							lstExpr.add(new StringLiteralExpr(getFileName(context)));
						} else if(param.getID().compareTo(PAT) == 0) {
							lstExpr.add(new StringLiteralExpr(pattern));
						} else if(param.getID().compareTo(AINPUT) == 0) {
							CellArrayExpr lstargs = new CellArrayExpr();
							Row row = new Row();

							if(rhs instanceof ParameterizedExpr){
								ParameterizedExpr trhs = (ParameterizedExpr)rhs;
								for(Expr arg : trhs.getArgs()) {
									if(arg instanceof NameExpr){
										NameExpr tne = (NameExpr)arg;
										if(tne.getWeavability())
											row.addElement(new StringLiteralExpr(tne.getName().getID()));
										else
											row.addElement(new StringLiteralExpr(tne.getInputParamName()));
									} else
										row.addElement(new StringLiteralExpr(""));
								}
							}

							lstargs.addRow(row);
							lstExpr.add(lstargs);
						} else
							lstExpr.add(new CellArrayExpr());
					}

					Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
					Expr de2 = new DotExpr(de1, new Name(act.getName()));
					ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
					pe.setWeavability(false);

					Stmt action;
					if(act.getType().compareTo(AROUND) == 0)
						action = new AssignStmt(lhs, pe);
					else
						action = new ExprStmt(pe);

					action.setOutputSuppressed(true);
					Stmt call = action;

					if(!(isCall && isGet) && varOrFun.isBottom() && isScript) { //script case
						BinaryExpr cond;
						ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
						elst.add(new StringLiteralExpr("var"));
						ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);

						if(isGet)
							cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
						else
							cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));

						IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(action));
						Stmt outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

						if(act.getType().compareTo(AROUND) == 0) {					
							AssignStmt eas = new AssignStmt(lhs, rhs);
							eas.setOutputSuppressed(true);
							ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
							outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
						}

						call = outerIf;
					}

					if(act.getType().compareTo(BEFORE) == 0) {
						stmts.insertChild(call, s+bcount+tcount);
						bcount += 1;
					} else if(act.getType().compareTo(AFTER) == 0) {
						stmts.insertChild(call, s+bcount+acount+tcount+1);
						acount += 1;
					} else if(act.getType().compareTo(AROUND) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(lhs, rhs, fun.getNestedFunction(0), ile, isGet ? GET:CALL, aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						stmts.setChild(call, s+bcount+tcount);

						if(context.getLoopBound())
						{
							Stmt loop = context.getBoundLoop();
							if(loop instanceof ForStmt)
								((ForStmt)loop).setLoopHead(call);
							else if(loop instanceof WhileStmt)
								((WhileStmt)loop).setLoopHead(call);
						}

						aroundExist = true;
					}
				}
			}
		}

		return acount + bcount + tcount;
	}

	
	/*
	 * Matching & Weaving
	 * OP: binaryExpr 
	 * 
	 */
	private static int bExprMatchAndWeave(ast.List<Stmt> stmts, int s, String target, BinaryExpr context, VFDatum varOrFun, boolean isScript){
		return 0;
	}
	/*
	 * Matching & Weaving
	 * GETorCALL: expression statement
	 */
	
	
	private static int exprMatchAndWeave(ast.List<Stmt> stmts, int s, String target, ExprStmt context, VFDatum varOrFun, boolean isScript) {
		Expr rhs = context.getExpr();
		int acount = 0, bcount = 0, tcount = 0;
		int args = rhs.FetchArgsCount();
		boolean aroundExist = false;
		Expr prevCase = null;
		boolean isExistCheck = false;
		Expr existObj = null;
		String pattern = "";
		for(int j=0; j<actionsList.size(); j++)
		{
			ActionInfo act = actionsList.get(j);

			boolean isGet = false, isCall = false, isOp = false;

			if(patternsListNew.containsKey(act.getPattern())) {
				Expr pat = patternsListNew.get(act.getPattern());

				if(varOrFun.isVariable()) { //only match if target is variable
					
					isGet = pat.ShadowMatch(target, GET, args, context);
					pattern = GET;
				}else if(rhs instanceof BinaryExpr){
				
					isOp = rhs.ShadowMatch(rhs.dumpString(), OPERATORS, args, context);
					if(isOp)
					pattern = OPERATORS;
				
				} else if(varOrFun.isFunction() || (varOrFun.isBottom() && !isScript)) {
					isCall = pat.ShadowMatch(target, CALL, args, context);
					pattern = CALL;
				} else if(varOrFun.isBottom() && isScript) {
					isGet = pat.ShadowMatch(target, GET, args, context);
					isCall = pat.ShadowMatch(target, CALL, args, context);
					if(isGet && (isCall || isOp) && !isExistCheck) {
						ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
						elst.add(new StringLiteralExpr("var"));
						ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);
						BinaryExpr cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));

						String var = generateCorrespondingVariableName();
						existObj = new NameExpr(new Name(var));

						AssignStmt ias = new AssignStmt(existObj, new NameExpr(new Name(target)));
						ias.setOutputSuppressed(true);
						ias.setWeavability(false, true);
						AssignStmt eas = new AssignStmt(existObj, new ParameterizedExpr(new NameExpr(new Name("eval")), new List<Expr>().add(new StringLiteralExpr("@"+target))));
						eas.setOutputSuppressed(true);
						eas.setWeavability(false, true);

						IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(ias));
						ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
						Stmt existCheck = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));

						int ind = context.getParent().getIndexOfChild(context);
						context.getParent().insertChild(existCheck, ind);

						isExistCheck = true;
						tcount = 1;
					}
				} //else
				//System.out.println("not var, nor fun");


				if(isGet || isCall ) {

					Function fun = act.getFunction();
					ast.List<Expr> lstExpr = new ast.List<Expr>();

					if(act.getType().compareTo(AROUND) == 0 && aroundExist) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(null, rhs, fun.getNestedFunction(0), ile, isGet ? GET:CALL, aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						continue;
					}

					for(Name param : fun.getInputParams()) {
						if(param.getID().compareTo(ARGS) == 0) {
							lstExpr.add(getDims(rhs));
						} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
							IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
							lstExpr.add(ile);
						} else if(param.getID().compareTo(CF_INPUT_OBJ.getID()) == 0) {
							if(isCall && !isGet){
								if(!isScript)
									lstExpr.add(new FunctionHandleExpr(new Name(target)));
								else if(isScript)	
									lstExpr.add(new ParameterizedExpr(new NameExpr(new Name("eval")), new List<Expr>().add(new StringLiteralExpr("@"+target))));
							}
							else if(isGet && !isCall)
								lstExpr.add(new NameExpr(new Name(target)));
							else if(isGet && isCall)
								lstExpr.add(existObj);
						} else if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
							lstExpr.add(getDims(rhs));
						} else if(param.getID().compareTo(THIS) == 0) {
							//do nothing
						} else if(param.getID().compareTo(NAME) == 0) {
							lstExpr.add(new StringLiteralExpr(target));
						} else if(param.getID().compareTo(OBJ) == 0) {
							if(isGet && !isCall)
								lstExpr.add(new NameExpr(new Name(target)));
							else
								lstExpr.add(new MatrixExpr());
						} else if(param.getID().compareTo(LINE) == 0) {
							lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(context.getLineNum()))));
						} else if(param.getID().compareTo(LOC) == 0) {
							lstExpr.add(new StringLiteralExpr(getLocation(context)));
						} else if(param.getID().compareTo(FILE) == 0) {
							lstExpr.add(new StringLiteralExpr(getFileName(context)));
						} else if(param.getID().compareTo(PAT) == 0) {
							lstExpr.add(new StringLiteralExpr(pattern));
						} else if(param.getID().compareTo(AINPUT) == 0) {
							CellArrayExpr lstargs = new CellArrayExpr();
							Row row = new Row();

							if(rhs instanceof ParameterizedExpr){
								ParameterizedExpr trhs = (ParameterizedExpr)rhs;
								for(Expr arg : trhs.getArgs()) {
									if(arg instanceof NameExpr){
										NameExpr tne = (NameExpr)arg;
										if(tne.getWeavability())
											row.addElement(new StringLiteralExpr(tne.getName().getID()));
										else
											row.addElement(new StringLiteralExpr(tne.getInputParamName()));
									} else
										row.addElement(new StringLiteralExpr(""));
								}
							}

							lstargs.addRow(row);
							lstExpr.add(lstargs);
						} else
							lstExpr.add(new CellArrayExpr());
					}

					Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
					Expr de2 = new DotExpr(de1, new Name(act.getName()));
					ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
					pe.setWeavability(false);

					Stmt action = new ExprStmt(pe);
					action.setOutputSuppressed(true);

					Stmt call = action;

					if(!(isCall && isGet) && varOrFun.isBottom() && isScript) { //script case
						BinaryExpr cond;
						ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
						elst.add(new StringLiteralExpr("var"));
						ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);

						if(isGet)
							cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
						else
							cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));

						IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(action));
						Stmt outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());
						call = outerIf;
					}

					if(act.getType().compareTo(BEFORE) == 0) {
						stmts.insertChild(call, s+bcount+tcount);
						bcount += 1;
					} else if(act.getType().compareTo(AFTER) == 0) {
						stmts.insertChild(call, s+bcount+acount+1+tcount);
						acount += 1;
					} else if(act.getType().compareTo(AROUND) == 0) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(null, rhs, fun.getNestedFunction(0), ile, isOp? OPERATORS:(isGet ? GET:CALL), aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						stmts.setChild(call, s+bcount+tcount);

						aroundExist = true;
					}
				}
			}
		}

		return acount + bcount + tcount;
	}

	/*
	 * Matching & Weaving
	 * LOOP, LOOPBODY, LOOPHEAD: FOR and WHILE loops
	 */
	public static int weaveLoops(Stmt loop)
	{
		String loopVar = fetchLoopVariables(loop);

		//AssignStmt head = fetchLoopHeads(loop);
		AssignStmt head = null;
		Stmt headStmt = fetchLoopHeads(loop);
		if(headStmt instanceof AssignStmt)
			head = (AssignStmt)headStmt;

		int acount = 0, bcount = 0, tcount = 0;
		int bacount = 0, bbcount = 0;
		int hacount = 0, hbcount = 0;
		ASTNode parent = loop.getParent();
		boolean aroundExist = false;
		Expr prevCase = null;

		for(int j=0; j<actionsList.size(); j++)
		{
			ActionInfo act = actionsList.get(j);

			boolean isLoop = false, isBody = false, isHead = false;

			if(patternsListNew.containsKey(act.getPattern())) {
				Expr pat = patternsListNew.get(act.getPattern());
				isLoop = pat.ShadowMatch(loopVar, LOOP, -1, loop);
				isBody = pat.ShadowMatch(loopVar, LOOPBODY, -1, loop);
				isHead = pat.ShadowMatch(loopVar, LOOPHEAD, -1, loop);

				if(isLoop || isBody || isHead){

					Function fun = act.getFunction();
					ast.List<Expr> lstExpr = new ast.List<Expr>();
					Expr nv = null;

					if(act.getType().compareTo(AROUND) == 0 && aroundExist && isHead && head != null) {
						IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
						prevCase = addSwitchCaseToAroundCorrespondingFunction(head.getLHS(), head.getRHS(), fun.getNestedFunction(0), ile, LOOPHEAD, aroundExist, prevCase, act.getClassName());
						fun.incCorrespondingCount();
						continue;
					}

					for(Name param : fun.getInputParams()) {
						if(param.getID().compareTo(ARGS) == 0) {
							if(loop instanceof ForStmt && !(isHead && (act.getType().compareTo(BEFORE) == 0 || act.getType().compareTo(AROUND) == 0))){
								CellArrayExpr cae = new CellArrayExpr();
								Row row = new Row();
								row.addElement(new NameExpr(new Name("AM_tmpAS_" + loopVar.replace(",", ""))));
								cae.addRow(row);
								lstExpr.add(cae);
							} else
								lstExpr.add(new CellArrayExpr());
						} else if(param.getID().compareTo(CF_INPUT_CASE.getID()) == 0) {
							if(isHead){
								IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
								lstExpr.add(ile);
							} else
								lstExpr.add(new CellArrayExpr());
						} 
						else if(param.getID().compareTo(NEWVAL) == 0 || param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0) {
							//if(isHead){
							if(isHead && head != null){
								if(nv == null){
									Expr rhs = head.getRHS();

									if(rhs.getWeavability() && !rhs.getCFStmtDone()) {
										String var = generateCorrespondingVariableName();
										Expr tmp = new NameExpr(new Name(var));
										tmp.setWeavability(false);
										rhs.setCFStmtDone(true);

										AssignStmt stmt = new AssignStmt((Expr) tmp.copy(), (Expr) rhs.copy());
										stmt.setOutputSuppressed(true);

										int ind = head.getIndexOfChild(rhs);
										head.setChild(tmp, ind);

										ind = head.getParent().getIndexOfChild(head);
										head.getParent().insertChild(stmt, ind);

										nv = tmp;
										tcount = 1;

										//other heads of while
										if(loop instanceof WhileStmt){
											WhileStmt ws = (WhileStmt)loop;
											ws.getStmtList().add(stmt);
											ws.WeaveLoopStmts(stmt, true);
										}

									} else if(!rhs.getWeavability()){
										nv = rhs;
									}
								}

								if(param.getID().compareTo(CF_INPUT_AGRS.getID()) == 0){
									CellArrayExpr cae = new CellArrayExpr();
									Row row = new Row();
									row.addElement(nv);
									cae.addRow(row);
									lstExpr.add(cae);
								} else
									lstExpr.add(nv);
							} else
								lstExpr.add(new CellArrayExpr());
						}
						else if(param.getID().compareTo(COUNTER) == 0) {
							if(loop instanceof ForStmt && isBody)
								lstExpr.add(new NameExpr(new Name("AM_tmpFS_" + loopVar.replace(",", ""))));
							else
								lstExpr.add(new CellArrayExpr());
						} else if(param.getID().compareTo(OBJ) == 0) {
							if(loop instanceof ForStmt && isBody)
								lstExpr.add(new NameExpr(new Name(loopVar.replace(",", ""))));
							else
								lstExpr.add(new CellArrayExpr());
						} else if(param.getID().compareTo(THIS) == 0) {
							//do nothing
						} else if(param.getID().compareTo(LINE) == 0) {
							lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(loop.getLineNum()))));
						} else if(param.getID().compareTo(LOC) == 0) {
							lstExpr.add(new StringLiteralExpr(getLocation(loop)));
						} else if(param.getID().compareTo(FILE) == 0) {
							lstExpr.add(new StringLiteralExpr(getFileName(loop)));
						} else if(param.getID().compareTo(PAT) == 0) {
							lstExpr.add(new StringLiteralExpr(isLoop ? LOOP:(isBody ? LOOPBODY:LOOPHEAD)));
						} else
							lstExpr.add(new CellArrayExpr());
					}

					Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
					Expr de2 = new DotExpr(de1, new Name(act.getName()));
					ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
					pe.setWeavability(false);

					Stmt call = new ExprStmt(pe);
					call.setOutputSuppressed(true);

					if(isLoop){
						if(act.getType().compareTo(BEFORE) == 0) {
							parent.insertChild(call, parent.getIndexOfChild(loop));
							bcount += 1;
						} else if(act.getType().compareTo(AFTER) == 0) {
							parent.insertChild(call, parent.getIndexOfChild(loop)+acount+1);
							acount += 1;
						} else if(act.getType().compareTo(AROUND) == 0) {
							//TODO
						}
					} 

					if(isBody){
						if(act.getType().compareTo(BEFORE) == 0) {
							if(loop instanceof ForStmt)
								loop.getChild(1).insertChild(call, 1+bbcount);
							else
								loop.getChild(1).insertChild(call, 0+bbcount);
							bbcount += 1;
						} else if(act.getType().compareTo(AFTER) == 0) {
							loop.getChild(1).addChild(call);
							bacount += 1;

							loop.WeaveLoopStmts(call, false);
						} else if(act.getType().compareTo(AROUND) == 0) {
							//TODO
						}
					} 

					if(isHead){
						if(act.getType().compareTo(BEFORE) == 0) {
							parent.insertChild(call, parent.getIndexOfChild(head));
							hbcount += 1;

							//other heads of while
							if(loop instanceof WhileStmt){
								WhileStmt ws = (WhileStmt)loop;
								ws.getStmtList().add(call);
								ws.WeaveLoopStmts(call, true);
							}
						} else if(act.getType().compareTo(AFTER) == 0) {
							parent.insertChild(call, parent.getIndexOfChild(head)+hacount+1);
							hacount += 1;

							//other heads of while
							if(loop instanceof WhileStmt){
								WhileStmt ws = (WhileStmt)loop;
								ws.getStmtList().add(call);
								ws.WeaveLoopStmts(call, true);
							}
							//} else if(act.getType().compareTo(AROUND) == 0) {
						} else if(act.getType().compareTo(AROUND) == 0 && head != null) {
							IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
							prevCase = addSwitchCaseToAroundCorrespondingFunction(head.getLHS(), head.getRHS(), fun.getNestedFunction(0), ile, LOOPHEAD, aroundExist, prevCase, act.getClassName());
							fun.incCorrespondingCount();
							head.setRHS(pe);

							aroundExist = true;
						}
					}
				}
			}
		}

		return acount + bcount + tcount + hacount + hbcount;
	}


	public static String fetchLoopVariables(Stmt loop)
	{
		String loopVar = "";

		if(loop instanceof ForStmt){
			ForStmt fstmt = (ForStmt) loop;
			loopVar = fstmt.getLoopVar();
		} if(loop instanceof WhileStmt){
			WhileStmt wstmt = (WhileStmt) loop;
			loopVar = wstmt.getLoopVars();
		}

		return loopVar+",";
	}

	private static Stmt fetchLoopHeads(Stmt loop)
	{
		//AssignStmt loopHead = null;
		Stmt loopHead = null;

		if(loop instanceof ForStmt){
			ForStmt fstmt = (ForStmt) loop;
			loopHead = fstmt.getLoopHead();
		} if(loop instanceof WhileStmt){
			WhileStmt wstmt = (WhileStmt) loop;
			loopHead = wstmt.getLoopHead();
		}

		return loopHead;
	}

	/*
	 * Helper function to weave at distinct places in loop
	 * BREAK, CONTINUE, RETURN
	 */
	public static void WeaveLoopStmts(ast.List<Stmt> stmts, Stmt call, boolean onlyContinue){
		int count = stmts.getNumChild();
		for(int i=0; i<count; i++) {
			Stmt stmt = stmts.getChild(i);

			if(stmt instanceof BreakStmt || stmt instanceof ContinueStmt || stmt instanceof ReturnStmt){
				if((!onlyContinue && (stmt instanceof BreakStmt || stmt instanceof ReturnStmt)) || stmt instanceof ContinueStmt){
					ASTNode<ASTNode> parent = stmt.getParent();
					parent.insertChild(call.copy(), parent.getIndexOfChild(stmt));
					i++; count++;
				}
			} else
				stmt.WeaveLoopStmts(call, onlyContinue);
		}
	}

	/*
	 * Matching & Weaving
	 * EXECUTION: function execution
	 */
	public static void weaveFunction(Function func)
	{
		int acount = 0, bcount = 0;
		boolean aroundExist = false;
		Expr prevCase = null;

		for(int j=0; j<actionsList.size(); j++)
		{
			ActionInfo act = actionsList.get(j);
			if(patternsListNew.containsKey(act.getPattern()) 
					&& patternsListNew.get(act.getPattern()).ShadowMatch(func.getName(), EXECUTION, func.getInputParams().getNumChild(), func)
			){

				Function fun = act.getFunction();
				NameExpr funcName = new NameExpr(new Name(act.getName()));

				List<Expr> lst = new List<Expr>();
				for(Name arg : func.getOutputParams())
					lst.add((new NameExpr(arg)));
				Expr out = new MatrixExpr(new List<Row>().add(new Row(lst)));

				if(act.getType().compareTo(AROUND) == 0 && aroundExist) {
					IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
					prevCase = addSwitchCaseToAroundCorrespondingFunction(out, null, fun.getNestedFunction(0), ile, EXECUTION, aroundExist, prevCase, act.getClassName());
					fun.incCorrespondingCount();
					continue;
				}

				ast.List<Expr> lstExpr = new ast.List<Expr>();

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
						lstExpr.add(new CellArrayExpr());
					} else if(param.getID().compareTo(LINE) == 0) {
						lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(Symbol.getLine(func.getStart())))));
					} else if(param.getID().compareTo(LOC) == 0) {
						lstExpr.add(new StringLiteralExpr(func.getName()));
					} else if(param.getID().compareTo(FILE) == 0) {
						lstExpr.add(new StringLiteralExpr(getFileName(func)));
					} else if(param.getID().compareTo(PAT) == 0) {
						lstExpr.add(new StringLiteralExpr(EXECUTION));
					} else if(param.getID().compareTo(AINPUT) == 0) {
						CellArrayExpr args = new CellArrayExpr();
						Row row = new Row();
						for(Name arg : func.getInputParamNames()) {
							row.addElement(new StringLiteralExpr(arg.getID()));
						}
						args.addRow(row);
						lstExpr.add(args);
					} else if(param.getID().compareTo(AOUTPUT) == 0) {
						CellArrayExpr args = new CellArrayExpr();
						Row row = new Row();
						for(Name arg : func.getOutputParams()) {
							row.addElement(new StringLiteralExpr(arg.getID()));
						}
						args.addRow(row);
						lstExpr.add(args);
					} else
						lstExpr.add(new CellArrayExpr());
				}

				Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
				Expr de2 = new DotExpr(de1, new Name(act.getName()));
				ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
				pe.setWeavability(false);

				Stmt call;
				if(act.getType().compareTo(AROUND) == 0)
					call = new AssignStmt(out, pe);
				else
					call = new ExprStmt(pe);
				call.setOutputSuppressed(true);			

				if(act.getType().compareTo(BEFORE) == 0) {
					func.getStmts().insertChild(call, 0+bcount);
					bcount += 1;
				} else if(act.getType().compareTo(AFTER) == 0) {
					func.getStmts().addChild(call);
					acount += 1;

					WeaveBeforeReturn(func.getStmts(), call);

				} else if(act.getType().compareTo(AROUND) == 0) {
					IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
					convertToHandleFunction(func);
					ast.List<Expr> tmp = new ast.List<Expr>();
					for(Name arg : func.getInputParams()) {
						tmp.add(new NameExpr(arg));
					}
					ParameterizedExpr tmp_pe = new ParameterizedExpr(funcName, tmp);

					prevCase = addSwitchCaseToAroundCorrespondingFunction(out, tmp_pe, fun.getNestedFunction(0), ile, EXECUTION, aroundExist, prevCase, act.getClassName());
					fun.incCorrespondingCount();
					func.getStmts().addChild(call);

					aroundExist = true;
				}
			}
		}
	}

	/*
	 * Matching & Weaving
	 * EXECUTION: script execution
	 */
	public static void weaveScript(Script script)
	{
		int acount = 0, bcount = 0;
		String name = script.getFileName().replace(".m", "");

		for(int j=0; j<actionsList.size(); j++)
		{
			ActionInfo act = actionsList.get(j);

			if(patternsListNew.containsKey(act.getPattern()) 
					&& patternsListNew.get(act.getPattern()).ShadowMatch(name, EXECUTION, 0, script)
			){

				if(act.getType().compareTo(AROUND) == 0) {
					System.out.println("Skipped Action "+act.getName()+": Around actions for Execution of Script are not allowed!");
					continue;
				}

				Function fun = act.getFunction();
				NameExpr funcName = new NameExpr(new Name(act.getName()));

				ast.List<Expr> lstExpr = new ast.List<Expr>();

				for(Name param : fun.getInputParams()) {
					if(param.getID().compareTo(THIS) == 0) {
						//do nothing
					} else if(param.getID().compareTo(NAME) == 0) {
						lstExpr.add(new StringLiteralExpr(name));
					} else if(param.getID().compareTo(OBJ) == 0) {
						lstExpr.add(new CellArrayExpr());
					} else if(param.getID().compareTo(LINE) == 0) {
						lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(Symbol.getLine(script.getStart())))));
					} else if(param.getID().compareTo(LOC) == 0) {
						lstExpr.add(new StringLiteralExpr(name));
					} else if(param.getID().compareTo(FILE) == 0) {
						lstExpr.add(new StringLiteralExpr(getFileName(script)));
					} else if(param.getID().compareTo(PAT) == 0) {
						lstExpr.add(new StringLiteralExpr(EXECUTION));
					} else
						lstExpr.add(new CellArrayExpr());
				}

				Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
				Expr de2 = new DotExpr(de1, new Name(act.getName()));
				ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
				pe.setWeavability(false);


				Stmt call = new ExprStmt(pe);
				call.setOutputSuppressed(true);		

				if(act.getType().compareTo(BEFORE) == 0) {
					script.getStmts().insertChild(call, 0+bcount);
					bcount += 1;
				} else if(act.getType().compareTo(AFTER) == 0) {
					script.getStmts().addChild(call);
					acount += 1;

					WeaveBeforeReturn(script.getStmts(), call);

				} else if(act.getType().compareTo(AROUND) == 0) {
					//Not allowed!
				}
			}
		}
	}

	/*
	 * Matching & Weaving
	 * MAINEXECUTION: execution of main entry point
	 */
	public static void weaveMain(Program p, ast.List<Stmt> lstbefore, ast.List<Stmt> lstafter) {
		String name = p.getFileName().replace(".m", "");

		//Weave MainExecution here
		for(int j=0; j<actionsList.size(); j++)
		{
			ActionInfo act = actionsList.get(j);

			if(patternsListNew.containsKey(act.getPattern()) 
					&& patternsListNew.get(act.getPattern()).ShadowMatch("", MAINEXECUTION, 0, p)
			){

				if(act.getType().compareTo(AROUND) == 0) {
					System.out.println("Skipped Action "+act.getName()+": Around actions for Execution of Main are not allowed!");
					continue;
				}

				Function fun = act.getFunction();
				ast.List<Expr> lstExpr = new ast.List<Expr>();

				for(Name param : fun.getInputParams()) {
					if(param.getID().compareTo(THIS) == 0) {
						//do nothing
					} else if(param.getID().compareTo(NAME) == 0) {
						lstExpr.add(new StringLiteralExpr(name));
					} else if(param.getID().compareTo(LINE) == 0) {
						lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(Symbol.getLine(p.getStart())))));
					} else if(param.getID().compareTo(LOC) == 0) {
						lstExpr.add(new StringLiteralExpr(name));
					} else if(param.getID().compareTo(FILE) == 0) {
						lstExpr.add(new StringLiteralExpr(p.getFileName()));
					} else if(param.getID().compareTo(PAT) == 0) {
						lstExpr.add(new StringLiteralExpr(MAINEXECUTION));
					} else
						lstExpr.add(new CellArrayExpr());
				}

				Expr de1 = new DotExpr(new NameExpr(new Name(GLOBAL_STRUCTURE)), new Name(act.getClassName()));
				Expr de2 = new DotExpr(de1, new Name(act.getName()));
				ParameterizedExpr pe = new ParameterizedExpr(de2, lstExpr);
				pe.setWeavability(false);

				Stmt call = new ExprStmt(pe);
				call.setOutputSuppressed(true);		

				if(act.getType().compareTo(BEFORE) == 0) {
					lstbefore.add(call);
				} else if(act.getType().compareTo(AFTER) == 0) {
					lstafter.add(call);
				} else if(act.getType().compareTo(AROUND) == 0) {
					//Not allowed!
				}
			}
		}
	}

	public static void WeaveBeforeReturn(ast.List<Stmt> stmts, Stmt call){
		int count = stmts.getNumChild();
		for(int i=0; i<count; i++) {
			Stmt stmt = stmts.getChild(i);

			if(stmt instanceof ReturnStmt){
				ASTNode<ASTNode> parent = stmt.getParent();
				parent.insertChild(call.copy(), parent.getIndexOfChild(stmt));
				i++; count++;
			} else
				stmt.WeaveBeforeReturn(call);
		}
	}

	private static String generateHandleName(String func){
		return "AM_Handle_" + func;
	}

	private static void convertToHandleFunction(Function func){
		Function handle = new Function(func.getOutputParams(), generateHandleName(func.getName()), func.getInputParams(), new ast.List<HelpComment>(), func.getStmts(), func.getNestedFunctions());
		func.setStmtList(new ast.List<Stmt>());
		func.setNestedFunctionList(new ast.List<Function>());
		func.addNestedFunction(handle);
	}

	private static CellArrayExpr getDims(Expr exp){
		CellArrayExpr dims = new CellArrayExpr();

		if(exp instanceof ParameterizedExpr) {
			ParameterizedExpr pe = (ParameterizedExpr) exp;
			Expr target = pe.getTarget();
			int count = pe.getNumArg();
			IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1)));

			ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr("end"));
			elst.add(target);

			ast.List<Expr> args = new ast.List<Expr>();			
			for(int i=0; i<count; i++){
				Expr arg = pe.getArg(i);

				if(arg instanceof ColonExpr) {
					elst.add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i+1))));
					elst.add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(count))));
					ParameterizedExpr builtin = new ParameterizedExpr(new NameExpr(new Name("builtin")), elst);
					args.add(new RangeExpr(ile, new Opt<Expr>(), builtin));
				} else //TODO: resolve end expression
					args.add(arg);
			}

			dims.addRow(new Row(args));
		}

		return dims;
	}

	private static String getLocation(ASTNode exp){
		String loc = "";

		ASTNode node = exp;
		while(node != null && !(node instanceof Function || node instanceof Script))
			node = node.getParent();

		if(node != null){
			if(node instanceof Function)
				loc = ((Function)node).getName();
			else if(node instanceof Script)
				loc = ((Script)node).getFileName().replace(".m", "");
		}

		return loc;
	}

	private static String getFileName(ASTNode exp){
		String file = "";

		ASTNode node = exp;
		while(node != null && !(node instanceof Program))
			node = node.getParent();

		if(node != null){
			file = ((Program)node).getFileName();
		}

		return file;
	}


    static boolean isOperator(String target){
    	if(target.equals("+")||target.equals("-")||target.equals("/")||target.equals("*")||target.equals("^")||target.equals("'")||target.equals(".*")||target.equals("./")||target.equals(".^")||target.equals(".'")){
    		return true;
    	}else return false;
    }
    /*
    static String toOpFunction(String target){
    	if(target.equals("+")){
    		return "PlusExpr";
    	}else if(target.equals("-")){
    		return "MinusExpr";
    	}else if(target.equals("*")){
    		return "MTimesExpr";
    	}else if(target.equals("/")){
    		return "MDivExpr";
    	}else if(target.equals("^")){
    		return "MPowExpr";
    	}else if (target.equals("'")){
    		return "MTransposeExpr";
    	}else if(target.equals(".*")){
    		return "ETimesExpr";
    	}else if(target.equals("./")){
    		return "EDivExpr";
    	}else if(target.equals(".^")){
    		return "EPowExpr";
    	}else 
    		return "NOTIMPLEMENTED";
    	
    	
    }
    */
//	Removed Code//

	{
		/*class action {
	private String name;
	private String type;
	//private pattern patt;
	private String patt;
	private Function func;
	private String className;

	public action(String nam, String typ, String pat, Function fun, String cName) {
		name = nam;
		type = typ;
		patt = pat;
		func = fun;
		className = cName;
	}

	public void setName(String nam) { name = nam; }
	public void setType(String typ) { type = typ; }
	public void setPattern(String pat) { patt = pat; }
	public void setFunction(Function fun) { func = fun; }
	public void setClassName(String name) { className = name; }

	public String getName() { return name; }
	public String getType() { return type; }
	public String getPattern() { return patt; }
	public Function getFunction() { return func; }
	public String getClassName() { return className; }
}*/

		/*
class pattern {
	private String name;
	private String type;
	private String target;
	private int dims;
	private boolean dimsAndMore;

	public pattern(String nam, String typ, String tar, int dim, boolean mor) {
		name = nam;
		type = typ;
		target = tar;
		dims = dim;
		dimsAndMore = mor;
	}

	public void setName(String nam) { name = nam; }
	public void setType(String typ) { type = typ; }
	public void setTarget(String tar) { target = tar; }
	public void setDims(int dim) { dims = dim; }
	public void setDimsAndMore(boolean mor) { dimsAndMore = mor; }

	public String getName() { return name; }
	public String getType() { return type; }
	public String getTarget() { return target; }
	public int getDims() { return dims; }
	public boolean getDimsAndMore() { return dimsAndMore; }
}
		 */

		/*
	 private static void addSwitchCaseToAroundCorrespondingFunction(Expr lhs, Expr pe, Function corFun, IntLiteralExpr ile, String type, boolean aroundExist, String prevAroundName, IntLiteralExpr prevAroundile){
		ast.List<Stmt> scsl = new ast.List<Stmt>();
		ast.List<Expr> input = new ast.List<Expr>();
		Expr exp = null;

		if(!aroundExist){
			exp = (Expr) pe.copy();
			Expr tmp = (Expr) pe.copy();

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
					input.add(new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));

				if(exp instanceof ParameterizedExpr)
					exp = new ParameterizedExpr(((ParameterizedExpr)exp).getTarget(), input);
				else if(exp instanceof CellIndexExpr)
					exp = new CellIndexExpr(((CellIndexExpr)exp).getTarget(), input);
			}

			AssignStmt tmpStmt = new AssignStmt();
			if(type.compareTo(SET) == 0 || type.compareTo(LOOPHEAD) == 0) {
				exp = new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1)))));
			} else if(type.compareTo(EXECUTION) == 0 || type.compareTo(CALL) == 0) {
				exp = new ParameterizedExpr(new NameExpr(CF_INPUT_OBJ), input);
			} else {
				tmpStmt = new AssignStmt(tmp, new NameExpr(CF_INPUT_OBJ));
				tmpStmt.setOutputSuppressed(true);
				tmpStmt.setWeavability(false, true);
				scsl.add(tmpStmt);
			}
		} else {
			input.add(new NameExpr(new Name(THIS)));
			input.add(prevAroundile);
			input.add(new NameExpr(CF_INPUT_OBJ));
			input.add(new NameExpr(CF_INPUT_AGRS));

			input.add(new NameExpr(new Name(ARGS)));
			input.add(new NameExpr(new Name(COUNTER)));
			input.add(new NameExpr(new Name(FILE)));
			input.add(new NameExpr(new Name(LINE)));
			input.add(new NameExpr(new Name(LOC)));
			input.add(new NameExpr(new Name(NAME)));
			input.add(new NameExpr(new Name(NEWVAL)));
			input.add(new NameExpr(new Name(OBJ)));

			exp = new ParameterizedExpr(new NameExpr(new Name(prevAroundName)), input); 
		}

		Expr out = null;
		if(lhs instanceof MatrixExpr){
			MatrixExpr me = (MatrixExpr)lhs;
			int size = me.getRow(0).getNumElement();
			List<Expr> lst = new List<Expr>();

			for(int i=1; i<=size; i++)
				lst.add(new CellIndexExpr(new NameExpr(CF_OUTPUT), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));
			out = new MatrixExpr(new List<Row>().add(new Row(lst)));
		} else {
			out = new CellIndexExpr(new NameExpr(CF_OUTPUT), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1)))));
		}

		Stmt stmt = new AssignStmt(out, exp);
		if(lhs == null) //ExprStmt case
			stmt = new ExprStmt(exp);

		stmt.setOutputSuppressed(true);
		scsl.add(stmt);

		SwitchCaseBlock scb = new SwitchCaseBlock(ile, scsl);
		SwitchStmt ss = (SwitchStmt) corFun.getStmt(0);
		ss.addSwitchCaseBlock(scb);
	}*/

		/*

	public static void analysis(ASTNode cu){
		VFPreorderAnalysis vfa = new VFPreorderAnalysis(cu);
		vfa.analyze();
		vfaMap = vfa.getFlowSets();
		//System.out.println(vfa.getCurrentSet().toString());
	}

	public static FunctionVFDatum checkVarOrFun(ASTNode node, String target){
		java.util.List<ValueDatumPair<String, FunctionVFDatum>> fset = vfaMap.get(node).toList();

		for(ValueDatumPair<String, FunctionVFDatum> vdp : fset){
			if(vdp.getValue().compareTo(target) == 0)
				return vdp.getDatum();
		}

		return new FunctionVFDatum();
	}
		 */

		/*
private static CellArrayExpr getNewVal(Expr exp){
	CellArrayExpr nv = new CellArrayExpr();

	if(exp instanceof IntLiteralExpr || exp instanceof FPLiteralExpr || exp instanceof StringLiteralExpr) {
		nv.addRow(new Row(new ast.List<Expr>().add(exp)));
	}

	return nv;
}
		 */

		/*
public static void generateCorrespondingFunction(Expr pe) {
	if(pe.getWeavability()) {
		String target = pe.FetchTargetExpr();
		for(pattern pat : patternsList) {
			if((pat.getType().compareTo(GET) == 0 || pat.getType().compareTo(CALL) == 0) && 
					(pat.getTarget().compareTo(target) == 0 || pat.getTarget().compareTo("*") == 0)) {
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
							prog.getParent().addChild(new FunctionList(new ast.List<Function>().add(corFun)));
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

private static void addSwitchCaseToCorrespondingFunction(Expr pe, Function corFun, IntLiteralExpr ile, boolean isScript){
	Expr exp = (Expr) pe.copy();
	Expr tmp = (Expr) pe.copy();
	ast.List<Expr> input = new ast.List<Expr>();

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
			input.add(new CellIndexExpr(new NameExpr(CF_INPUT_AGRS), new ast.List<Expr>().add(new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(i))))));

		if(exp instanceof ParameterizedExpr)
			exp = new ParameterizedExpr(((ParameterizedExpr)exp).getTarget(), input);
		else if(exp instanceof CellIndexExpr)
			exp = new CellIndexExpr(((CellIndexExpr)exp).getTarget(), input);
	}

	ast.List<Stmt> scsl = new ast.List<Stmt>();

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
	ast.List<Expr> input = new ast.List<Expr>().add(ile);
	ast.List<Expr> args = new ast.List<Expr>();

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
		ast.List<Expr> lst = new ast.List<Expr>().add(new StringLiteralExpr(tmp.FetchTargetExpr()));
		lst.add(new StringLiteralExpr("var"));
		ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), lst);
		BinaryExpr cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));

		AssignStmt eas = new AssignStmt(tmp, new StringLiteralExpr("AM_tmp_" + tmp.FetchTargetExpr()));
		eas.setOutputSuppressed(true);
		eas.setWeavability(false, true);
		IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(eas));
		ifstmt = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

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
		 */

		/*
private static int matchAndWeave(ast.List<Stmt> stmts, int s, String type, String target, AssignStmt context) {
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
			ast.List<Expr> lstExpr = new ast.List<Expr>();

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

				IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(action));
				call = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

				if(act.getType().compareTo(AROUND) == 0) {					
					AssignStmt eas = new AssignStmt(lhs, rhs);
					eas.setOutputSuppressed(true);
					ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
					call = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
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

		/*	private static int getOrCallMatchAndWeave(ast.List<Stmt> stmts, int s, String target, AssignStmt context) {
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
		ast.List<Expr> lstExpr = new ast.List<Expr>();

		for(Name param : fun.getInputParams()) {
			if(param.getID().compareTo(ARGS) == 0) {
				//if(pat.getType().compareTo(CALL) == 0)
				lstExpr.add(getDims(rhs));
				//else
				//lstExpr.add(new CellArrayExpr());
			} else if(param.getID().compareTo(DIMS) == 0) {
				//if(pat.getType().compareTo(GET) == 0)
				//	lstExpr.add(getDims(rhs));
				//else
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
			} else if(param.getID().compareTo(LINE) == 0) {
				lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(context.getLine(context.getStart())))));
			} else if(param.getID().compareTo(LOC) == 0) {
				lstExpr.add(new StringLiteralExpr(getLocation(context)));
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

//		ASTNode node = stmts;
//		while(node != null && !(node instanceof Function))
//			node = node.getParent();
//		boolean isScriptCF = false;
//		ParameterizedExpr strcmp = new ParameterizedExpr();
//
//		if(node != null && ((Function)node).getName().startsWith(AM_CF_SCRIPT)) {
//			isScriptCF = true;
//			ast.List<Expr> lst = new ast.List<Expr>().add(new NameExpr(CF_INPUT_OBJ));
//			lst.add(new StringLiteralExpr("AM_tmp_" + target));
//			strcmp = new ParameterizedExpr(new NameExpr(new Name("strcmp")), lst);
//		}

		//ast.List<Expr> nlst = new ast.List<Expr>().add(new NameExpr(new Name(target)));
		//ParameterizedExpr numel = new ParameterizedExpr(new NameExpr(new Name("numel")), nlst);
		ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
		elst.add(new StringLiteralExpr("var"));
		ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);

		if(pat.getType().compareTo(GET) == 0) {
//			if(!(pat.getDims().compareTo("0") == 0)) {
//				BinaryExpr cond2;
//
//				if(!pat.getDimsAndMore())
//					cond2 = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));
//				else
//					cond2 = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));
//
//				IfBlock ib = new IfBlock(cond2, new ast.List<Stmt>().add(action));
//				call = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());
//
//				if(act.getType().compareTo(AROUND) == 0) {					
//					AssignStmt eas = new AssignStmt(lhs, rhs);
//					eas.setOutputSuppressed(true);
//					ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
//					call = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
//				}
//			}

			//if(isScriptCF)
			//	cond = new NEExpr(strcmp, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
			//else
				cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
		} else {
			//if(isScriptCF)
			//	cond = new EQExpr(strcmp, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
			//else
				cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
		}

		IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(call));
		Stmt outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

		if(act.getType().compareTo(AROUND) == 0) {					
			AssignStmt eas = new AssignStmt(lhs, rhs);
			eas.setOutputSuppressed(true);
			ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
			outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
		}

		if(act.getType().compareTo(BEFORE) == 0) {
			stmts.insertChild(outerIf, s);
			bcount += 1;
		} else if(act.getType().compareTo(AFTER) == 0) {
			stmts.insertChild(outerIf, s+bcount+acount+1);
			acount += 1;
		} else if(act.getType().compareTo(AROUND) == 0) {
			IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
			addSwitchCaseToAroundCorrespondingFunction(lhs, rhs, fun.getNestedFunction(0), ile, pat.getType());
			fun.incCorrespondingCount();
			stmts.setChild(outerIf, s+bcount);
		}
	}
}

return acount + bcount;
}*/

		/*	private static int getOrCallMatchAndWeave(ast.List<Stmt> stmts, int s, String target, AssignStmt context) {
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
		ast.List<Expr> lstExpr = new ast.List<Expr>();

		for(Name param : fun.getInputParams()) {
			if(param.getID().compareTo(ARGS) == 0) {
				//if(pat.getType().compareTo(CALL) == 0)
				lstExpr.add(getDims(rhs));
				//else
				//lstExpr.add(new CellArrayExpr());
			} else if(param.getID().compareTo(DIMS) == 0) {
				//if(pat.getType().compareTo(GET) == 0)
				//	lstExpr.add(getDims(rhs));
				//else
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
			} else if(param.getID().compareTo(LINE) == 0) {
				lstExpr.add(new IntLiteralExpr(new DecIntNumericLiteralValue(String.valueOf(context.getLine(context.getStart())))));
			} else if(param.getID().compareTo(LOC) == 0) {
				lstExpr.add(new StringLiteralExpr(getLocation(context)));
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

//		ASTNode node = stmts;
//		while(node != null && !(node instanceof Function))
//			node = node.getParent();
//		boolean isScriptCF = false;
//		ParameterizedExpr strcmp = new ParameterizedExpr();
//
//		if(node != null && ((Function)node).getName().startsWith(AM_CF_SCRIPT)) {
//			isScriptCF = true;
//			ast.List<Expr> lst = new ast.List<Expr>().add(new NameExpr(CF_INPUT_OBJ));
//			lst.add(new StringLiteralExpr("AM_tmp_" + target));
//			strcmp = new ParameterizedExpr(new NameExpr(new Name("strcmp")), lst);
//		}

		//ast.List<Expr> nlst = new ast.List<Expr>().add(new NameExpr(new Name(target)));
		//ParameterizedExpr numel = new ParameterizedExpr(new NameExpr(new Name("numel")), nlst);
		ast.List<Expr> elst = new ast.List<Expr>().add(new StringLiteralExpr(target));
		elst.add(new StringLiteralExpr("var"));
		ParameterizedExpr exist = new ParameterizedExpr(new NameExpr(new Name("exist")), elst);

		if(pat.getType().compareTo(GET) == 0) {
//			if(!(pat.getDims().compareTo("0") == 0)) {
//				BinaryExpr cond2;
//
//				if(!pat.getDimsAndMore())
//					cond2 = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));
//				else
//					cond2 = new EQExpr(numel, new NameExpr(new Name(pat.getDims())));
//
//				IfBlock ib = new IfBlock(cond2, new ast.List<Stmt>().add(action));
//				call = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());
//
//				if(act.getType().compareTo(AROUND) == 0) {					
//					AssignStmt eas = new AssignStmt(lhs, rhs);
//					eas.setOutputSuppressed(true);
//					ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
//					call = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
//				}
//			}

			//if(isScriptCF)
			//	cond = new NEExpr(strcmp, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
			//else
				cond = new EQExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
		} else {
			//if(isScriptCF)
			//	cond = new EQExpr(strcmp, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
			//else
				cond = new NEExpr(exist, new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(1))));
		}

		IfBlock ib = new IfBlock(cond, new ast.List<Stmt>().add(call));
		Stmt outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>());

		if(act.getType().compareTo(AROUND) == 0) {					
			AssignStmt eas = new AssignStmt(lhs, rhs);
			eas.setOutputSuppressed(true);
			ElseBlock eb = new ElseBlock(new ast.List<Stmt>().add(eas));
			outerIf = new IfStmt(new ast.List<IfBlock>().add(ib), new Opt<ElseBlock>(eb));
		}

		if(act.getType().compareTo(BEFORE) == 0) {
			stmts.insertChild(outerIf, s);
			bcount += 1;
		} else if(act.getType().compareTo(AFTER) == 0) {
			stmts.insertChild(outerIf, s+bcount+acount+1);
			acount += 1;
		} else if(act.getType().compareTo(AROUND) == 0) {
			IntLiteralExpr ile = new IntLiteralExpr(new DecIntNumericLiteralValue(Integer.toString(fun.getCorrespondingCount())));
			addSwitchCaseToAroundCorrespondingFunction(lhs, rhs, fun.getNestedFunction(0), ile, pat.getType());
			fun.incCorrespondingCount();
			stmts.setChild(outerIf, s+bcount);
		}
	}
}

return acount + bcount;
}*/
	}

}
