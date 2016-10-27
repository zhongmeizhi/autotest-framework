package frame;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ TestNGListener.class })
public class ZY {
    static Properties CONFIG = null;
    static WebDriver driver = null;
    static String name = null;
    static String description = null;

    @DataProvider
    public String[][] db() {
        String[][] properties = getProperties();
        return properties;
    }

    @Test(dataProvider = "db")
    public void autoTest(String path, String excel, String sheet) {
        ZY.name = excel + "-" + sheet;
        Reporter.log("<strong style='color:green;'>Auto Start!</strong>");
        System.out.println(path + "-" + excel + "-" + sheet);

        // XXX get excel data
        Object[][] data = null;
        try {
            // data = MyExcel.getExcelData("", "TestData.xlsx", "Top100");
            data = MyExcel.getExcelData(path, excel + ".xlsx", sheet);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // XXX get driver and before
        getDriver();
        driver.manage().window().maximize();
        WebElement element = null;

        // XXX CASE
        stop: for (int i = 0; i < data.length + 1; i++) {
            String key = null;
            String value = null;
            String parameter = null;
            try {
                key = data[i][0].toString().toLowerCase().trim();
            }
            catch (Exception e) {
                key = "empty";
                Reporter.log("KEYS EMPTY!");
                break stop;
            }
            try {
                ZY.description = data[i][3].toString().toLowerCase().trim();
            }
            catch (Exception e) {
                ZY.description = "empty";
            }
            try {
                value = data[i][1].toString().trim();
            }
            catch (Exception e) {
                value = "empty";
            }
            if (value != "empty") {
                element = driver.findElement(By.xpath(value));
            }
            try {
                parameter = data[i][2].toString().toLowerCase().trim().replace("[", "").replace("]", "");
            }
            catch (Exception e) {
                parameter = "empty";
            }

            System.out.println(key + " " + value + " " + parameter + " " + description);
            // new ZYCase().use(key, value, parameter, element, parameter, driver);
            ZYCases.use(i, key, value, parameter, element, description, driver);
        }
        Reporter.log("<Strong style=\"color: green;\"> AUTO TEST PASS ! </strong>");
        driver.close();
    }

    // // quit is use in listener
    // @AfterMethod
    // public void quit() {
    // driver.quit();
    // }

    // // XXX get properties
    // String[][] getProperties() {
    // String filePath = "./auto.properties"; // 当前目录就是项目工程目录
    // File configFile = new File(filePath); // 配置文件路径
    // Properties properties = new Properties();
    // try {
    // FileInputStream config = new FileInputStream(configFile); // 用数据流读取文件
    // properties.load(config); // 加载配置文件
    // }
    // catch (Exception e) {
    // }
    // String number = null;
    // String path = null;
    // String excel = null;
    // String sheet = null;
    // try {
    // number = properties.getProperty("number");
    // }
    // catch (Exception e) {
    // }
    // try {
    // path = properties.getProperty("path");
    // }
    // catch (Exception e) {
    // }
    // try {
    // excel = properties.getProperty("excel");
    // }
    // catch (Exception e) {
    // }
    // try {
    // sheet = properties.getProperty("sheet");
    // }
    // catch (Exception e) {
    // }
    // System.out.println(number + " " + path + " " + excel + " " + sheet);
    // String[] excelValue = excel.split(",");
    // String[] sheetValue = sheet.split(",");
    // String[][] arrs = new String[3][Integer.parseInt(number)];
    // for (int i = 0; i < Integer.parseInt(number); i++) {
    // arrs[i][0] = path;
    // arrs[i][1] = excelValue[i];
    // arrs[i][2] = sheetValue[i];
    // System.out.println(path + "——" + excelValue[i] + "——" + sheetValue[i]);
    // }
    // return arrs;
    // }

    String[][] getProperties() {
        // XXX get excel data
        Object[][] data = null;
        String[][] str = null;
        try {
            // data = MyExcel.getExcelData("", "TestData.xlsx", "Top100");
            // data = MyExcel.getExcelData(path, excel + ".xlsx", sheet);
            data = MyExcel.getExcelData("", "properties.xlsx", "sheet1");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                str = new String[data.length][data[i].length];
                if (data[i][j] != null) {
                    str[i][j] = data[i][j].toString();
                }
            }
        }
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length; j++) {
                if (data[i][j] != null) {
                    str[i][j] = data[i][j].toString();
                }
            }
        }
        return str;
    }

    public static WebDriver getDriver() {
        // XXX driver options
        File pathBinary = new File("C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        FirefoxBinary binary = new FirefoxBinary(pathBinary);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("webdriver_accept_untrusted_certs", true);
        // profile.setAssumeUntrustedCertificateIssuer(false);
        //
        // ProfilesIni allProfiles = new ProfilesIni();
        // profile = allProfiles.getProfile("default");
        //
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("security.OCSP.enabled", 0);
        profile.setPreference("browser.download.dir", "/driverdownload");
        profile.setPreference(
                "browser.helperApps.neverAsk.saveToDisk",
                "application/msword,application/x-rar-compressed,application/octet-stream,application/csv,text/csv");
        driver = new FirefoxDriver(binary, profile);
        return driver;
    }

    private void takeScreenshot(String screenPath) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenPath));
        }
        catch (IOException e) {
            System.out.println("Screen shot error: " + screenPath);
        }
    }

    // XXX screen
    public void takeScreenshot() {
        // String screenName = String.valueOf(new Date().getTime()) + ".png";
        String screenName = String.valueOf(ZY.name + ".png");
        File dir = new File("test-output/snapshot");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String screenPath = dir.getAbsolutePath() + "/" + screenName;
        this.takeScreenshot(screenPath);
    }

    public void openIndex() {

    }
}
