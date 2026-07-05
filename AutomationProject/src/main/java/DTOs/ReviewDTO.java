package DTOs;


public class ReviewDTO
{
    /* ============================================================================ */
    /* ----------------------------- [ATTRIBUTES] --------------------------------- */
    /* ============================================================================ */
    private int productIndex;
    private String reviewName;
    private String reviewEmail;
    private String reviewText;


    /* ============================================================================ */
    /* ------------------------------- [GETTERS] ---------------------------------- */
    /* ============================================================================ */

    /**
     * @return Index of the product to click on (0-based)
     */
    public int getProductIndex()
    {
        return productIndex;
    }

    /**
     * @return Name to enter in the review form
     */
    public String getReviewName()
    {
        return reviewName;
    }

    /**
     * @return Email to enter in the review form
     */
    public String getReviewEmail()
    {
        return reviewEmail;
    }

    /**
     * @return Review message text to submit
     */
    public String getReviewText()
    {
        return reviewText;
    }
}