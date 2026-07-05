package Tests;

import Utils.FrameWork;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class BaseTest
{
    protected String screenshotFolder = "src/main/resources/Screenshots";

    protected FrameWork fw;
    protected int timeoutInSeconds = 10;


    /**
     * Initializes the browser before any tests in the class run.
     * Runs once per test class. Supports parameterized browser selection,
     * headless mode , and maximize mode via configuration
     *
     * @param browser  Browser name: "chrome" (default), "firefox", or "edge"
     * @param headless Run browser in headless mode: true or false (default)
     * @param maximize Run browser in maximize mode: false or true (default)
     */
    @BeforeClass
    @Parameters({"browser", "headless", "maximize"})
    public void prepareBrowser(@Optional("chrome") String browser, @Optional("false") boolean headless, @Optional("true") boolean maximize)
    {
        fw = new FrameWork(browser, timeoutInSeconds, headless,  maximize);

        System.out.println("====================================================================================");
        System.out.println("🟢 [BASE TEST] Browser started: " + browser.toUpperCase() + " | Headless Mode: " + headless + " | Maximize Mode: " + maximize);
        System.out.println("====================================================================================");
    }


    /**
     * Quits the browser after all tests in the class have completed.
     * Runs once per test class. Cleans up the WebDriver session.
     */
    @AfterClass
    public void tearDown()
    {
        if (fw != null)
        {
            fw.quitBrowser();
            System.out.println("🔴 [BASE TEST] Browser closed.");
            System.out.println("================================");
        }
    }

    /**
     * Captures a screenshot if the test method fails.
     * Runs after each method.
     *
     * @param result TestNG result object containing test name and pass/fail status
     */
    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result)
    {
        if (result.getStatus() == ITestResult.FAILURE)
        {
            fw.takeScreenshot(screenshotFolder, result.getName());
            System.out.println("=================================================================================");
            System.out.println("[BASE TEST] Screenshot captured: " + result.getName() + " \nIn Folder: " + screenshotFolder);
            System.out.println("=================================================================================");


            try
            {
                File folder = new File(screenshotFolder);
                File[] files = folder.listFiles((dir, name) -> name.startsWith(result.getName()) && name.endsWith(".png"));

                if (files != null && files.length > 0)
                {
                    Allure.addAttachment("Screenshot", new FileInputStream(files[0]));
                }
            } catch (Exception e)
            {
                System.out.println("[BASE TEST] Could not attach screenshot: " + e.getMessage());
            }
            System.out.println("=================================================================================");
        }
    }

    @BeforeSuite
    public void setupAllureEnvironment()
    {
        try (FileWriter writer = new FileWriter("allure-results/environment.properties"))
        {
            writer.write("Browser=Chrome\n");
            writer.write("Environment=Production\n");
            writer.write("URL=https://automationexercise.com\n");
            writer.write("Java=" + System.getProperty("java.version") + "\n");
            writer.write("OS=" + System.getProperty("os.name") + "\n");
            writer.write("Selenium=4.43.0\n");
            writer.write("TestNG=7.12.0\n");
            writer.write("Pattern=Page Object Model\n");
            writer.write("TestData=JSON\n");
            writer.write("Reporting=Allure\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}