package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class SideBarSection
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public SideBarSection(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */

    private final By sidebarLocator = By.cssSelector("div.left-sidebar");
    private final By brandsSectionLocator = By.cssSelector("div.brands_products");
    private final By pageTitleLocator = By.cssSelector("h2.title.text-center");



    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the sidebar is visible on the page.
     *
     * @return true if sidebar is displayed
     */
    public boolean isSidebarDisplayed()
    {
        return fw.isDisplayed(sidebarLocator);
    }

    /**
     * Checks if the Brands section is visible in the sidebar.
     *
     * @return true if Brands section is displayed
     */
    public boolean isBrandsSectionDisplayed()
    {
        return fw.isDisplayed(brandsSectionLocator);
    }

    /**
     * Clicks a main category (Women, Men, Kids) to expand it.
     *
     * @param category Category name (e.g., "Women", "Men", "Kids")
     */
    public void clickCategory(String category)
    {
        By categoryLocator = By.xpath("//a[@href='#" + category + "']");
        fw.clickOnElement(categoryLocator);
    }

    /**
     * Clicks a subcategory link by name under any expanded category.
     *
     * @param subcategory Subcategory name (e.g., "Dress", "Jeans", "Tops")
     */
    public void clickSubcategory(String subcategory)
    {
        By subcategoryLocator = By.xpath(
                "//div[@id='Women']//a[contains(text(),'" + subcategory + "')] | " +
                        "//div[@id='Men']//a[contains(text(),'" + subcategory + "')] | " +
                        "//div[@id='Kids']//a[contains(text(),'" + subcategory + "')]"
        );
        fw.clickOnElement(subcategoryLocator);
    }

    /**
     * Clicks a brand by name in the sidebar.
     *
     * @param brandName Brand name (e.g., "Polo", "Madame", "H&M")
     */
    public void clickBrand(String brandName)
    {
        fw.scrollToElementUsingJS(brandsSectionLocator);
        By brandLocator = By.cssSelector("a[href='/brand_products/" + brandName + "']");
        fw.clickOnElement(brandLocator);
    }

    /**
     * Gets the page title after navigating to a category or brand.
     *
     * @return Page title text (e.g., "WOMEN - DRESS PRODUCTS", "BRAND - POLO PRODUCTS")
     */
    public String getPageTitle()
    {
        return fw.readText(pageTitleLocator);
    }
}
