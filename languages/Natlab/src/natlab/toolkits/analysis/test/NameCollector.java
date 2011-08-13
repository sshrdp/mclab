package natlab.toolkits.analysis.test;

import java.util.*;
import ast.*;
import analysis.*;
import natlab.toolkits.analysis.HashSetFlowSet;

/**
 * @author Jesse Doherty
 */
public class NameCollector extends AbstractDepthFirstAnalysis<HashSetFlowSet<String>>
{
    private HashSetFlowSet<String> fullSet;
    private boolean inLHS = false;

    public NameCollector(ASTNode tree)
    {
        super(tree);
        fullSet = new HashSetFlowSet<String>();
    }

    public HashSetFlowSet<String> newInitialFlow()
    {
        return new HashSetFlowSet<String>();
    }

    public Set<String> getAllNames()
    {
        return fullSet.getSet();
    }
    public Set<String> getNames( AssignStmt node )
    {
        HashSetFlowSet<String> set = flowSets.get(node);
        if( set == null )
            return null;
        else
            return set.getSet();
    }

    public void caseName( Name node )
    {
        if( inLHS )
            currentSet.add( node.getID() );
    }

    public void caseAssignStmt( AssignStmt node )
    {
        inLHS = true;
        currentSet = newInitialFlow();
        analyze( node.getLHS() );
        flowSets.put(node,currentSet);
        fullSet.addAll( currentSet );
        inLHS = false;
    }
    public void caseParameterizedExpr( ParameterizedExpr node )
    {
        analyze(node.getTarget());
    }
    // Need other cases for completeness (dot, cell, ...)
}