package DTOs;


public class BonusDTO
{

    /* =============================================================================== */
    /* ----------------------------- [ATTRIBUTES] ------------------------------------ */
    /* =============================================================================== */
    private String weakPassword;
    private String invalidEmail;
    private int zeroQuantity;

    /* ============================================================================ */
    /* ----------------------------- [GETTERS] ------------------------------------ */
    /* ============================================================================ */

    /**
     * @return Weak password for security validation test
     */
    public String getWeakPassword()
    {
        return weakPassword;
    }

    /**
     * @return Invalid email format for validation test
     */
    public String getInvalidEmail()
    {
        return invalidEmail;
    }

    /**
     * @return Zero quantity for cart validation test
     */
    public int getZeroQuantity()
    {
        return zeroQuantity;
    }
}