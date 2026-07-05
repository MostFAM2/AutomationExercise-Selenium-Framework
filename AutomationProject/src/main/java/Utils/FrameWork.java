package Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FrameWork
{

    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */

    private final WebDriver driver;
    private final Actions actions;
    private final WebDriverWait wait;

    protected int timeoutInSeconds;

    /**
     * Initializes WebDriver, Actions, and WebDriverWait based on browser type.
     *
     * @param browserName browser type (chrome, firefox, edge)
     * @param timeoutInSeconds explicit wait timeout in seconds
     * @param headlessMode configure headlessMode
     * @param maximizeMode configure maximizeMode
     */
    public FrameWork(String browserName, int timeoutInSeconds, boolean headlessMode, boolean maximizeMode) {
        this.timeoutInSeconds = timeoutInSeconds;

        switch (browserName.toLowerCase())
        {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headlessMode)
                    firefoxOptions.addArguments("--headless");
                if (maximizeMode)
                    firefoxOptions.addArguments("--start-maximized");

                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();

                if (headlessMode)
                    edgeOptions.addArguments("--headless");
                if (maximizeMode)
                    edgeOptions.addArguments("--start-maximized");

                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();

                if (headlessMode)
                    chromeOptions.addArguments("--headless");
                if (maximizeMode)
                    chromeOptions.addArguments("--start-maximized");

                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutInSeconds));
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        actions = new Actions(driver);
    }


    /* =================================================================================== */
    /* ----------------------------- [HELPER METHODS] ------------------------------------ */
    /* =================================================================================== */

    /**
     * Waits until element becomes visible in DOM and UI.
     */
    public WebElement waitForVisibility(By locator)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until element is clickable (visible + enabled).
     */
    public WebElement waitForClickable(By locator)
    {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until element is present in DOM.
     */
    public WebElement waitForPresence(By locator)
    {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until URL matches expected value.
     */
    public void waitForUrl(String url)
    {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Waits until the browser page title matches the expected value.
     *
     * @param title the expected page title
     */
    public void waitForTitle(String title)
    {
        wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Waits until the specified text is present inside a web element.
     *
     * @param locator the locator used to identify the element
     * @param text the expected text to be present inside the element
     */
    public void waitForText(By locator, String text)
    {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Waits until the specified element becomes invisible on the page.
     * Commonly used for loaders, spinners, or temporary overlays that block UI interaction.
     *
     * @param locator the locator used to identify the element
     */
    public void waitForInvisibility(By locator)
    {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Performs a safe click operation on a web element.
     * The method first attempts a standard Selenium click. If the click fails due to
     * issues such as overlays, intercepted elements, or timing problems, it falls
     * back to a JavaScript-based click execution.
     *
     * @param locator the locator used to identify the target element
     */
    public void safeClick(By locator)
    {
        try
        {
            waitForClickable(locator).click();
        }
        catch (Exception e)
        {
            clickUsingJS(locator);
        }
    }

    /* =================================================================================== */
    /* ------------------------------- [BROWSER CONTROL] --------------------------------- */
    /* =================================================================================== */

    /**
     * Move a single "item" forward in the browser's history.
     * Does nothing if we are on the latest page viewed.
     */
    public void moveForward()
    {
        driver.navigate().forward();
    }

    /**
     * Move back a single "item" in the browser's history.
     * Does nothing if we are on the latest page viewed.
     */
    public void moveBack()
    {
        driver.navigate().back();
    }

    /**
     * Refreshes current browser page.
     */
    public void refreshPage()
    {
        driver.navigate().refresh();
    }

    /**
     * Closes current browser window.
     */
    public void closeBrowser()
    {
        driver.close();
    }

    /**
     * Quits entire browser session.
     */
    public void quitBrowser()
    {
        driver.quit();
    }


    /* =================================================================================== */
    /* ------------------------------- [ELEMENT STATES] ---------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if element is visible on page.
     *
     * @param locator element locator
     * @return true if visible, false otherwise
     */
    public boolean isDisplayed(By locator)
    {
        try
        {
            return waitForVisibility(locator).isDisplayed();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Checks if element is enabled for interaction.
     *
     * @param locator element locator
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled(By locator)
    {
        try
        {
            return waitForVisibility(locator).isEnabled();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Checks if element is selected (checkbox/radio state).
     *
     * @param locator element locator
     * @return true if selected, false otherwise
     */
    public boolean isSelected(By locator)
    {
        try
        {
            return waitForVisibility(locator).isSelected();
        }
        catch (Exception e)
        {
            return false;
        }
    }


    /* =================================================================================== */
    /* ------------------------------- [ALERT HANDLING] ---------------------------------- */
    /* =================================================================================== */

    /**
     * Accepts browser alert popup.
     */
    public void acceptAlert()
    {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /**
     * Dismisses browser alert popup.
     */
    public void dismissAlert()
    {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    /**
     * Retrieves text from browser alert.
     *
     * @return alert message
     */
    public String getAlertText()
    {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    /**
     * Sends Key/Text to browser alert.
     *
     * @param text text to be written in alert popup
     */
    public void sendKeyToAlert(String text)
    {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }



    /* =================================================================================== */
    /* ---------------------------- [FRAME/WINDOWS HANDLING] ---------------------------- */
    /* =================================================================================== */

    /**
     * Switches driver context to iframe.
     *
     * @param locator iframe locator
     */
    public void switchToFrame(By locator)
    {
        driver.switchTo().frame(waitForVisibility(locator));
    }

    /**
     * Switches back to main page content.
     */
    public void switchToDefaultContent()
    {
        driver.switchTo().defaultContent();
    }

    /**
     * Switches back to parent frame page content.
     */
    public void switchToParentFrame()
    {
        driver.switchTo().parentFrame() ;
    }

    /**
     * Opens to new window.
     */
    public void openNewWindow()
    {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    /**
     * Opens to new TAB.
     */
    public void openNewTab()
    {
        driver.switchTo().newWindow(WindowType.TAB);
    }


    /* ================================================================================= */
    /* -------------------------------- [JS UTILITIES] --------------------------------- */
    /* ================================================================================= */

    /**
     * Clicks element using JavaScript (bypasses UI issues).
     *
     * @param locator element locator
     */
    public void clickUsingJS(By locator)
    {
        WebElement element = waitForPresence(locator);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Clicks element using JavaScript (Overloading).
     *
     * @param element WebElement
     */
    public void clickUsingJS(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls element into center view using JavaScript.
     *
     * @param locator element locator
     */
    public void scrollToElementUsingJS(By locator)
    {
        WebElement element = waitForVisibility(locator);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});",element);
    }

    /**
     * Scrolls to an element found by locator at a specific index.
     * Useful for lists where you need the nth element.
     *
     * @param locator Element locator
     * @param index   Index of the element (0-based)
     */
    public void scrollToElementByIndex(By locator, int index)
    {
        List<WebElement> elements = driver.findElements(locator);
        if (index < elements.size())
        {
            WebElement element = elements.get(index);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});", element);
        }
    }

    /**
     * Scrolls to a specific WebElement using JavaScript.
     * Used when working with elements from a list.
     *
     * @param element The WebElement to scroll to
     */
    public void scrollToElementUsingJS_ByElement(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});", element);
    }


    /* ================================================================================= */
    /* -------------------------------- [SAFE INPUT] ----------------------------------- */
    /* ================================================================================= */


    /**
     * Clears input field and enters text.
     *
     * @param locator input field locator
     * @param text value to enter
     */
    public void clearAndType(By locator, String text)
    {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /* =================================================================================== */
    /* ---------------------------- [DROPDOWN HANDLING] ---------------------------------- */
    /* =================================================================================== */

    /**
     * Selects an option from a <select> dropdown by visible text.
     *
     * @param locator The dropdown element locator
     * @param visibleText The visible option text to select
     */
    public void selectByVisibleText(By locator, String visibleText)
    {
        Select dropdown = new Select(waitForVisibility(locator));
        dropdown.selectByVisibleText(visibleText);
    }

    /**
     * Selects an option from a <select> dropdown by value attribute.
     *
     * @param locator The dropdown element locator
     * @param value The value attribute of the option to select
     */
    public void selectByValue(By locator, String value)
    {
        Select dropdown = new Select(waitForVisibility(locator));
        dropdown.selectByValue(value);
    }

    /**
     * Selects an option from a <select> dropdown by index (0-based).
     *
     * @param locator The dropdown element locator
     * @param index The index of the option (0 for first option)
     */
    public void selectByIndex(By locator, int index)
    {
        Select dropdown = new Select(waitForVisibility(locator));
        dropdown.selectByIndex(index);
    }


    /* =================================================================================== */
    /* ------------------------------ [BASIC OPERATIONS] --------------------------------- */
    /* =================================================================================== */

    /**
     * Navigates to URL and waits for page load.
     *
     * @param url target URL
     */
    public void goToUrl(String url)
    {
        driver.get(url);
    }

    /**
     * Retrieves the current URL of the active browser page.
     *
     * @return the current page URL as a String
     */
    public String getCurrentUrl()
    {
        return driver.getCurrentUrl();
    }

    /**
     * Retrieves the title of the current browser page.
     *
     * @return the page title as a String
     */
    public String getPageTitle()
    {
        return driver.getTitle();
    }


    /**
     * Finds all elements matching the given locator.
     *
     * @param locator Element locator
     * @return List of matching WebElements
     */
    public List<WebElement> findElements(By locator)
    {
        return driver.findElements(locator);
    }

    /**
     * Hovers the mouse over a WebElement.
     *
     * @param element The WebElement to hover over
     */
    public void hoverOverElement(WebElement element)
    {
        actions.moveToElement(element).perform();
    }


    /**
     * Sends text to element.
     *
     * @param locator element locator
     * @param text input text
     */
    public void sendString(By locator, String text)
    {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Reads text from element.
     *
     * @param locator element locator
     * @return visible text
     */
    public String readText(By locator)
    {
        return waitForVisibility(locator).getText();
    }

    /**
     * Clicks on element safely.
     *
     * @param locator element locator
     */
    public void clickOnElement(By locator)
    {
        safeClick(locator);
    }

    /**
     * Performs right-click action on element.
     *
     * @param locator element locator
     */
    public void contextClick(By locator)
    {
        actions.moveToElement(waitForVisibility(locator)).contextClick().perform();
    }

    /**
     * Drags source element and drops it on target element.
     *
     * @param srcLocator source element
     * @param dstLocator destination element
     */
    public void dragAndDrop(By srcLocator, By dstLocator)
    {
        actions.dragAndDrop(waitForVisibility(srcLocator), waitForVisibility(dstLocator)).perform();
    }

    /**
     * Scrolls to element using Actions class.
     *
     * @param locator element locator
     */
    public void scrollToElement(By locator)
    {
        actions.scrollToElement(waitForVisibility(locator)).perform();
    }

    /**
     * Scrolls page by pixel amount.
     *
     * @param x horizontal scroll
     * @param y vertical scroll
     */
    public void scrollByAmount(int x, int y)
    {
        actions.scrollByAmount(x, y).perform();
    }


    /**
     * Captures a screenshot of the current browser window and saves it to the specified folder.
     * The screenshot file name is constructed by appending a timestamp to the provided
     * {@code screenshotName} in the format: {@code screenshotName_yyyyMMdd_HHmmss.png}.
     * This method uses Selenium's {@link TakesScreenshot} interface
     * to capture the screenshot and {@link FileHandler} to save it.
     *
     * @param folderPath     the absolute or relative path to the directory where the screenshot
     *                       will be saved. The directory must exist.
     *
     * @param screenshotName the base name of the screenshot file (without extension or timestamp).
     *
     * @throws RuntimeException if an error occurs during screenshot capture or file saving.
     *
     */
    public void takeScreenshot(String folderPath, String screenshotName) {
        try
        {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timeStamp + ".png";
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(folderPath + File.separator + fileName);
            FileHandler.copy(srcFile, destFile);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Captures a screenshot of a WebElement identified by the given locator
     * and saves it to the specified folder.
     * The screenshot file name is constructed by appending a timestamp to the provided
     * {@code screenshotName} in the format:
     * {@code screenshotName_yyyyMMdd_HHmmss.png}.
     *
     * @param locator        the {@link By} locator used to find the target WebElement.
     * @param folderPath     the path where the screenshot will be saved (must exist).
     * @param screenshotName the base name of the screenshot file (without extension or timestamp).
     *
     * @throws RuntimeException if the element is not found or the screenshot fails.
     *
     */
    public void takeElementScreenshot(By locator, String folderPath, String screenshotName) {
        try
        {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timeStamp + ".png";

            File srcFile = waitForVisibility(locator).getScreenshotAs(OutputType.FILE);
            File destFile = new File(folderPath + File.separator + fileName);
            FileHandler.copy(srcFile, destFile);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Implements a FluentWait mechanism for dynamically loaded elements.
     * This method repeatedly polls the DOM at a fixed interval until the element
     * is found or the timeout is reached. It ignores exceptions during polling,
     * making it suitable for unstable or slow-loading UI components.
     *
     * @param locator the locator used to identify the target element
     * @return the located WebElement once found
     */
    public WebElement fluentWait(By locator)
    {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(Exception.class)
                .until(d -> d.findElement(locator));
    }

    /**
     * Hard wait for the specified seconds.
     * Use only when explicit waits are not sufficient.
     *
     * @param seconds Seconds to pause
     */
    public void hardWait(int seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000L);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

}