package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class ProductDetailsPage
{

    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;

    public ProductDetailsPage(FrameWork fw)
    {
        this.fw = fw;
    }

    private final static String PRODUCT_DETAILS_URL_PREFIX  = "https://automationexercise.com/product_details/";


    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By productNameLocator = By.cssSelector("div.product-information > h2 ");
    private final By productCategoryLocator = By.xpath("//div[@class='product-information']/p[contains(text(), 'Category')]");
    private final By productPriceLocator = By.cssSelector("div.product-information>span>span");
    private final By productAvailabilityLocator = By.xpath("//div[@class=\"product-information\"]/p/b[contains(text(), 'Availability:')]");
    private final By productConditionLocator = By.xpath("//div[@class=\"product-information\"]/p/b[contains(text(), 'Condition:')]");
    private final By productBrandLocator = By.xpath("//div[@class=\"product-information\"]/p/b[contains(text(), 'Brand:')]");

    private final By productQuantityLocator = By.cssSelector("input#quantity");
    private final By addToCartLocator = By.cssSelector("button.btn.btn-default.cart");

    private final By viewCartLocator = By.cssSelector("p.text-center a[href=\"/view_cart\"]");

    private final By writeReviewTitleLocator = By.xpath("//a[contains(text(),'Write Your Review')]");

    private final By reviewNameLocator = By.id("name");
    private final By reviewEmailLocator = By.id("email");
    private final By reviewTextLocator = By.id("review");
    private final By reviewSubmitButtonLocator = By.id("button-review");
    private final By reviewSuccessMsgLocator = By.cssSelector("div.alert-success.alert span");

    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the current URL is a valid product details page.
     *
     * @return true if URL starts with the product details prefix
     */
    public boolean isProductDetailsPageDisplayed()
    {
        return fw.getCurrentUrl().startsWith(PRODUCT_DETAILS_URL_PREFIX);
    }

    /**
     * Checks if the product name is visible on the details page.
     *
     * @return true if product name is displayed
     */
    public boolean isProductNameDisplayed()
    {
        return fw.isDisplayed(productNameLocator);
    }

    /**
     * Checks if the product category is visible.
     *
     * @return true if category is displayed
     */
    public boolean isProductCategoryDisplayed()
    {
        return fw.isDisplayed(productCategoryLocator);
    }

    /**
     * Checks if the product price is visible.
     *
     * @return true if price is displayed
     */
    public boolean isProductPriceDisplayed()
    {
        return fw.isDisplayed(productPriceLocator);
    }

    /**
     * Checks if the product availability is visible.
     *
     * @return true if availability is displayed
     */
    public boolean isProductAvailabilityDisplayed()
    {
        return fw.isDisplayed(productAvailabilityLocator);
    }

    /**
     * Checks if the product condition is visible.
     *
     * @return true if condition is displayed
     */
    public boolean isProductConditionDisplayed()
    {
        return fw.isDisplayed(productConditionLocator);
    }

    /**
     * Checks if the product brand is visible.
     *
     * @return true if brand is displayed
     */
    public boolean isProductBrandDisplayed()
    {
        return fw.isDisplayed(productBrandLocator);
    }

    /**
     * Sets the product quantity by clearing the field and entering the new value.
     *
     * @param quantity The desired quantity (e.g., 4)
     */
    public void setProductQuantityTo(int quantity)
    {
        fw.sendString(productQuantityLocator, String.valueOf(quantity));
    }

    /**
     * Clicks the 'Add to Cart' button on the product details page.
     */
    public void clickAddToCart()
    {
        fw.clickOnElement(addToCartLocator);
    }

    /**
     * Clicks the 'View Cart' link in the popup after adding to cart.
     *
     * @return CartPage object for cart verification
     */
    public CartPage clickViewCart()
    {
        fw.clickOnElement(viewCartLocator);
        return new CartPage(fw);
    }


    /**
     * Checks if 'Write Your Review' section is visible.
     *
     * @return true if review section is displayed
     */
    public boolean isWriteYourReviewDisplayed()
    {
        return fw.isDisplayed(writeReviewTitleLocator);
    }

    /**
     * Fills the review form with reviewer details.
     *
     * @param name  Reviewer name
     * @param email Reviewer email
     * @param text  Review message
     */
    public void fillReviewForm(String name, String email, String text)
    {
        fw.sendString(reviewNameLocator, name);
        fw.sendString(reviewEmailLocator, email);
        fw.sendString(reviewTextLocator, text);
    }

    /**
     * Clicks the Submit button to post the review.
     */
    public void clickReviewSubmit()
    {
        fw.clickOnElement(reviewSubmitButtonLocator);
    }

    /**
     * Checks if the review success message is visible.
     *
     * @return true if success message is displayed
     */
    public boolean isReviewSuccessMessageDisplayed()
    {
        return fw.isDisplayed(reviewSuccessMsgLocator);
    }


}
