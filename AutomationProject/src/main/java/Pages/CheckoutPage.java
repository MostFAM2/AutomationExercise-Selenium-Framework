package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class CheckoutPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;

    public CheckoutPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By addressDetailsLocator = By.xpath("//div[@class='step-one']/h2[contains(text(), 'Address Details')]");
    private final By reviewOrderLocator = By.xpath("//div[@class='step-one']/h2[contains(text(), 'Review Your Order')]");
    private final By placeOrderButtonLocator = By.cssSelector("a[href=\"/payment\"]");

    private final By orderMsgLocator = By.cssSelector("div#ordermsg > textarea.form-control");

    private final By deliveryAddressLocator = By.cssSelector("ul#address_delivery");
    private final By billingAddressLocator = By.cssSelector("ul#address_invoice");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */
    /**
     * Checks if Address Details Title is visible.
     *
     * @return true if ('Address Details' visible), false otherwise
     */
    public boolean isAddressDetailsDisplayed()
    {
        return fw.isDisplayed(addressDetailsLocator);
    }

    /**
     * Checks if Review Your Order Title is visible.
     *
     * @return true if ('Review Your Order' visible), false otherwise
     */
    public boolean isReviewYourOrderDisplayed()
    {
        return fw.isDisplayed(reviewOrderLocator);
    }

    /**
     * Writes Message/Comment on the order if needed.
     */
    public void writeOrderMessage(String message)
    {
        fw.sendString(orderMsgLocator, message);
    }

    /**
     * Clicks on the Place Order Button .
     * Navigates to the Payment page.
     *
     * @return PaymentPage Object
     */
    public PaymentPage clickPlaceOrderButton()
    {
        fw.clickOnElement(placeOrderButtonLocator);
        return new PaymentPage(fw);
    }

    /**
     * Gets the delivery address text displayed on the checkout page.
     *
     * @return Full delivery address as string
     */
    public String getDeliveryAddressText()
    {
        return fw.readText(deliveryAddressLocator);
    }

    /**
     * Gets the billing address text displayed on the checkout page.
     *
     * @return Full billing address as string
     */
    public String getBillingAddressText()
    {
        return fw.readText(billingAddressLocator);
    }

}
