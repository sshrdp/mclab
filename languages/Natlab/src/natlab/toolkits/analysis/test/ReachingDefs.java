// =========================================================================== //
//                                                                             //
// Copyright 2011 Jesse Doherty and McGill University.                         //
//                                                                             //
//   Licensed under the Apache License, Version 2.0 (the "License");           //
//   you may not use this file except in compliance with the License.          //
//   You may obtain a copy of the License at                                   //
//                                                                             //
//       http://www.apache.org/licenses/LICENSE-2.0                            //
//                                                                             //
//   Unless required by applicable law or agreed to in writing, software       //
//   distributed under the License is distributed on an "AS IS" BASIS,         //
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  //
//   See the License for the specific language governing permissions and       //
//  limitations under the License.                                             //
//                                                                             //
// =========================================================================== //

package natlab.toolkits.analysis.test;

import java.util.HashSet;
import java.util.Set;

import natlab.toolkits.analysis.HashMapFlowMap;
import natlab.toolkits.analysis.Merger;
import natlab.toolkits.analysis.Mergers;
import natlab.utils.NodeFinder;
import analysis.AbstractSimpleStructuralForwardAnalysis;
import ast.ASTNode;
import ast.AssignStmt;
import ast.Function;
import ast.Name;
import ast.Script;
import ast.Stmt;

/**
 * A simple forward analysis example, computing reaching definitions. 
 *
 * @author Jesse Doherty
 */
public class ReachingDefs 
    extends AbstractSimpleStructuralForwardAnalysis<HashMapFlowMap<String,Set<AssignStmt>>>
{
	public static final AssignStmt UNDEF = new AssignStmt();
	public static final AssignStmt PARAM = new AssignStmt();
	public static final AssignStmt GLOBAL = new AssignStmt();

	private Merger<Set<AssignStmt>> merger = Mergers.union();

    private HashMapFlowMap<String,Set<AssignStmt>> startMap;
    private NameCollector nameCollector;

    public ReachingDefs( ASTNode<?> tree )
    {
        super(tree);
//        DEBUG=true;
        startMap = new HashMapFlowMap<String,Set<AssignStmt>>(merger);
        nameCollector= new NameCollector(tree);
        nameCollector.analyze();
        for( String var : nameCollector.getAllNames() )
            startMap.put( var, new HashSet<AssignStmt>() );
    }
    
    /**
     * Defines the merge operation for this analysis.
     *
     * This implementation uses the union method defined by {@link
     * AbstrcatFlowMap}. Note that the union method deals with the
     * cases where {@literal in1==in2}, {@literal in1==out} or
     * {@literal in2==out}. 
     */
    public void merge( HashMapFlowMap<String,Set<AssignStmt>> in1,
                       HashMapFlowMap<String,Set<AssignStmt>> in2,
                       HashMapFlowMap<String,Set<AssignStmt>> out )
    {
    	Set<String> keys = new HashSet<String>(in1.keySet());
        keys.addAll(in2.keySet());
        for (String s: keys){
        	Set<AssignStmt> res = new HashSet<AssignStmt>();
    		res.addAll(in1.get(s));
    		res.addAll(in2.get(s));
        	out.put(s, res);
        }
    }

    /** 
     * Creates a copy of the FlowMap with copies of the contained set.
     */
    public void copy( HashMapFlowMap<String,Set<AssignStmt>> in,
                      HashMapFlowMap<String,Set<AssignStmt>> out )
    {
        if( in == out )
            return;
        out.clear();
        for( String i : in.keySet() )
            out.put(i, new HashSet<AssignStmt>(in.get(i)));
    }

    /**
     * Creates a copy of the given flow-map and returns it.
     */
    public HashMapFlowMap<String,Set<AssignStmt>> 
        copy( HashMapFlowMap<String,Set<AssignStmt>> in )
    {
        HashMapFlowMap<String,Set<AssignStmt>> out = new HashMapFlowMap<String,Set<AssignStmt>>();
        copy(in, out);
        return out;
    }
    
    public void caseFunction(Function f)
    {
    	currentOutSet = newInitialFlow();
    	currentInSet = currentOutSet;
        for (Name n: NodeFinder.find(f, Name.class)){
        	Set<AssignStmt> s = new HashSet<AssignStmt>();
        	s.add(UNDEF);
        	currentOutSet.put(n.getID(), s);
        }
        for (Name inputArg: f.getInputParamList()){
        	Set<AssignStmt> s = new HashSet<AssignStmt>();
        	s.add(PARAM);
        	currentOutSet.put(inputArg.getID(), s);
        }
        caseASTNode(f.getStmts());
        outFlowSets.put(f, currentOutSet);
    }


    public void caseScript(Script f)
    {
    	currentOutSet = newInitialFlow();
    	currentInSet = currentOutSet;
        for (Name n: NodeFinder.find(f, Name.class)){
        	Set<AssignStmt> s = new HashSet<AssignStmt>();
        	s.add(UNDEF);
        	currentOutSet.put(n.getID(), s);
        }
        caseASTNode(f.getStmts());
        outFlowSets.put(f, currentOutSet);
    }

    
    
    public HashMapFlowMap<String,Set<AssignStmt>> newInitialFlow()
    {
        return copy(startMap);
    }

    public void caseAssignStmt( AssignStmt node )
    {
        inFlowSets.put( node, currentInSet );
        currentOutSet = copy(currentInSet);
        Set<String> defVars = nameCollector.getNames( node );

        for( String n : defVars ){
            Set<AssignStmt> newDefSite = new HashSet<AssignStmt>();
            newDefSite.add( node );
            currentOutSet.put( n, newDefSite );
        }
        outFlowSets.put(node, currentOutSet);
    }

    public void caseStmt( Stmt node )
    {
        inFlowSets.put( node, currentInSet );
        currentOutSet = currentInSet;
        outFlowSets.put( node, currentOutSet );
    }

}
