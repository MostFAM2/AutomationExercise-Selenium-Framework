package Tests;

import DTOs.BonusDTO;
import DTOs.NewUserDTO;
import Pages.*;

import Utils.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BonusTests extends BaseTest
{

    private NewUserDTO registerData;
    private BonusDTO bonusData;

    protected final String testBonusDataFile = "BonusData.json";
    protected final String testRegisterUserFile = "RegisterUserData.json";
    /* ================================================================================== */
    /* ------------------------------------ [SETUP] ------------------------------------- */
    /* ================================================================================== */

    /**
     * Runs before each test method.
     * Loads fresh test data
     */
    @BeforeClass
    public void prepareTest()
    {
        bonusData = JsonReader.readJsonAsObject("bonusData.json", BonusDTO.class);
        Assert.assertNotNull(bonusData, "Failed to load data From: " + testBonusDataFile);

        registerData = JsonReader.readJsonAsObject("registerUserData.json", NewUserDTO.class);
        Assert.assertNotNull(registerData, "Failed to load data From: " + testRegisterUserFile);
    }


    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */

    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */


    /* ================================================================================== */
    /* ------------------ [BONUS TC1: Register with Weak Password] ----------------------- */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("BONUS TC1: Register with Weak Password - Bug: No validation for weak passwords")
    @Severity(SeverityLevel.NORMAL)
    public void testRegisterWithWeakPassword()
    {

        System.out.println("========== BONUS TC: Register with Weak Password (BUG) ==========");
        // ===== STEP 1: Launch browser =====
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify home page is visible =====
        System.out.println("STEP 3: Verifying home page...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Click Signup/Login =====
        System.out.println("STEP 4: Clicking Signup/Login...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
        Assert.assertTrue(signupLoginPage.isSignupFormDisplayed(), "Signup Form not displayed!");

        // ===== STEP 5: Enter name and email =====
        System.out.println("STEP 5: Entering name and email...");
        signupLoginPage.enterSignupDetails(registerData.getName(), registerData.getEmail());

        // ===== STEP 6: Click Signup =====
        System.out.println("STEP 6: Clicking Signup...");
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        // ===== STEP 7: Fill all details =====
        System.out.println("STEP 7: Filling account details...");
        signupDetailsPage.selectTitle(registerData.getTitle());
        signupDetailsPage.selectDateOfBirth(registerData.getDay(), registerData.getMonth(), registerData.getYear());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                registerData.getFirstName(), registerData.getLastName(), registerData.getCompany(),
                registerData.getAddress1(), registerData.getAddress2(), registerData.getCity(), registerData.getCountry(),
                registerData.getState(), registerData.getZipcode(), registerData.getMobileNumber());

        // ===== STEP 8: Enter WEAK password =====
        System.out.println("STEP 8: Entering weak password: '" + bonusData.getWeakPassword() + "'...");
        signupDetailsPage.enterPassword(bonusData.getWeakPassword());

        // ===== STEP 9: Click Create Account =====
        System.out.println("STEP 9: Clicking Create Account...");
        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();

        // ===== STEP 10: Verify weak password SHOULD be rejected =====
        System.out.println("STEP 10: Checking if account was created with weak password...");

        if (accountCreatedPage.isAccountCreatedDisplayed())
        {
            homePage = accountCreatedPage.clickContinueButton();
            AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
            accountDeletedPage.clickContinue();

            Assert.fail("BUG: No password strength validation! Weak password '" + bonusData.getWeakPassword() + "' was accepted. Account was created.");
        }

        System.out.println("✅ Weak password rejected. No bug found.");
        System.out.println("=======================================\n");

    }


    /* ================================================================================== */
    /* ------------------ [BONUS TC02: Add Product with Zero Quantity] ------------------ */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("BONUS TC02: Add Product with Zero Quantity")
    @Severity(SeverityLevel.NORMAL)
    public void testAddProductWithZeroQuantity()
    {

        System.out.println("========== BONUS TC02: Zero Quantity Validation ==========");

        // ===== STEP 1: Launch browser =====
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify home page =====
        System.out.println("STEP 3: Verifying home page...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Click View Product =====
        System.out.println("STEP 4: Clicking View Product...");
        ProductDetailsPage productDetailsPage = homePage.clickViewProductByIndex(0);

        // ===== STEP 5: Verify product detail page =====
        System.out.println("STEP 5: Verifying product detail page...");
        Assert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(), "Details page not displayed!");

        // ===== STEP 6: Manually type 0 in quantity =====
        System.out.println("STEP 6: Typing quantity: " + bonusData.getZeroQuantity());
       productDetailsPage.setProductQuantityTo(bonusData.getZeroQuantity());

        // ===== STEP 7: Click Add to Cart =====
        System.out.println("STEP 7: Clicking Add to Cart...");
       productDetailsPage.clickAddToCart();

        // ===== STEP 8: View Cart =====
        System.out.println("STEP 8: Viewing cart...");
        CartPage cartPage =productDetailsPage.clickViewCart();

        // ===== STEP 9: Verify quantity SHOULD NOT be 0 =====
        System.out.println("STEP 9: Checking cart quantity...");

        if (cartPage.getCartProductCount() > 0)
        {
            if (cartPage.getProductQuantityByIndex(0).equals("0"))
            {
                Assert.fail("BUG: Product added with zero quantity! manual typing bypasses validation.");
            }
        }

        System.out.println("✅ Zero quantity rejected. No bug found.");
        System.out.println("=======================================\n");
    }

    /* ================================================================================== */
    /* ----------- [BONUS TC03: Search with Empty Input - Shows All Products] ---------- */
    /* ================================================================================== */
    @Test(priority = 2)
    @Description("BONUS TC03: Search with Empty Input - Bug: Returns all products instead of none")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchWithEmptyInput()
    {

        System.out.println("========== BONUS TC03: Empty Search Validation ==========");

        // ===== STEP 1: Launch browser =====
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify home page =====
        System.out.println("STEP 3: Verifying home page...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Click Products =====
        System.out.println("STEP 4: Clicking Products...");
        ProductPage productPage = homePage.clickProductsButton();

        // ===== STEP 5: Verify products page =====
        System.out.println("STEP 5: Verifying products page...");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Products page not displayed!");

        // ===== STEP 6: Click search with empty input =====
        System.out.println("STEP 6: Clicking search with empty input...");
        productPage.clickSearchButton();

        // ===== STEP 7: Verify NO results should appear =====
        System.out.println("STEP 7: Checking search results...");

        if (productPage.getSearchResultsCount() > 0)
        {
            Assert.fail("BUG: Empty search returned " + productPage.getSearchResultsCount() + " products! Should have returned 0 results.");
        }

        System.out.println("✅ Empty search returns no results. No bug found.");
        System.out.println("===============================================\n");
    }


    /* ================================================================================== */
    /* ------------ [BONUS TC04: Checkout with Zero Quantity Product] ------------------- */
    /* ================================================================================== */
    @Test(priority = 3)
    @Description("BONUS TC04: Checkout with Zero Quantity - Bug: Can checkout with 0 quantity product")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutWithZeroQuantity()
    {

        System.out.println("========== BONUS TC04: Zero Quantity Checkout ==========");

        // ===== STEP 1: Launch browser =====
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Register and login =====
        System.out.println("STEP 2: Registering new user...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
        signupLoginPage.enterSignupDetails(registerData.getName(), registerData.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        signupDetailsPage.selectTitle(registerData.getTitle());
        signupDetailsPage.enterPassword(registerData.getPassword());
        signupDetailsPage.selectDateOfBirth(registerData.getDay(), registerData.getMonth(), registerData.getYear());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                registerData.getFirstName(), registerData.getLastName(), registerData.getCompany(),
                registerData.getAddress1(), registerData.getAddress2(), registerData.getCity(),
                registerData.getCountry(), registerData.getState(), registerData.getZipcode(), registerData.getMobileNumber());

        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "Account not created!");
        homePage = accountCreatedPage.clickContinueButton();
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Not logged in!");

        // ===== STEP 3: Add product with zero quantity =====
        System.out.println("STEP 3: Adding product with quantity 0...");
        ProductDetailsPage productDetailsPage = homePage.clickViewProductByIndex(0);
        productDetailsPage.setProductQuantityTo(bonusData.getZeroQuantity());
        productDetailsPage.clickAddToCart();

        // ===== STEP 4: Go to Cart =====
        System.out.println("STEP 4: Going to Cart...");
        CartPage cartPage = homePage.clickCartButton();
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed!");

        // ===== STEP 5: Try Proceed to Checkout =====
        System.out.println("STEP 5: Attempting Proceed to Checkout...");
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // ===== STEP 6: Verify checkout SHOULD be blocked =====
        System.out.println("STEP 6: Checking if checkout is blocked...");

        if (checkoutPage.isAddressDetailsDisplayed())
        {
            homePage = new HomePage(fw);
            AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
            accountDeletedPage.clickContinue();

            Assert.fail("BUG: Checkout allowed with zero quantity product! Should have been blocked.");
        }

        System.out.println("✅ Zero quantity checkout blocked. No bug found.");
        System.out.println("==============================================\n");
    }

}
