package aspectMatlab;

import java.io.*;

import aspectMatlab.ast.Aspect;
import beaver.Parser;

/**
 * A utility for producing the output file corresponding to a given input file.
 * Note that the output should be checked manually before using it as a test.
 */
public class ParserTestTool {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java aspectMatlab.ParserTestTool {basename}");
            System.exit(1);
        }
        String basename = args[0];
        try {
            BufferedReader in = new BufferedReader(new FileReader(basename + ".in"));

            AspectsScanner scanner = new AspectsScanner(in);
            AspectsParser parser = new AspectsParser();
            PrintWriter out = new PrintWriter(new FileWriter(basename + ".out"));
            try {
            	Aspect actual = (Aspect) parser.parse(scanner);
                if(parser.hasError()) {
                    for(String error : parser.getErrors()) {
                        out.println(error);
                    }
                } else {
                    int startPos = actual.getStart();
                    int endPos = actual.getEnd();
                    out.println(Aspect.getLine(startPos) + " " + Aspect.getColumn(startPos));
                    out.println(Aspect.getLine(endPos) + " " + Aspect.getColumn(endPos));
                    out.print(actual.getStructureString());
                }
            } catch(Parser.Exception e) {
                for(String error : parser.getErrors()) {
                    out.println(error);
                }
                out.println(e.getMessage());
            }
            out.close();
            in.close();
            System.exit(0);
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
