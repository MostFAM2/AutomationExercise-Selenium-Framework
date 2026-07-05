package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class AccountDeletedPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public AccountDeletedPage(FrameWork fw)
    {
        this.fw = fw;
    }


    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By accountDeletedTitleLocator = By.xpath("//b[text()='Account Deleted!']");
    private final By continueButtonLocator = By.xpath("//a[@data-qa='continue-button']");



    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Verifies that 'Account Deleted!' message is visible.
     *
     * @return true if the success message is displayed, false otherwise
     */
    public boolean isAccountDeletedVisible()
    {
        return fw.isDisplayed(accountDeletedTitleLocator);
    }

    /**
     * Clicks Continue button and returns to home page.
     *
     * @return HomePage object
     */
    public HomePage clickContinue()
    {
        fw.clickOnElement(continueButtonLocator);
        return new HomePage(fw);
    }


}
