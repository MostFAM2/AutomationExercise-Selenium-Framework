package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage
{

    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public ProductPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By salesImageLocator = By.cssSelector("img#sale_image");
    private final By allProductsTitleLocator = By.cssSelector("h2.title.text-center");

    private final By productImagesWrapperLocator = By.cssSelector("div.product-image-wrapper");

    private final By viewProductsLocator = By.cssSelector("div.choose a");

    private final By searchBarLocator = By.cssSelector("input#search_product");
    private final By searchButtonLocator = By.cssSelector("button#submit_search");

    private final By searchResultItemsTitleLocator = By.xpath("//div[@class='product-image-wrapper']/div/div/p");


    private final By popupWindowLocator = By.cssSelector("div.modal-body");
    private final By continueShoppingButtonLocator = By.cssSelector("button.btn-success.close-modal");
    private final By viewCartLocator = By.cssSelector("div.modal-body a[href=\"/view_cart\"]");

    private final By overlayLocator = By.cssSelector("div.overlay-content");
    private final By addToCartFromOverlayLocator = By.cssSelector("div.overlay-content a.btn.add-to-cart");

    private final By productNamesLocator = By.cssSelector("div.product-image-wrapper div.productinfo p");

    private final By cartLocator = By.cssSelector("li > a[href=\"/view_cart\"]");

    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the Products page is displayed by verifying title and image.
     *
     * @return true if the page elements are visible, false otherwise
     */
    public boolean isProductPageDisplayed()
    {
        return fw.isDisplayed(salesImageLocator) && fw.isDisplayed(allProductsTitleLocator);
    }

    /**
     * Checks if the Products list is Displayed and available.
     *
     * @return true if the products are visible, false otherwise
     */
    public boolean isProductListAvailable()
    {
        return fw.isDisplayed(productImagesWrapperLocator);
    }


    /**
     * Scrolls to a product at the specified index using JavaScript.
     * Ensures the product is in view before interacting with it.
     *
     * @param index Position of the product (0 = first)
     */
    public void scrollToProductByIndex(int index)
    {
        fw.scrollToElementByIndex(productImagesWrapperLocator, index);
    }


    /**
     * Clicks on a product at a specific index from the products list.
     *
     * @param index The position of the product to click (0 = first product, 1 = second, etc.)
     * @return ProductDetailsPage object to continue testing on the product details page
     */
    public ProductDetailsPage clickViewProductByIndex(int index)
    {
        List<WebElement>products = fw.findElements(viewProductsLocator);
        scrollToProductByIndex(index);
        products.get(index).click();
        return new ProductDetailsPage(fw);
    }

    /**
     * Types text into the search bar.
     *
     * @param searchText Text to search for (e.g., "T-shirt")
     */
    public void enterSearchBarText(String searchText)
    {
        fw.sendString(searchBarLocator, searchText);
    }

    /**
     * Clicks the search button to execute the product search.
     */
    public void clickSearchButton()
    {
        fw.clickOnElement(searchButtonLocator);
    }

    /**
     * Checks if the 'SEARCHED PRODUCTS' section is visible after search.
     *
     * @return true if search results section is displayed
     */
    public boolean isSearchedProductsDisplayed()
    {
        return fw.isDisplayed(allProductsTitleLocator);
    }

    /**
     * Verifies that all visible search result titles contain the search text.
     *
     * @param searchText The text that was searched for
     * @return true if all result titles contain the search text, false otherwise
     */
    public boolean areAllProductsRelatedToSearch(String searchText)
    {
        List<WebElement>titles = fw.findElements(searchResultItemsTitleLocator);
        if(titles.isEmpty())
        {
            return false;
        }
        for(WebElement title : titles)
        {
            if(!title.getText().toLowerCase().contains(searchText.toLowerCase()))
            {
                System.out.printf("Title: %s is not related to search results\n", title.getText());
                return false;
            }
        }
        return true;
    }

    /**
     * Hovers the mouse over a product to reveal the Add to Cart button.
     *
     * @param index Position of the product (0 = first)
     */
    public void hoverOverProductByIndex(int index)
    {
        List<WebElement>products = fw.findElements(productImagesWrapperLocator);
        fw.hoverOverElement(products.get(index));
    }

    /**
     * Clicks the Add to Cart button from overlay for the product at the given index.
     *
     * @param index Position of the product (0 = first)
     */
    public void addToCartFromOverlayByIndex(int index)
    {
        scrollToProductByIndex(index);
        hoverOverProductByIndex(index);
        fw.waitForVisibility(overlayLocator);

        List<WebElement> buttons = fw.findElements(addToCartFromOverlayLocator);
        buttons.get(index).click();
    }

    /**
     * Clicks 'Continue Shopping' in the popup after adding to cart.
     */
    public void clickContinueShoppingButton()
    {
        fw.clickOnElement(continueShoppingButtonLocator);
    }

    /**
     * Clicks 'View Cart' in the popup after adding to cart.
     *
     * @return CartPage Object
     */
    public CartPage clickViewCartButton()
    {
        fw.clickOnElement(viewCartLocator);
        return new CartPage(fw);
    }

    /**
     * Gets the name of a product by its index.
     *
     * @param index Position of the product (0 = first)
     * @return Product name as displayed on the page
     */
    public String gerProductNameByIndex(int index)
    {
        List<WebElement>names = fw.findElements(productNamesLocator);
        return names.get(index).getText();
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
     * Gets the total number of products displayed in search results.
     *
     * @return Number of products in the search results list
     */
    public int getSearchResultsCount()
    {
        List<WebElement> results = fw.findElements(searchResultItemsTitleLocator);
        return results.size();
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



}

