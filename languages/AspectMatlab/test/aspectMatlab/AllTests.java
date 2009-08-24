package aspectMatlab;

import junit.framework.Test;
import junit.framework.TestSuite;

/** Top-level test suite.  Contains all tests. */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		//$JUnit-BEGIN$
		suite.addTestSuite(AspectScannerTests.class);
		suite.addTestSuite(AspectParserPassTests.class);
		suite.addTestSuite(AspectParserFailTests.class);
		//$JUnit-END$
		return suite;
	}

}
