package frame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class ZYCases {

    static Map<String, String> map = new HashMap<String, String>();
    static String mapKey = null;
    static String mapValue = null;
    static String actual = null;
    static String expect = null;

    static void clear() {
        mapKey = null;
        mapValue = null;
        actual = null;
        expect = null;
    }

    public static void use(int i, String key, String value, String parameter, WebElement element, String description, WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 5);
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Alert alert = null;

        switch (key) {
        case "open":
            driver.get(parameter);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "click":
            wait.until(ExpectedConditions.visibilityOf(element)).click();
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            ;
            break;
        case "sendkeys":
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(parameter);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "hover":
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.moveToElement(element).build().perform();
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "wait":
            try {
                int time = Integer.parseInt(parameter) * 1000;
                Thread.sleep(time);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "exe":
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(parameter);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "waitele":
            new WebDriverWait(driver, Integer.parseInt(parameter) * 1000).until(ExpectedConditions.visibilityOf(element));
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "move":
            wait.until(ExpectedConditions.visibilityOf(element));
            js.executeScript("arguments[0].scrollIntoView();", element);
            actions.moveToElement(element).build().perform();
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "enter":
            actions.sendKeys(Keys.ENTER).build().perform();
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "select":
            Select select = new Select(element);
            select.selectByValue(parameter);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "delete":
            js.executeScript("arguments[0].style.display='none';", element);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "switch":
            String parentWindowHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String a : handles) {
                if (!a.equals(parentWindowHandle)) {
                    driver.switchTo().window(a); // switch to new window
                }
            }
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "iframe":
            new WebDriverWait(driver, 5).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "accept":
            alert = driver.switchTo().alert();
            alert.accept();
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "unaccept":
            alert = driver.switchTo().alert();
            alert.dismiss();
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        // assert
        case "attr":
            mapKey = parameter.split("=")[0];
            String attribute = parameter.split("=")[1];
            mapValue = element.getAttribute(attribute);
            map.put(mapKey, mapValue);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "text":
            mapKey = parameter;
            mapValue = element.getText();
            map.put(mapKey, mapValue);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "textis":
            actual = element.getText();
            expect = parameter;
            Assert.assertEquals(actual, expect);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "titleis":
            actual = driver.getTitle();
            expect = parameter;
            Assert.assertEquals(actual, expect);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "assert":
            String p1 = parameter.split("=")[0];
            String p2 = parameter.split("=")[1];
            actual = map.get(p1);
            expect = map.get(p2);
            Assert.assertEquals(actual, expect);
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "contain":
            String p3 = parameter.split(">")[0];
            String p4 = parameter.split(">")[1];
            actual = map.get(p3);
            expect = p4;
            Assert.assertTrue(actual.contains(expect));
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "exist":
            Assert.assertTrue(element.isDisplayed());
            Reporter.log("<strong style=\"background: green;\">Step: " + (i + 1) + " </strong>:" + description + ". ");
            break;
        case "end":
            try {
                Thread.sleep(999999999);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        default:
            Reporter.log(" _ ZHU YUAN KEY IS EMPTY !");
            break;
        }
    }
}
