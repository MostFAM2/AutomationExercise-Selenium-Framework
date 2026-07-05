package DTOs;

public class ProductsDTO
{

    /* =============================================================================== */
    /* ----------------------------- [ATTRIBUTES] ------------------------------------ */
    /* =============================================================================== */

    private int quantity;

    private int firstProductIndex;
    private int secondProductIndex;
    private int thirdProductIndex;


    /* ============================================================================ */
    /* ----------------------------- [GETTERS] ------------------------------------ */
    /* ============================================================================ */

    /**
     *
     * @return quantity
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     *
     * @return Index for first product
     */
    public int getFirstProductIndex()
    {
        return firstProductIndex;
    }

    /**
     *
     * @return Index for second product
     */
    public int getSecondProductIndex()
    {
        return secondProductIndex;
    }

    /**
     *
     * @return Index for third product
     */
    public int getThirdProductIndex()
    {
        return thirdProductIndex;
    }
}


