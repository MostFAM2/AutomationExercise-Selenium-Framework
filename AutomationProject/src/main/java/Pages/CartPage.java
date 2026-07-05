package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CartPage extends HomePage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;

    public CartPage(FrameWork fw) {
        super(fw);
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By shoppingCartTitleLocator = By.cssSelector("ol.breadcrumb li.active");

    private final By cartProductNamesLocator = By.cssSelector("td.cart_description h4 a");
    private final By cartProductQuantitiesLocator = By.cssSelector("td.cart_quantity button");
    private final By cartProductPricesLocator = By.cssSelector("td.cart_price p");
    private final By cartProductTotalPricesLocator = By.cssSelector("td.cart_total p");

    private final By proceedToCheckoutButtonLocator = By.cssSelector("a.btn.check_out");

    private final By registerLoginButtonLocator = By.cssSelector("p.text-center a[href=\"/login\"]");
    private final By checkoutModalLocator = By.cssSelector("div.modal-content");

    private final By removeProductButtonsLocator = By.cssSelector("a.cart_quantity_delete");
    private final By emptyCartMessageLocator = By.cssSelector("span#empty_cart > p > b");

    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the cart page is Displayed.
     *
     * @return true if we are in cart Page, fals otherwise
     */
    public boolean isCartPageDisplayed()
    {
        return fw.isDisplayed(shoppingCartTitleLocator);
    }



    /**
     * Gets all product names currently displayed in the cart.
     *
     * @return List of product name strings
     */
    public List<String> getCartProductNames()
    {
        List<WebElement>productNames = fw.findElements(cartProductNamesLocator);
        List<String> productNamesList = new ArrayList<>();
        for (WebElement productName : productNames)
        {
            productNamesList.add(productName.getText());
        }
        return productNamesList;
    }

    /**
     * Checks if the cart contains all the expected product names.
     *
     * @param expectedProductNames List of product names that should be in the cart
     * @return true if all expected product names are found
     */
    public boolean isCartContainsExactProducts(List<String> expectedProductNames)
    {
        List<String> actualProductNames = getCartProductNames();
        return new HashSet<>(actualProductNames).containsAll(expectedProductNames);
    }

    /**
     * Gets all product prices from the cart.
     *
     * @return List of price strings (e.g., "Rs. 500")
     */
    public List<String> getCartProductPrices()
    {
        List<WebElement> prices = fw.findElements(cartProductPricesLocator);
        List<String> priceList = new ArrayList<>();
        for (WebElement price : prices)
        {
            priceList.add(price.getText());
        }
        return priceList;
    }

    /**
     * Gets all product quantities from the cart.
     *
     * @return List of quantity strings (e.g., "1")
     */
    public List<String> getCartProductQuantities()
    {
        List<WebElement> quantities = fw.findElements(cartProductQuantitiesLocator);
        List<String> quantityList = new ArrayList<>();
        for (WebElement qty : quantities)
        {
            quantityList.add(qty.getText());
        }
        return quantityList;
    }

    /**
     * Gets all product total prices from the cart.
     *
     * @return List of total price strings (e.g., "Rs. 500")
     */
    public List<String> getCartProductTotalPrices()
    {
        List<WebElement> totals = fw.findElements(cartProductTotalPricesLocator);
        List<String> totalList = new ArrayList<>();
        for (WebElement total : totals)
        {
            totalList.add(total.getText());
        }
        return totalList;
    }

    /**
     * Gets the price of a product at a specific index.
     *
     * @param index Position in cart (0 = first)
     * @return Price string
     */
    public String getProductPriceByIndex(int index)
    {
        return getCartProductPrices().get(index);
    }

    /**
     * Gets the quantity of a product at a specific index.
     *
     * @param index Position in cart (0 = first)
     * @return Quantity string
     */
    public String getProductQuantityByIndex(int index)
    {
        return getCartProductQuantities().get(index);
    }

    /**
     * Gets the total price of a product at a specific index.
     *
     * @param index Position in cart (0 = first)
     * @return Total price string
     */
    public String getProductTotalPrice(int index)
    {
        return getCartProductTotalPrices().get(index);
    }

    /**
     * Clicks 'Proceed To Checkout' when user is LOGGED IN.
     * Redirects directly to the Checkout page.
     *
     * @return CheckoutPage object
     */
    public CheckoutPage proceedToCheckout()
    {
        fw.clickOnElement(proceedToCheckoutButtonLocator);
        return new CheckoutPage(fw);
    }

    /**
     * Clicks 'Proceed To Checkout' when user is LOGGED OUT.
     * Opens a Popup with Register/Login option.
     *
     * @return SignupLoginPage via the popup's Register/Login link
     */
    public SignupLoginPage proceedToCheckoutAndRegister()
    {
        fw.clickOnElement(proceedToCheckoutButtonLocator);
        fw.waitForVisibility(checkoutModalLocator);
        fw.clickOnElement(registerLoginButtonLocator);
        return new SignupLoginPage(fw);
    }

    /**
     * Removes a product from the cart by its index.
     *
     * @param index Position of the product in cart (0 = first)
     */
    public void removeProductByIndex(int index)
    {
        List<WebElement> removeButtons = fw.findElements(removeProductButtonsLocator);
        removeButtons.get(index).click();
        fw.hardWait(2);
    }

    /**
     * Checks if the cart is empty.
     *
     * @return true if empty cart message is displayed
     */
    public boolean isCartEmpty()
    {
        return fw.isDisplayed(emptyCartMessageLocator);
    }

    /**
     * Gets the total number of products currently in the cart.
     *
     * @return Number of products in cart
     */
    public int getCartProductCount()
    {
        return fw.findElements(cartProductNamesLocator).size();
    }


}
