package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends ProductPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;

    public HomePage(FrameWork driver)
    {
        super(driver);
        fw = driver;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By homePageLogoLocator = By.cssSelector("div.logo");
    private final By productLocator = By.cssSelector("a[href=\"/products\"]");
    private final By cartLocator = By.cssSelector("li > a[href=\"/view_cart\"]");
    private final By signupLoginLocator = By.cssSelector("li > a[href=\"/login\"]");
    private final By testCasesLocator = By.cssSelector("li > a[href=\"/test_cases\"]");
    private final By contactUsLocator = By.cssSelector("li > a[href=\"/contact_us\"]");

    private final By loggedInAsLocator = By.cssSelector("li > a > i.fa.fa-user");
    private final By deleteAccountLocator = By.cssSelector("li > a[href=\"/delete_account\"]");
    private final By logOutLocator = By.cssSelector("li > a[href=\"/logout\"]");

    private final By viewProductsLocator = By.cssSelector("div.choose a");

    private final By recommendedItemsTitleLocator = By.xpath("//h2[contains(text(),'recommended items')]");
    private final By recommendedOverlayLocator = By.cssSelector("div.modal-content");
    private final By recommendedAddToCartButtonsLocator = By.cssSelector("div.recommended_items a.add-to-cart");
    private final By viewCartLocator = By.cssSelector("div.modal-body a[href=\"/view_cart\"]");

    private final By scrollUpArrowLocator = By.cssSelector("a#scrollUp");
    private final By automationEngineersTextLocator = By.xpath("//h2[contains(text(),'Full-Fledged practice website')]");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Navigates to the Automation Exercise home page.
     * Uses the base URL
     */
    public void openHomePage()
    {
        fw.goToUrl("https://automationexercise.com");
    }

    /**
     * Checks if the home page is visible by verifying the logo is displayed.
     *
     * @return true if the logo is visible, false otherwise
     */
    public boolean isHomePageDisplayed()
    {
        return fw.isDisplayed(homePageLogoLocator);
    }

    /**
     * Clicks on the Signup/Login Button in the header.
     * Navigates to the Signup/Login page.
     *
     * @return SignupLoginPage object for further interactions
     */
    public SignupLoginPage clickSignupLoginButton()
    {
        fw.clickOnElement(signupLoginLocator);
        return new SignupLoginPage(fw);
    }

    /**
     * Verifies that 'Logged in as [username]' is visible in the header.
     *
     * @return true if the logged-in indicator is displayed, false otherwise
     */
    public boolean isLoggedAsDisplayed()
    {
        return fw.isDisplayed(loggedInAsLocator);
    }

    /**
     * Clicks 'Delete Account' link and navigates to deletion confirmation.
     *
     * @return AccountDeletedPage object
     */
    public AccountDeletedPage clickDeleteAccountButton()
    {
        fw.clickOnElement(deleteAccountLocator);
        return  new AccountDeletedPage(fw);
    }

    /**
     * Checks if user is logged out by verifying Signup/Login link is visible.
     *
     * @return true if logged out (SignupLogin link visible), false otherwise
     */
    public boolean isLoggedOut()
    {
        return fw.isDisplayed(signupLoginLocator);
    }

    /**
     * Clicks on the Logout Button in the header.
     * Navigates to the HomePage page.
     */
    public HomePage clickLogOutButton()
    {
        fw.clickOnElement(logOutLocator);
        return this;
    }

    /**
     * Clicks 'Contact Us' link and navigates to Contact Us Form.
     *
     * @return ContactUsPage object
     */
    public ContactUsPage clickContactUsButton()
    {
        fw.clickOnElement(contactUsLocator);
        return new ContactUsPage(fw);
    }

    /**
     * Clicks 'Test Cases' link and navigates to Test Cases Page.
     *
     * @return ContactUsPage object
     */
    public TestCasesPage clickTestCasesButton()
    {
        fw.clickOnElement(testCasesLocator);
        return new TestCasesPage(fw);
    }

    /**
     * Clicks 'Products' link and navigates to Products Page.
     *
     * @return ProductPage object
     */
    public ProductPage clickProductsButton()
    {
        fw.clickOnElement(productLocator);
        return new ProductPage(fw);
    }

    /**
     * Clicks 'Cart' link and navigates to Cart Page.
     *
     * @return CartPage object
     */
    public CartPage clickCartButton()
    {
        fw.clickOnElement(cartLocator);
        return new CartPage(fw);
    }

    /**
     * Gets the Footer section for subscription actions.
     *
     * @return FooterSection object
     */
    public FooterSection getFooter()
    {
        return new FooterSection(fw);
    }


    /**
     * Gets the SideBar section for Brand & Category actions.
     *
     * @return SideBarSection object
     */
    public SideBarSection getSidebar()
    {
        return new SideBarSection(fw);
    }


    /**
     * Checks if 'RECOMMENDED ITEMS' section is visible.
     *
     * @return true if section is displayed
     */
    public boolean isRecommendedItemsDisplayed() {
        return fw.isDisplayed(recommendedItemsTitleLocator);
    }

    /**
     * Clicks 'Add to Cart' on a recommended product by index.
     *
     * @param index Position of the recommended product (0 = first)
     */
    public void addRecommendedProductToCart(int index)
    {
        List<WebElement> buttons = fw.findElements(recommendedAddToCartButtonsLocator);
        buttons.get(index).click();
    }

    /**
     * Clicks 'View Cart' in the popup after adding recommended product.
     *
     * @return CartPage object
     */
    public CartPage clickViewCartFromOverlay()
    {
        fw.waitForVisibility(recommendedOverlayLocator);
        fw.clickOnElement(viewCartLocator);
        return new CartPage(fw);
    }

    /**
     * Scrolls to the Recommended Items section at the bottom of the page.
     */
    public void scrollToRecommendedItems()
    {
        fw.scrollToElementUsingJS(recommendedItemsTitleLocator);
    }


    /**
     * Clicks the scroll-up arrow button at the bottom right.
     */
    public void clickScrollUpArrow()
    {
        fw.clickOnElement(scrollUpArrowLocator);
    }

    /**
     * Scrolls to the very top of the page.
     */
    public void scrollToTop()
    {
        fw.scrollToElementUsingJS(homePageLogoLocator);
    }

    /**
     * Checks if the 'Full-Fledged practice website' text is visible at the top.
     *
     * @return true if text is displayed
     */
    public boolean isAutomationEngineersTextDisplayed()
    {
        return fw.isDisplayed(automationEngineersTextLocator);
    }

}
