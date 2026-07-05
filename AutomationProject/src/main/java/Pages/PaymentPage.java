package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class PaymentPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;

    public PaymentPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By cardNameLocator = By.cssSelector("input[data-qa='name-on-card']");
    private final By cardNumberLocator = By.cssSelector("input[data-qa='card-number']");
    private final By cvcLocator = By.cssSelector("input[data-qa='cvc']");
    private final By expiryMonthLocator = By.cssSelector("input[data-qa='expiry-month']");
    private final By expiryYearLocator = By.cssSelector("input[data-qa='expiry-year']");
    private final By placeOrderButtonLocator = By.cssSelector("button[data-qa='pay-button']");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */
    /**
     * Fills in all credit card payment details.
     *
     * @param cardName   Name on card
     * @param cardNumber 16-digit card number
     * @param cvc        3-digit CVC code
     * @param expiryMonth Expiry month (e.g., "12")
     * @param expiryYear  Expiry year (e.g., "2030")
     */
    public void enterPaymentDetails(String cardName, String cardNumber,
                                    String cvc, String expiryMonth, String expiryYear)
    {
        fw.sendString(cardNameLocator, cardName);
        fw.sendString(cardNumberLocator, cardNumber);
        fw.sendString(cvcLocator, cvc);
        fw.sendString(expiryMonthLocator, expiryMonth);
        fw.sendString(expiryYearLocator, expiryYear);
    }


    /**
     * Clicks the 'Place Order' button to complete the purchase.
     *
     * @return OrderPlacedPage for confirmation
     */
    public OrderPlacedPage clickPlaceOrder()
    {
        fw.clickOnElement(placeOrderButtonLocator);
        return new OrderPlacedPage(fw);
    }


}
