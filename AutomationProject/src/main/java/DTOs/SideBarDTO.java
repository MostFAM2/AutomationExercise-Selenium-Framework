package DTOs;


public class SideBarDTO {

    /* =============================================================================== */
    /* --------------------------------- [ATTRIBUTES] -------------------------------- */
    /* =============================================================================== */
    private String firstCategory;
    private String firstSubcategory;
    private String firstExpectedTitle;
    private String secondCategory;
    private String secondSubcategory;
    private String secondExpectedTitle;

    private String firstBrand;
    private String firstBrandExpectedTitle;
    private String secondBrand;
    private String secondBrandExpectedTitle;



    /* ============================================================================ */
    /* -------------------------------- [GETTERS] --------------------------------- */
    /* ============================================================================ */

    /**
     * @return First main category to click
     */
    public String getFirstCategory()
    {
        return firstCategory;
    }

    /**
     * @return Subcategory under the first category
     */
    public String getFirstSubcategory()
    {
        return firstSubcategory;
    }

    /**
     * @return Expected page title after selecting the first subcategory
     */
    public String getFirstExpectedTitle()
    {
        return firstExpectedTitle;
    }

    /**
     * @return Second main category to click
     */
    public String getSecondCategory()
    {
        return secondCategory;
    }

    /**
     * @return Subcategory under the second category
     */
    public String getSecondSubcategory()
    {
        return secondSubcategory;
    }

    /**
     * @return Expected page title after selecting the second subcategory
     */
    public String getSecondExpectedTitle()
    {
        return secondExpectedTitle;
    }

    /**
     * @return First brand name to click in sidebar
     */
    public String getFirstBrand()
    {
        return firstBrand;
    }

    /**
     * @return Expected page title after selecting the first brand
     */
    public String getFirstBrandExpectedTitle()
    {
        return firstBrandExpectedTitle;
    }

    /**
     * @return Second brand name to click in sidebar
     */
    public String getSecondBrand()
    {
        return secondBrand;
    }

    /**
     * @return Expected page title after selecting the second brand
     */
    public String getSecondBrandExpectedTitle()
    {
        return secondBrandExpectedTitle;
    }

}