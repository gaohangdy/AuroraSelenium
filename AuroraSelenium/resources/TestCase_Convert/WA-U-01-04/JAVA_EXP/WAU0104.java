import junit.framework.Test;
import junit.framework.TestSuite;

public class LoginWAU0104Test {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(D01001001.class);
    suite.addTestSuite(D01001002.class);
    suite.addTestSuite(D01001003.class);
    suite.addTestSuite(D01003006.class);
    suite.addTestSuite(D01003007.class);
    suite.addTestSuite(D01004010.class);
    suite.addTestSuite(D01004011.class);
    suite.addTestSuite(D01004012.class);
    suite.addTestSuite(D01004013.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
