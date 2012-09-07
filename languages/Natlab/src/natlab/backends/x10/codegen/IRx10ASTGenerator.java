package natlab.backends.x10.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import natlab.backends.x10.IRx10.ast.AssignStmt;
import natlab.backends.x10.IRx10.ast.ClassBlock;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.Method;
import natlab.backends.x10.IRx10.ast.MethodBlock;
import natlab.backends.x10.IRx10.ast.MethodHeader;
import natlab.backends.x10.IRx10.ast.Program;
import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.Type;
import natlab.backends.x10.IRx10.ast.Stmt;
import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRAbstractAssignToListStmt;
import natlab.tame.tir.TIRAbstractAssignToVarStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.tir.TIRNode;
import natlab.tame.tir.analysis.TIRAbstractNodeCaseHandler;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.advancedMatrix.AdvancedMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.AggrValue;
import ast.ASTNode;
import ast.Name;

import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.*;

import natlab.tame.tir.analysis.TIRAbstractNodeCaseHandler;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.ValueAnalysisPrinter;
import natlab.tame.valueanalysis.basicmatrix.BasicMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.AggrValue;
import natlab.backends.Fortran.codegen.FortranMapping;

@SuppressWarnings("unused")
public class IRx10ASTGenerator extends TIRAbstractNodeCaseHandler{
    ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis;
    private StringBuffer buf;
    private x10Mapping x10Map;
    private  HashMap<String, Collection<ClassReference>> symbolMap = new HashMap<String, Collection<ClassReference>>(); 
    private String symbolMapKey;    
    private int graphSize;
    private int index;
    private String fileDir;
    private Method method;
    
    
    private IRx10ASTGenerator(ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis2, int size, int index, String fileDir, String classname) {
        this.buf = new StringBuffer();
        this.x10Map = new x10Mapping();
        this.analysis = analysis2;
        //this.graphSize = graphSize;
        this.index = index;
        this.fileDir = fileDir;
        this.method = new Method();
        ((TIRNode)analysis2.getNodeList().get(index).getAnalysis().getTree()).tirAnalyze(this);
        
    }
    
    
    public static ClassBlock x10ClassMaker(
            ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis2, int graphSize, String fileDir, String classname){
        
        List<Method> methodList = new List<Method>();
        IRx10ASTGenerator subAST;
        for (int i=0; i<graphSize; i++)
        {
         subAST =   new IRx10ASTGenerator(analysis2, graphSize, 0, fileDir, classname);
         methodList.add(subAST.method);
        }
        ClassBlock class_block = new ClassBlock(null,methodList);
        return class_block;
    
    }
    
    
    @Override
    public void caseASTNode(ASTNode node) {
        
    }
    
    @Override
    public void caseTIRFunction(TIRFunction node){
        String indent = node.getIndent();
        boolean first = true;
        ArrayList<String> inArgs = new ArrayList<String>();
        // TODO - CHANGE IT TO DETERMINE RETURN TYPE
        //TODO - change "Args" to "ARguments" to avoid name clash 
        //TODO - change to Arguments* for MethodHeader
        List<Args> arguments= new List<Args>();
        MethodHeader method_header = new MethodHeader(); 
        method_header.setName(node.getName());  
        method_header.setReturnType(null);
        
        for(Name param : node.getInputParams()) {
            
            arguments.add(new Args(x10Map.getX10TypeMapping(getArgumentType(analysis, this.index, node, param.getID())),param.getPrettyPrinted())) ;
            symbolMap.put(param.getID().toString(), getAnalysisValue(analysis, this.index, node, param.getID()));
        }
        method_header.setArgsList(arguments);
        MethodBlock method_block = new MethodBlock(new List<Stmt>());
        method.setMethodHeader(method_header);
        method.setMethodBlock(method_block);
        buildStmtsSubAST(node.getStmts());
        
    }
    
    
    private void buildStmtsSubAST(ast.List<ast.Stmt> stmts) {
        for(ast.Stmt stmt : stmts) {
            ((TIRNode)stmt).tirAnalyze(this);
        }
    }

    @Override
    public void caseTIRAbstractAssignStmt(TIRAbstractAssignStmt node){
       
            if (node instanceof TIRAbstractAssignToVarStmt){
            handleTIRAbstractAssignToVarStmt(node);
        }
        else if (node instanceof TIRAbstractAssignToListStmt){
        //  for(ast.Name name : ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
        //      vars.add(name.getID());
            handleTIRAbstractAssignToListStmt(node);        
            
            }
//                  
//      //TODO implement other cases here - refer to ValueAnalysisPrinter
//      /*
//      else if (node instanceof TIRAbstractAssignToListStmt){
//          for(ast.Name name : ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
//              vars.add(name.getID());             
//          }
//      } else if (node instanceof TIRArraySetStmt){
//          vars.add(((TIRArraySetStmt)node).getArrayName().getID());
//      } else if (node instanceof TIRCellArraySetStmt){
//          vars.add(((TIRCellArraySetStmt)node).getCellArrayName().getID());
//      } else if (node instanceof TIRDotSetStmt){
//          vars.add(((TIRDotSetStmt)node).getDotName().getID());
//      };
//      */
//      //printVars(analysis.getOutFlowSets().get(node), vars);
    }
    
