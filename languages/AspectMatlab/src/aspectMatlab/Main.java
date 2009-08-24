package aspectMatlab;

import aspectMatlab.ast.*;
import natlab.CommentBuffer;

import beaver.Parser;
import java.io.*;
import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		StringBuffer errors = new StringBuffer();
		
		//parse each file and put them in a list of Programs
		LinkedList<Program> programs = new LinkedList<Program>();
		LinkedList<Program> aspects = new LinkedList<Program>();

		for( String file : args ){
			Reader fileReader = new StringReader("");

			//treat as a natlab input, set fileReader to a new 
			//FileReader instance pointing to the current file
			try{
				fileReader = new FileReader( file );
			}catch(FileNotFoundException e){
				System.err.println("File "+file+" not found!\nAborting");
				System.exit(1);
			}
			
			//parse the file
			System.err.println("Parsing: " + file);
			Program prog = null;
			prog = parseFile( file,  fileReader, errors );

			//report errors
			if( errors.length() > 0 )
				System.err.print( errors.toString() );

			if( prog == null ){
				System.err.println("\nSkipping " + file);
				break;
			}

			if(prog instanceof Aspect) {
				System.err.println("Fetching Aspect Info: " + file);
				AspectsEngine.fetchAspectInfo(prog);
				//aspects.add(AspectsEngine.convertToFunctionList(prog));
				aspects.add(AspectsEngine.convertToClass(prog));
			} else {
				programs.add(prog);
			}
		}

		System.err.println("Matching and Weaving...");
		//Take all resulting Program nodes and place them in a
		//CompilationUnits instance
		CompilationUnits cu = new CompilationUnits();
		for( Program p : programs ){
			cu.addProgram( p );
			//p.aspectsCorrespondingFunctions();
			//p.aspectsWeave();
		}

		for( Program p : cu.getPrograms() ){
			//cu.addProgram( p );
			p.aspectsCorrespondingFunctions();
			//p.aspectsWeave();
		}
		
		for( Program p : cu.getPrograms() ){
			//cu.addProgram( p );
			//p.aspectsCorrespondingFunctions();
			p.aspectsWeave();
		}
		
		for( Program a : aspects ){
			cu.addProgram( a );
		}

		System.err.println("Pretty Printing...");
		System.out.println(cu.getPrettyPrinted());

		System.exit(0);
	}

	//Parse a given aspect file and return a Program ast node
	//if file does not exist or other problems, exit program
	private static Program parseFile(String fName, Reader file, StringBuffer errBuf )
	{
		AspectsParser parser = new AspectsParser();
		AspectsScanner scanner = null;
		CommentBuffer cb = new CommentBuffer();
	
		parser.setCommentBuffer(cb);
	
		try{
			scanner = new AspectsScanner( file );
			scanner.setCommentBuffer( cb );
			try{
	
				Program prog = (Program)parser.parse(scanner);
				if( parser.hasError() ){
					for( String error : parser.getErrors())
						errBuf.append(error + "\n");
					prog = null;
				}
				return prog;
	
			}catch(Parser.Exception e){
				errBuf.append(e.getMessage());
				for(String error : parser.getErrors()) {
					errBuf.append(error + "\n");
				}
				return null;
			} 
		}catch(FileNotFoundException e){
			errBuf.append( "File "+fName+" not found!\n" );
			return null;
		}
		catch(IOException e){
			errBuf.append( "Problem parsing "+fName + "\n");
			if( e.getMessage() != null )
				errBuf.append( e.getMessage() + "\n");
			return null;
		}
		finally{
			if(scanner != null) {
				//scanner.stop();
			}
		}
	}
}