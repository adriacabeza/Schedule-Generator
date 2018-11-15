/**
 * @author Aina Garcia
 */

package drivers;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.CtrlDominiTest;

public class DriverCtrlDominiJUnit {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CtrlDominiTest.class);

        for (Failure failure: result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) System.out.println("Tots els tests passats");
    }
}