    public void handleTIRAbstractAssignToListStmt(TIRAbstractAssignStmt node){
        System.out.println("list assignment");
        String LHS;
        ArrayList<ast.Name> lhsVars = new ArrayList<ast.Name>();
        for(ast.Name name : ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
             handleTIRAbstractAssignToListVarStmt(node,name,
                ((TIRAbstractAssignToListStmt)node).getRHS()); 
             }   
        }
    
    
    //This version handles assignment to multiple variables   
    public void handleTIRAbstractAssignToListVarStmt 
        (TIRAbstractAssignStmt node, ast.Name name, ast.Expr rhs_expr){
        String LHS;
        symbolMapKey = name.getID();
        LHS = symbolMapKey;
        AssignStmt assign_stmt = new AssignStmt();
        if(true == symbolMap.containsKey(LHS)) //variable already defined and analyzed
        {
            
            assign_stmt.setVariableName(name.getNodeString());
            assign_stmt.setValue(null); //Set RHS expression here
            //TODO : Handle expressions of various types 
            //Set parent's value in various expressions
        }
        else 
        {   
            
            Type type = x10Map.getX10TypeMapping(getLHSType(analysis,this.index, node,LHS ));
            assign_stmt.setVariableName(name.getNodeString());
            assign_stmt.setType(type);
            assign_stmt.setValue(null); 
            this.method.getMethodBlock().addStmt(assign_stmt);
            //TODO check for expression on RHS
            //TODO check for built-ins
            //TODO check for operators
            //add to symbol Map
            symbolMap.put(node.getLHS().getNodeString(), getAnalysisValue(analysis,this.index, node,LHS));
            
            
        }
        
    }
    
