package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class AccountCreatedPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public AccountCreatedPage(FrameWork driver)
    {
        fw = driver;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By accountCreatedTitleLocator = By.cssSelector("h2[data-qa=\"account-created\"]");
    private final By continueBottonLocator = By.cssSelector("a[data-qa=\"continue-button\"]");

    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Verifies that 'Account Created!' message is visible.
     *
     * @return true if the success message is displayed, false otherwise
     */
    public boolean isAccountCreatedDisplayed()
    {
        return fw.isDisplayed(accountCreatedTitleLocator);
    }

    /**
     * Clicks Continue button and returns to home page.
     *
     * @return HomePage object
     */
    public HomePage clickContinueButton()
    {
        fw.clickOnElement(continueBottonLocator);
        return new HomePage(fw);
    }

}
