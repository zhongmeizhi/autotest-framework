package frame;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestNGListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        new ZY().takeScreenshot();
        String name = ZY.name;
        if (ZYCases.actual != null | ZYCases.expect != null) {
            Reporter.log("<Strong style=\"color: brown;\"> Expect:[" + ZYCases.expect + "] Actual:[" + ZYCases.actual + "]</strong> ");
        }
        ZYCases.clear();
        Reporter.log("<Strong style=\"color: red;\"> Fail! [" + ZY.description + "] SCREEN: </strong> ");
        Reporter.log("<a href=./snapshot/" + name + ".png/ target=_blank ><img style=\"width: 30px;\"  src=\"./snapshot/" + name + ".png/ \"></a>", true);
        // new ZY().openIndex();
        ZY.driver.quit();
    }

    // private void takeScreenShot(ITestResult tr) {
    // UITest b = (UITest) tr.getInstance();
    // WebDriver currentDirver = b.getDriver();
    // System.out.println(currentDirver.getTitle());
    // b.takeScreenShot();
    // }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
    }

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        ZYCases.clear();
    }

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        ZYCases.clear();
        Reporter.log("<Strong style=\"color: green;\"> AUTO TEST PASS ! </strong>");
        ZY.driver.quit();
    }

}