    public void handleTIRAbstractAssignToVarStmt(TIRAbstractAssignStmt node){
        String LHS;
        symbolMapKey = ((TIRAbstractAssignToVarStmt)node).getTargetName().getID();
        LHS = symbolMapKey;
        AssignStmt assign_stmt = new AssignStmt();
        if(true == symbolMap.containsKey(symbolMapKey)) //variable already defined and analyzed
        {
            
            assign_stmt.setVariableName(((TIRAbstractAssignToVarStmt)node).getTargetName().toString());
            assign_stmt.setValue(null); 
            //TODO : Handle expressions of various types 
            //Set parent's value in various expressions
        }
        else 
        {   
            
            Type type = x10Map.getX10TypeMapping(getLHSType(analysis,this.index, node,LHS ));
            
            assign_stmt.setVariableName(node.getLHS().getNodeString());
            assign_stmt.setType(type);
            assign_stmt.setValue(null); 
            this.method.getMethodBlock().addStmt(assign_stmt);
            //TODO check for expression on RHS
            //TODO check for built-ins
            //TODO check for operators
            //add to symbol Map
            symbolMap.put(node.getLHS().getNodeString(), getAnalysisValue(analysis,this.index, node,LHS));
            
            
        }
        
    }
/******************************************************************************    
    public void makeExpression(TIRAbstractAssignStmt node)
    {
        
        int RHStype;
        String RHS;
        String Operand1, Operand2, prefix="";
        String ArgsListasString;
        RHStype = getRHSType(node);
        RHS = getRHSExp(node);
        //TODO
        
        Operand1 = getOperand1(node);
        Operand2 = getOperand2(node);
        
        ArrayList<String> Args = new ArrayList<String>();
        
        
        if (Operand2 != "" && Operand2 != null)
            prefix = ", ";
        switch(RHStype)
        {
        case 1:
            buf.append(Operand1+" "+RHS+" "+Operand2+" ;");
            break;
        case 2:
            buf.append(RHS+""+Operand1+" ;"); //TODO test this
            break;
        case 3:
            Args = GetArgs(node);
            ArgsListasString = GetArgsListasString(Args);
            buf.append(RHS+"("+ArgsListasString+");");
            break;
        case 4:
            Args = GetArgs(node);
            ArgsListasString = GetArgsListasString(Args);
            buf.append(RHS+"("+ArgsListasString+");");
            break;
        case 5:
            buf.append(RHS+";");
            break;
        default:
            buf.append("//is it an error?");    
            break;
        }
    }
******************************************************************************/    
    
//  public String getOperand1(TIRAbstractAssignStmt node){
//      if(node.getRHS().getChild(1).getNumChild() >= 1)
//          return node.getRHS().getChild(1).getChild(0).getNodeString();
//      else
//          return "";
//  }
//  
//  public String getOperand2(TIRAbstractAssignStmt node){
//      if(node.getRHS().getChild(1).getNumChild() >= 2)
//          return node.getRHS().getChild(1).getChild(1).getNodeString();
//      else
//          return "";
//  }
//  
//  public int getRHSType(TIRAbstractAssignStmt node){
//      String RHSName;
//      RHSName = node.getRHS().getVarName();
//      if (true==x10Map.isBinOperator(RHSName))
//      {
//          return 1; //"binop";
//      }
//      else if (true==x10Map.isUnOperator(RHSName))
//      {
//         return 2; //"unop";
//      }
//      
//      else if (true==x10Map.isX10DirectBuiltin(RHSName))
//      {
//          return 3; // "builtin";
//      }
//      else if (true == x10Map.isMethod(RHSName))
//      {
//          return 4; // "method";
//      }
//      else if (true==x10Map.isBuiltinConst(RHSName))
//      {
//          return 5; // "builtin";
//      }
//      else
//      {
//          return 0; // "default";
//      }
//  }
//  
//  public String getRHSExp(TIRAbstractAssignStmt node){
//      String RHS = null;
//      String RHSName;
//      RHSName = node.getRHS().getVarName();
//      if (true==x10Map.isBinOperator(RHSName))
//      {
//          RHS= x10Map.getX10BinOpMapping(RHSName);
//      }
//      else if (true==x10Map.isUnOperator(RHSName))
//      {
//         RHS= x10Map.getX10UnOpMapping(RHSName);
//      }
//      
//      else if (true==x10Map.isX10DirectBuiltin(RHSName))
//      {
//          RHS= x10Map.getX10DirectBuiltinMapping(RHSName);
//      }
//      else if (true==x10Map.isBuiltinConst(RHSName))
//      {
//          RHS= x10Map.getX10BuiltinConstMapping(RHSName);
//      }
//      else if (true == x10Map.isMethod(RHSName))
//      {
//          RHS= x10Mapping.getX10MethodMapping(RHSName);
//      }
//      else
//      {
//          RHS = "//cannot process it yet";
//      }
//      return RHS;
//  }
//  



        
//  ArrayList<String> GetArgs(TIRAbstractAssignStmt node)
//  {
//      ArrayList<String> Args = new ArrayList<String>();
//      int numArgs = node.getRHS().getChild(1).getNumChild();
//      for (int i=0;i<numArgs;i++)
//      {
//          Args.add(node.getRHS().getChild(1).getChild(i).getNodeString());
//      }
//      
//      return Args;
//  }
//  
    
//  String GetArgsListasString(ArrayList<String> Args)
//  {
//     String prefix ="";
//     String ArgListasString="";
//     for (String arg : Args)
//     {
//         ArgListasString = ArgListasString+prefix+arg;
//         prefix=", ";
//     }
//     return ArgListasString;
//  }
    
    
    /**********************HELPER METHODS***********************************/
    private String getLHSType(ValueAnalysis<?> analysis, int graphIndex,
            TIRAbstractAssignStmt node, String SymbolMapKey) {
        //node.getTargetName().getID()
        return analysis.getNodeList().get(graphIndex).getAnalysis().getOutFlowSets().get(node).get(SymbolMapKey).getMatlabClasses().toArray()[0].toString();
        
    }


    
    private static String getArgumentType(ValueAnalysis<?> analysis, int graphIndex, TIRFunction node, String paramID){
        //System.out.println(analysis.getNodeList().get(graphIndex).getAnalysis().getOutFlowSets().get(node).get(paramID).toString());//.getOutFlowSets().get(node).get(paramID).toString());

        return analysis.getNodeList().get(graphIndex).getAnalysis().getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
    }
    
    //get analysis value for Function node
    private static Collection<ClassReference> getAnalysisValue(ValueAnalysis<?> analysis, int graphIndex, TIRFunction node, String ID){
        return analysis.getNodeList().get(graphIndex).getAnalysis().getOutFlowSets().get(node).get(ID).getMatlabClasses();

        //return analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
    }
    
    
    //get analysis value for abstract assignment node
    private static Collection<ClassReference> getAnalysisValue(ValueAnalysis<?> analysis, int graphIndex, TIRAbstractAssignStmt node, String ID){
        return analysis.getNodeList().get(graphIndex).getAnalysis().getOutFlowSets().get(node).get(ID).getMatlabClasses();

        //return analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
    }

//  private String makeX10StringLiteral(String StrLit)
//  {
//      
//      if(StrLit.charAt(0)=='\'' && StrLit.charAt(StrLit.length()-1)=='\'')
//      {
//          
//          return "\""+(String) StrLit.subSequence(1, StrLit.length()-1)+"\"";
//          
//      }
//      else
//      return StrLit;
//  }
//  
    
//  private void printStatements(ast.List<ast.Stmt> stmts){
//      for(ast.Stmt stmt : stmts) {
//          int length = buf.length();
//          ((TIRNode)stmt).tirAnalyze(this);
//          if (buf.length() > length) buf.append('\n');
//      }
//  }


    
}



