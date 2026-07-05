package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class TestCasesPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */

    private final FrameWork fw;
    public TestCasesPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By testCasesTitleLocator = By.xpath("//h2/b[contains(text() , 'Test Cases')]");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the Test Cases page title is visible.
     *
     * @return true if the title is displayed, false otherwise
     */
    public boolean isTestCasesPageDisplayed()
    {
        return fw.isDisplayed(testCasesTitleLocator);
    }

}
