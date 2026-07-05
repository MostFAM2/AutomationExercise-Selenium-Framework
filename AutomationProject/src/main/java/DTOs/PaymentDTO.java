package DTOs;

public class PaymentDTO
{

    /* =============================================================================== */
    /* ----------------------------- [ATTRIBUTES] ------------------------------------ */
    /* =============================================================================== */
    private String cardName;
    private String cardNumber;
    private String cvc;
    private String expiryMonth;
    private String expiryYear;


    /* ============================================================================ */
    /* ----------------------------- [GETTERS] ------------------------------------ */
    /* ============================================================================ */

    /**
     * @return Name on card
     */
    public String getCardName()
    {
        return cardName;
    }

    /**
     * @return Card number
     */
    public String getCardNumber()
    {
        return cardNumber;
    }

    /**
     * @return CVC security code
     */
    public String getCvc()
    {
        return cvc;
    }

    /**
     * @return Expiry month
     */
    public String getExpiryMonth()
    {
        return expiryMonth;
    }

    /**
     * @return Expiry year
     */
    public String getExpiryYear()
    {
        return expiryYear;
    }
}