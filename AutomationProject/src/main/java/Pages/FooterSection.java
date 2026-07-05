package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;


public class FooterSection
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    protected final FrameWork fw;

    public FooterSection(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By footerLocator = By.cssSelector("div.footer-widget");
    private final By subscriptionTextLocator = By.cssSelector("div.single-widget > h2");
    private final By subscriptionEmailLocator = By.cssSelector("input#susbscribe_email");
    private final By subscribeButtonLocator = By.cssSelector("button#subscribe");
    private final By subscriptionSuccessMsgLocator = By.cssSelector("div.alert-success.alert");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Scrolls to the footer section using JavaScript.
     */
    public void scrollToFooter()
    {
        fw.scrollToElementUsingJS(footerLocator);
    }

    /**
     * Checks if the 'SUBSCRIPTION' heading is displayed in the footer.
     *
     * @return true if subscription text is visible
     */
    public boolean isSubscriptionTextDisplayed()
    {
        return fw.isDisplayed(subscriptionTextLocator);
    }

    /**
     * Enters an email address into the subscription input field.
     *
     * @param email Email address to subscribe
     */
    public void enterEmailForSubscription(String email)
    {
        fw.sendString(subscriptionEmailLocator, email);
    }

    /**
     * Clicks the Subscribe button.
     */
    public void clickSubscribeButton()
    {
        fw.clickOnElement(subscribeButtonLocator);
    }

    /**
     * Checks if the subscription success message is visible.
     *
     * @return true if success message is displayed
     */
    public boolean isSubscriptionSuccessMsgDisplayed()
    {
        return fw.isDisplayed(subscriptionSuccessMsgLocator);
    }
}