package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class OrderPlacedPage
{

    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;

    public OrderPlacedPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By orderPlacedTitleLocator = By.cssSelector("h2[data-qa='order-placed']");
    private final By continueButtonLocator = By.cssSelector("a[data-qa='continue-button']");
    private final By successMsgLocator = By.cssSelector("div.col-sm-9 > p");
    private final By downloadInvoiceLocator = By.cssSelector("a.btn.btn-default.check_out");

    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if 'Order Placed!' is displayed.
     * then we are in Order Placed Page
     *
     * @return true if order placed Title is visible
     */
    public boolean isOrderPlacedTitleDisplayed()
    {
        return fw.isDisplayed(orderPlacedTitleLocator);
    }

    /**
     * Checks if 'Order Placed!' success message is displayed.
     *
     * @return true if order placed message is visible
     */
    public boolean isOrderPlacedSuccessMsgDisplayed()
    {
        return fw.isDisplayed(successMsgLocator);
    }

    /**
     * Clicks Continue button to return to home page.
     *
     * @return HomePage object
     */
    public HomePage clickContinue()
    {
        fw.clickOnElement(continueButtonLocator);
        return new HomePage(fw);
    }

    /**
     * Clicks 'Download Invoice' button .
     */
    public void clickDownloadInvoice()
    {
        fw.clickOnElement(downloadInvoiceLocator);
    }


}
