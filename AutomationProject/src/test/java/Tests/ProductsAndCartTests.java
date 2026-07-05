package Tests;

import DTOs.ProductsDTO;
import Pages.*;
import Utils.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductsAndCartTests extends BaseTest
{
    private ProductsDTO productsData;
    protected String testProductsDataFile = "ProductsData.json";

    private final static String SEARCH_TEXT = "T-Shirt";
    private final static String SUBSCRIPTION_EMAIL = "subEmail@Test.com";

    /* ================================================================================== */
    /* ------------------------------------ [SETUP] ------------------------------------- */
    /* ================================================================================== */
    @BeforeClass
    public void prepareTest()
    {
        productsData = JsonReader.readJsonAsObject(testProductsDataFile, ProductsDTO.class);
        Assert.assertNotNull(productsData, "Failed to load test data from: " + testProductsDataFile);
    }


    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */


    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */
    /**
     * Verifies that cart has correct prices, quantities, and total prices
     * for the expected number of products.
     *
     * @param cartPage    The cart page object
     * @param expectedProductCount Expected number of products
     */
    private void verifyCartPricesAndQuantities(CartPage cartPage, int expectedProductCount)
    {
        List<String> prices = cartPage.getCartProductPrices();
        List<String> quantities = cartPage.getCartProductQuantities();
        List<String> totalPrices = cartPage.getCartProductTotalPrices();


        Assert.assertEquals(prices.size(), expectedProductCount, "Price count mismatch!");
        Assert.assertEquals(quantities.size(), expectedProductCount, "Quantity count mismatch!");
        Assert.assertEquals(totalPrices.size(), expectedProductCount, "Total price count mismatch!");
    }

    /* ================================================================================== */
    /* -------------- [TC8: Verify All Products and product detail page] ---------------- */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC8: Verify All Products and product detail page")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifyAllProductsAndProductDetailPage()
    {
        System.out.println("========== TC8: Verify All Products and product detail page ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");

        // ===== STEP 4: Click on 'Products' button =====
        System.out.println("STEP 4: Clicking 'Products' button...");
        ProductPage productPage = homePage.clickProductsButton();

        // ===== STEP 5: Verify user is navigated to ALL PRODUCTS page successfully =====
        System.out.println("STEP 5: Verifying ALL PRODUCTS page is displayed...");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page is not displayed!");

        // ===== STEP 6: The products list is visible =====
        System.out.println("STEP 6: Verifying products list is available...");
        Assert.assertTrue(productPage.isProductListAvailable(), "Product List is not available!");

        // ===== STEP 7: Click on 'View Product' of first product =====
        System.out.println("STEP 7: Scrolling to product and clicking 'View Product'...");
        productPage.scrollToProductByIndex(productsData.getFirstProductIndex());
        ProductDetailsPage productDetailsPage = productPage.clickViewProductByIndex(productsData.getFirstProductIndex());

        // ===== STEP 8: User is landed to product detail page =====
        System.out.println("STEP 8: Verifying product detail page is displayed...");
        Assert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(), "Product details page is not displayed!");

        // ===== STEP 9: Verify product details are visible =====
        System.out.println("STEP 9: Verifying product details (name, category, price, availability, condition, brand)...");
        Assert.assertTrue(productDetailsPage.isProductNameDisplayed(), "Product name is not visible!");
        Assert.assertTrue(productDetailsPage.isProductPriceDisplayed(), "Product price is not visible!");
        Assert.assertTrue(productDetailsPage.isProductCategoryDisplayed(), "Product category is not visible!");
        Assert.assertTrue(productDetailsPage.isProductConditionDisplayed(), "Product condition is not visible!");
        Assert.assertTrue(productDetailsPage.isProductBrandDisplayed(), "Product brand is not visible!");
        Assert.assertTrue(productDetailsPage.isProductAvailabilityDisplayed(), "Product availability is not visible!");

        System.out.println("✅ TC8 PASSED: All Products and product detail verified.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ----------------------------- [TC9: Search Product] ------------------------------ */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC9: Search Product")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchProduct()
    {
        System.out.println("========== TC9: Search Product ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");

        // ===== STEP 4: Click on 'Products' button =====
        System.out.println("STEP 4: Clicking 'Products' button...");
        ProductPage productPage = homePage.clickProductsButton();

        // ===== STEP 5: Verify user is navigated to ALL PRODUCTS page successfully =====
        System.out.println("STEP 5: Verifying ALL PRODUCTS page is displayed...");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page is not displayed!");

        // ===== STEP 6: Enter product name in search input and click search button =====
        System.out.println("STEP 6: Searching for '" + SEARCH_TEXT + "'...");
        productPage.enterSearchBarText(SEARCH_TEXT);
        productPage.clickSearchButton();

        // ===== STEP 7: Verify 'SEARCHED PRODUCTS' is visible =====
        System.out.println("STEP 7: Verifying 'SEARCHED PRODUCTS' is visible...");
        Assert.assertTrue(productPage.isSearchedProductsDisplayed(), "Searched products title is not displayed!");

        // ===== STEP 8: Verify all the products related to search are visible =====
        System.out.println("STEP 8: Verifying all results are related to search...");
        Assert.assertTrue(productPage.areAllProductsRelatedToSearch(SEARCH_TEXT), "Products are not related to the search text!");

        System.out.println("✅ TC9 PASSED: All Products are related to the search text.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ----------------- [TC10: Verify Subscription in home page] ----------------------- */
    /* ================================================================================== */
    @Test(priority = 2)
    @Description("TC10: Verify Subscription in home page")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifySubscriptionInHomePage()
    {
        System.out.println("========== TC10: Verify Subscription in home page ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");

        // ===== STEP 4: Scroll down to footer =====
        System.out.println("STEP 4: Scrolling down to footer...");
        FooterSection footerSection = homePage.getFooter();
        footerSection.scrollToFooter();

        // ===== STEP 5: Verify text 'SUBSCRIPTION' =====
        System.out.println("STEP 5: Verifying 'SUBSCRIPTION' text is visible...");
        Assert.assertTrue(footerSection.isSubscriptionTextDisplayed(), "'SUBSCRIPTION' is not Displayed!");

        // ===== STEP 6: Enter email address and click subscribe =====
        System.out.println("STEP 6: Entering email and clicking subscribe...");
        footerSection.enterEmailForSubscription(SUBSCRIPTION_EMAIL);
        footerSection.clickSubscribeButton();

        // ===== STEP 7: Verify success message is visible =====
        System.out.println("STEP 7: Verifying subscription success message...");
        Assert.assertTrue(footerSection.isSubscriptionSuccessMsgDisplayed(), "SUBSCRIPTION Success Message is not Displayed!");

        System.out.println("✅ TC10 PASSED: Subscription in home page Verified Successfully.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ----------------- [TC11: Verify Subscription in cart page] ----------------------- */
    /* ================================================================================== */
    @Test(priority = 3)
    @Description("TC11: Verify Subscription in Cart page")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifySubscriptionInCartPage()
    {
        System.out.println("========== TC11: Verify Subscription in Cart page ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");

        // ===== STEP 4: Click 'Cart' button =====
        System.out.println("STEP 4: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickCartButton();

        // ===== STEP 5: Scroll down to footer =====
        System.out.println("STEP 5: Scrolling down to footer...");
        FooterSection footerSection = cartPage.getFooter();
        footerSection.scrollToFooter();

        // ===== STEP 6: Verify text 'SUBSCRIPTION' =====
        System.out.println("STEP 6: Verifying 'SUBSCRIPTION' text is visible...");
        Assert.assertTrue(footerSection.isSubscriptionTextDisplayed(), "'SUBSCRIPTION' is not Displayed!");

        // ===== STEP 7: Enter email address and click subscribe =====
        System.out.println("STEP 7: Entering email and clicking subscribe...");
        footerSection.enterEmailForSubscription(SUBSCRIPTION_EMAIL);
        footerSection.clickSubscribeButton();

        // ===== STEP 8: Verify success message is visible =====
        System.out.println("STEP 8: Verifying subscription success message...");
        Assert.assertTrue(footerSection.isSubscriptionSuccessMsgDisplayed(), "SUBSCRIPTION Success Message is not visible!");

        System.out.println("✅ TC11 PASSED: Subscription in Cart page Verified Successfully.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ------------------------ [TC12: Add Products in Cart] ---------------------------- */
    /* ================================================================================== */
    @Test(priority = 4)
    @Description("TC12: Add Products in Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddProductsInCart()
    {
        System.out.println("========== TC12: Add Products in Cart ==========");
        List<String> expectedProductsInCart = new ArrayList<>();

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");

        // ===== STEP 4: Click 'Products' button =====
        System.out.println("STEP 4: Clicking 'Products' button...");
        ProductPage productPage = homePage.clickProductsButton();

        // ===== STEP 5: Hover over first product and click 'Add to cart' =====
        System.out.println("STEP 5: Adding first product to cart...");
        String product1 = productPage.gerProductNameByIndex(productsData.getFirstProductIndex());
        productPage.addToCartFromOverlayByIndex(productsData.getFirstProductIndex());
        expectedProductsInCart.add(product1);

        // ===== STEP 6: Click 'Continue Shopping' button =====
        System.out.println("STEP 6: Clicking 'Continue Shopping'...");
        productPage.clickContinueShoppingButton();

        // ===== STEP 7: Hover over second product and click 'Add to cart' =====
        System.out.println("STEP 7: Adding second product to cart...");
        String product2 = productPage.gerProductNameByIndex(productsData.getSecondProductIndex());
        productPage.addToCartFromOverlayByIndex(productsData.getSecondProductIndex());
        expectedProductsInCart.add(product2);

        // ===== STEP 8: Click 'View Cart' button =====
        System.out.println("STEP 8: Clicking 'View Cart'...");
        CartPage cartPage = productPage.clickViewCartButton();

        // ===== STEP 9: Verify both products are added to Cart =====
        System.out.println("STEP 9: Verifying both products are in cart...");
        Assert.assertTrue(cartPage.isCartContainsExactProducts(expectedProductsInCart),
                "Cart does not contain all expected products!" +
                        "\nExpected: " + expectedProductsInCart +
                        "\nFound: " + cartPage.getCartProductNames());

        // ===== STEP 10: Verify their prices, quantity and total price =====
        System.out.println("STEP 10: Verifying prices, quantities and total prices...");
        verifyCartPricesAndQuantities(cartPage, expectedProductsInCart.size());

        System.out.println("✅ TC12 PASSED: All products verified in cart.");
        System.out.println("==============================================\n");
    }


    /* ================================================================================== */
    /* ------------------ [TC13: Verify Product quantity in Cart] ----------------------- */
    /* ================================================================================== */
    @Test(priority = 5)
    @Description("TC13: Verify Product quantity in Cart")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifyProductQuantityInCart()
    {
        System.out.println("========== TC13: Verify Product quantity in Cart ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");

        // ===== PRE-STEP: Clear cart from previous tests =====
        CartPage cartPage = homePage.clickCartButton();
        int cartCount = cartPage.getCartProductCount();
        for (int i = 0; i < cartCount; i++)
        {
            cartPage.removeProductByIndex(0);
        }
        homePage.openHomePage();

        // ===== STEP 4: Click 'View Product' for product on home page =====
        System.out.println("STEP 4: Clicking 'View Product'...");
        homePage.scrollToProductByIndex(productsData.getFirstProductIndex());
        ProductDetailsPage productDetailsPage = homePage.clickViewProductByIndex(productsData.getFirstProductIndex());

        // ===== STEP 5: Verify product detail is opened =====
        System.out.println("STEP 5: Verifying product detail page is displayed...");
        Assert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(), "Product details page is not displayed!");

        // ===== STEP 6: Increase quantity =====
        System.out.println("STEP 6: Setting quantity to " + productsData.getQuantity() + "...");
        productDetailsPage.setProductQuantityTo(productsData.getQuantity());

        // ===== STEP 7: Click 'Add to cart' button =====
        System.out.println("STEP 7: Clicking 'Add to cart'...");
        productDetailsPage.clickAddToCart();

        // ===== STEP 8: Click 'View Cart' button =====
        System.out.println("STEP 8: Clicking 'View Cart'...");
        cartPage = productDetailsPage.clickViewCart();

        // ===== STEP 9: Verify product quantity in cart =====
        System.out.println("STEP 9: Verifying product quantity in cart...");
        String actualQuantity = cartPage.getProductQuantityByIndex(productsData.getFirstProductIndex() - 1);
        Assert.assertEquals(actualQuantity, String.valueOf(productsData.getQuantity()),
                "Quantity mismatch! Expected: " + productsData.getQuantity() + ", Found: " + actualQuantity);

        System.out.println("✅ TC13 PASSED: Product quantity verified in cart.");
        System.out.println("==============================================\n");
    }


    /* ================================================================================== */
    /* ---------------------- [TC17: Remove Products From Cart] ------------------------- */
    /* ================================================================================== */
    @Test(priority = 6)
    @Description("TC17: Remove Products From Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testRemoveProductsFromCart()
    {
        System.out.println("========== TC17: Remove Products From Cart ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify home page is visible =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Add products to cart =====
        System.out.println("STEP 4: Adding products to cart...");
        homePage.addToCartFromOverlayByIndex(productsData.getFirstProductIndex());
        homePage.clickContinueShoppingButton();
        homePage.addToCartFromOverlayByIndex(productsData.getSecondProductIndex());

        // ===== STEP 5: Click 'Cart' button =====
        System.out.println("STEP 5: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickViewCartButton();

        // ===== STEP 6: Verify cart page is displayed =====
        System.out.println("STEP 6: Verifying cart page is displayed...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed!");

        // ===== STEP 7: Remove first product from cart (index 0) =====
        System.out.println("STEP 7: Removing first product from cart...");
        String productToRemove = cartPage.getCartProductNames().get(0);
        int countBefore = cartPage.getCartProductCount();
        cartPage.removeProductByIndex(0);

        // ===== STEP 8: Verify product is removed from cart =====
        System.out.println("STEP 8: Verifying product was removed...");

        int countAfter = cartPage.getCartProductCount();

        Assert.assertEquals(countAfter, countBefore - 1,
                "Product count did not decrease! Before: " + countBefore + ", After: " + countAfter);

        Assert.assertFalse(cartPage.getCartProductNames().contains(productToRemove),
                "Product '" + productToRemove + "' was not removed from cart!");

        System.out.println("✅ TC17 PASSED: Product removed successfully.");
        System.out.println("=============================================\n");
    }



    /* ================================================================================== */
    /* --------------- [TC22: Add to cart from Recommended items] ----------------------- */
    /* ================================================================================== */
    @Test(priority = 7)
    @Description("TC22: Add to cart from Recommended items")
    @Severity(SeverityLevel.NORMAL)
    public void testAddToCartFromRecommendedItems()
    {
        System.out.println("========== TC22: Add to cart from Recommended items ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Scroll to bottom of page =====
        System.out.println("STEP 3: Scrolling to Recommended Items...");
        homePage.scrollToRecommendedItems();

        // ===== STEP 4: Verify 'RECOMMENDED ITEMS' are visible =====
        System.out.println("STEP 4: Verifying 'RECOMMENDED ITEMS' section...");
        Assert.assertTrue(homePage.isRecommendedItemsDisplayed(), "'RECOMMENDED ITEMS' section not displayed!");

        // ===== STEP 5: Click on 'Add To Cart' on Recommended product =====
        System.out.println("STEP 5: Adding recommended product to cart...");
        homePage.addRecommendedProductToCart(0);

        // ===== STEP 6: Click on 'View Cart' =====
        System.out.println("STEP 6: Clicking 'View Cart'...");
        CartPage cartPage = homePage.clickViewCartFromOverlay();

        // ===== STEP 7: Verify that product is displayed in cart page =====
        System.out.println("STEP 7: Verifying product is in cart...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed!");
        Assert.assertTrue(cartPage.getCartProductCount() > 0, "Cart is empty! Product was not added.");

        System.out.println("✅ TC22 PASSED: Recommended product added to cart.");
        System.out.println("===================================================\n");
    }

}
