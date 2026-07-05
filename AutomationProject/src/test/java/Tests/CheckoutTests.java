package Tests;

import DTOs.*;
import Pages.*;

import Utils.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest
{

    private NewUserDTO newUserData;
    private ProductsDTO productsIndexes;
    private PaymentDTO paymentData;
    private OrderDTO orderData;

    protected String testProductsDataFile = "ProductsData.json";
    protected String testRegisterUserFile = "RegisterUserData.json";
    protected String testPaymentFile = "PaymentData.json";
    protected String testOrderFile = "OrderData.json";


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
        newUserData = JsonReader.readJsonAsObject(testRegisterUserFile, NewUserDTO.class);
        Assert.assertNotNull(newUserData,  "Failed to load test data from: " + testRegisterUserFile);

        productsIndexes = JsonReader.readJsonAsObject(testProductsDataFile, ProductsDTO.class);
        Assert.assertNotNull(productsIndexes,   "Failed to load test data from: " + testProductsDataFile);

        paymentData = JsonReader.readJsonAsObject(testPaymentFile, PaymentDTO.class);
        Assert.assertNotNull(paymentData,  "Failed to load test data from: " + testPaymentFile);

        orderData = JsonReader.readJsonAsObject(testOrderFile, OrderDTO.class);
        Assert.assertNotNull(orderData,  "Failed to load test data from: " + testOrderFile);

    }


    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */

    /**
     * Runs after each test method.
     * Deletes the user account if it was created during the test.
     */
    @AfterMethod
    public void cleanupAccount()
    {
        try
        {
            HomePage homePage = new HomePage(fw);
            homePage.openHomePage();

            if (homePage.isLoggedAsDisplayed())
            {
                AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
                accountDeletedPage.clickContinue();
                System.out.println("Deleted: " + newUserData.getEmail());
                System.out.println("==============================\n");
                return;
            }

            SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
            Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login form not visible!");
            signupLoginPage.enterLoginDetails(newUserData.getEmail(), newUserData.getPassword());
            homePage = signupLoginPage.clickLoginButton();

            if (!homePage.isLoggedAsDisplayed())
            {
                System.out.println("Account already deleted: " + newUserData.getEmail() + " — skipping.");
                System.out.println("==============================\n");
                return;
            }

            AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
            accountDeletedPage.clickContinue();
            System.out.println("Deleted: " + newUserData.getEmail());
            System.out.println("==============================\n");

        }
        catch (Exception e)
        {
            System.out.println("Could not delete " + newUserData.getEmail() + ": " + e.getMessage());
        }
        System.out.println("==============================\n");
    }

    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */

    /**
     * Registers a new user account and returns to home page logged in.
     *
     * @param user The user DTO to register
     */
    private void registerNewUser(NewUserDTO user)
    {
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
        Assert.assertTrue(signupLoginPage.isSignupFormDisplayed(), "Signup form not displayed!");

        signupLoginPage.enterSignupDetails(user.getName(), user.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();
        Assert.assertTrue(signupDetailsPage.isEnterAccountInfoDisplayed(), "Account info not displayed!");

        signupDetailsPage.selectTitle(user.getTitle());
        signupDetailsPage.enterPassword(user.getPassword());
        signupDetailsPage.selectDateOfBirth(user.getDay(), user.getMonth(), user.getYear());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                user.getFirstName(), user.getLastName(), user.getCompany(),
                user.getAddress1(), user.getAddress2(), user.getCity(),
                user.getCountry(), user.getState(), user.getZipcode(), user.getMobileNumber());

        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "Account not created!");
        accountCreatedPage.clickContinueButton();
    }


    /**
     * Builds the expected address string from user data.
     * Matches the format displayed on the checkout page.
     *
     * @param user The user DTO
     * @return Expected address string
     */
    private String buildExpectedAddress(NewUserDTO user)
    {
        return user.getFirstName() + " " +
                user.getLastName() + " " +
                user.getCompany() + " " +
                user.getAddress1() + " " +
                user.getAddress2() + " " +
                user.getCity() + " " +
                user.getState() + " " +
                user.getZipcode() + " " +
                user.getCountry() + " " +
                user.getMobileNumber();
    }


    /* ================================================================================== */
    /* ------------------ [TC14: Place Order: Register while Checkout] ------------------ */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC14: Place Order: Register while Checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void testPlaceOrderRegisterBeforeCheckout()
    {
        System.out.println("========== TC14: Place Order - Register while Checkout ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not visible successfully!");

        // ===== STEP 4: Add products to cart =====
        System.out.println("STEP 4: Adding products to cart...");
        homePage.addToCartFromOverlayByIndex(productsIndexes.getFirstProductIndex());
        homePage.clickContinueShoppingButton();
        homePage.addToCartFromOverlayByIndex(productsIndexes.getSecondProductIndex());

        // ===== STEP 5: Click 'Cart' button =====
        System.out.println("STEP 5: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickViewCartButton();

        // ===== STEP 6: Verify that cart page is displayed =====
        System.out.println("STEP 6: Verifying cart page is displayed...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page is not displayed!");

        // ===== STEP 7 & 8: Click Proceed To Checkout → Register / Login =====
        System.out.println("STEP 7 & 8: Proceeding to checkout and clicking Register/Login...");
        SignupLoginPage signupLoginPage = cartPage.proceedToCheckoutAndRegister();

        // ===== STEP 9: Fill all details in Signup and create account =====
        System.out.println("STEP 9: Filling signup details and creating account...");
        signupLoginPage.enterSignupDetails(newUserData.getName(), newUserData.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        signupDetailsPage.selectTitle(newUserData.getTitle());
        signupDetailsPage.enterPassword(newUserData.getPassword());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                newUserData.getFirstName(), newUserData.getLastName(), newUserData.getCompany(),
                newUserData.getAddress1(), newUserData.getAddress2(), newUserData.getCity(),
                newUserData.getCountry(), newUserData.getState(), newUserData.getZipcode(), newUserData.getMobileNumber());
        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();

        // ===== STEP 10: Verify 'ACCOUNT CREATED!' and click 'Continue' button =====
        System.out.println("STEP 10: Verifying 'ACCOUNT CREATED!' and clicking Continue...");
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "'ACCOUNT CREATED!' is not displayed!");
        homePage = accountCreatedPage.clickContinueButton();

        // ===== STEP 11: Verify 'Logged in as username' at top =====
        System.out.println("STEP 11: Verifying 'Logged in as username'...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "'Logged in as' is not displayed!");

        // ===== STEP 12: Click 'Cart' button =====
        System.out.println("STEP 12: Clicking 'Cart' button...");
        cartPage = homePage.clickCartButton();

        // ===== STEP 13: Click 'Proceed To Checkout' button =====
        System.out.println("STEP 13: Clicking 'Proceed To Checkout'...");
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // ===== STEP 14: Verify Address Details and Review Your Order =====
        System.out.println("STEP 14: Verifying Address Details and Review Your Order...");
        Assert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address Details' Title is not displayed!");
        Assert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "'Review Your Order' is not displayed!");

        // ===== STEP 15: Enter description and click 'Place Order' =====
        System.out.println("STEP 15: Entering description and clicking Place Order...");
        checkoutPage.writeOrderMessage(orderData.getDescription());
        PaymentPage paymentPage = checkoutPage.clickPlaceOrderButton();

        // ===== STEP 16: Enter payment details =====
        System.out.println("STEP 16: Entering payment details...");
        paymentPage.enterPaymentDetails(
                paymentData.getCardName(), paymentData.getCardNumber(),
                paymentData.getCvc(), paymentData.getExpiryMonth(), paymentData.getExpiryYear());

        // ===== STEP 17: Click 'Pay and Confirm Order' button =====
        System.out.println("STEP 17: Clicking 'Pay and Confirm Order'...");
        OrderPlacedPage orderPlacedPage = paymentPage.clickPlaceOrder();

        // ===== STEP 18: Verify success message =====
        System.out.println("STEP 18: Verifying order success messages...");
        Assert.assertTrue(orderPlacedPage.isOrderPlacedSuccessMsgDisplayed(), "'Congratulations! Your order has been confirmed!' not displayed!");
        homePage = orderPlacedPage.clickContinue();

        // ===== STEP 19: Click 'Delete Account' button =====
        System.out.println("STEP 19: Clicking 'Delete Account'...");
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();

        // ===== STEP 20: Verify 'ACCOUNT DELETED!' and click 'Continue' button =====
        System.out.println("STEP 20: Verifying 'ACCOUNT DELETED!' and clicking Continue...");
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "'Account deleted!' is not displayed!");
        accountDeletedPage.clickContinue();

        System.out.println("✅ TC14 PASSED: Order placed while registering during checkout.");
        System.out.println("======================================================\n");
    }

    /* ================================================================================== */
    /* ----------------- [TC15: Place Order: Register before Checkout] ------------------ */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC15: Place Order: Register before Checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void testPlaceOrderRegisterWhileCheckout()
    {
        System.out.println("========== TC15: Place Order - Register before Checkout ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not visible successfully!");

        // ===== STEP 4: Click 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Fill all details in Signup and create account =====
        System.out.println("STEP 5: Filling signup details and creating account...");
        signupLoginPage.enterSignupDetails(newUserData.getName(), newUserData.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        signupDetailsPage.selectTitle(newUserData.getTitle());
        signupDetailsPage.enterPassword(newUserData.getPassword());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                newUserData.getFirstName(), newUserData.getLastName(), newUserData.getCompany(),
                newUserData.getAddress1(), newUserData.getAddress2(), newUserData.getCity(),
                newUserData.getCountry(), newUserData.getState(), newUserData.getZipcode(), newUserData.getMobileNumber());
        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();

        // ===== STEP 6: Verify 'ACCOUNT CREATED!' and click 'Continue' button =====
        System.out.println("STEP 6: Verifying 'ACCOUNT CREATED!' and clicking Continue...");
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "'ACCOUNT CREATED!' is not displayed!");
        homePage = accountCreatedPage.clickContinueButton();

        // ===== STEP 7: Verify 'Logged in as username' at top =====
        System.out.println("STEP 7: Verifying 'Logged in as username'...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "'Logged in as' is not displayed!");

        // ===== STEP 8: Add products to cart =====
        System.out.println("STEP 8: Adding products to cart...");
        homePage.addToCartFromOverlayByIndex(productsIndexes.getFirstProductIndex());
        homePage.addToCartFromOverlayByIndex(productsIndexes.getSecondProductIndex());

        // ===== STEP 9: Click 'Cart' button =====
        System.out.println("STEP 9: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickViewCartButton();

        // ===== STEP 10: Verify that cart page is displayed =====
        System.out.println("STEP 10: Verifying cart page is displayed...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page is not displayed!");

        // ===== STEP 11: Click 'Proceed To Checkout' button =====
        System.out.println("STEP 11: Clicking 'Proceed To Checkout'...");
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // ===== STEP 12: Verify Address Details and Review Your Order =====
        System.out.println("STEP 12: Verifying Address Details and Review Your Order...");
        Assert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address Details' Title is not displayed!");
        Assert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "'Review Your Order' is not displayed!");

        // ===== STEP 13: Enter description and click 'Place Order' =====
        System.out.println("STEP 13: Entering description and clicking Place Order...");
        checkoutPage.writeOrderMessage(orderData.getDescription());
        PaymentPage paymentPage = checkoutPage.clickPlaceOrderButton();

        // ===== STEP 14: Enter payment details =====
        System.out.println("STEP 14: Entering payment details...");
        paymentPage.enterPaymentDetails(
                paymentData.getCardName(), paymentData.getCardNumber(),
                paymentData.getCvc(), paymentData.getExpiryMonth(), paymentData.getExpiryYear());

        // ===== STEP 15: Click 'Pay and Confirm Order' button =====
        System.out.println("STEP 15: Clicking 'Pay and Confirm Order'...");
        OrderPlacedPage orderPlacedPage = paymentPage.clickPlaceOrder();

        // ===== STEP 16: Verify success message =====
        System.out.println("STEP 16: Verifying order success messages...");
        Assert.assertTrue(orderPlacedPage.isOrderPlacedSuccessMsgDisplayed(), "'Congratulations! Your order has been confirmed!' not displayed!");
        homePage = orderPlacedPage.clickContinue();

        // ===== STEP 17: Click 'Delete Account' button =====
        System.out.println("STEP 17: Clicking 'Delete Account'...");
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();

        // ===== STEP 18: Verify 'ACCOUNT DELETED!' and click 'Continue' button =====
        System.out.println("STEP 18: Verifying 'ACCOUNT DELETED!' and clicking Continue...");
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "'Account deleted!' is not displayed!");
        accountDeletedPage.clickContinue();

        System.out.println("✅ TC15 PASSED: Order placed after registering before checkout.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ------------------ [TC16: Place Order: Login before Checkout] -------------------- */
    /* ================================================================================== */
    @Test(priority = 2)
    @Description("TC16: Place Order: Login before Checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void testPlaceOrderLoginBeforeCheckout()
    {
        System.out.println("========== TC16: Place Order - Login before Checkout ==========");

        // ===== PRE-STEP: Register account (needed for login) =====
        System.out.println("PRE-STEP: Registering new account for login...");
        registerNewUser(newUserData);
        new HomePage(fw).clickLogOutButton();

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

        // ===== STEP 4: Click 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Fill email, password, and click 'Login' =====
        System.out.println("STEP 5: Entering login credentials...");
        Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login form not displayed!");
        signupLoginPage.enterLoginDetails(newUserData.getEmail(), newUserData.getPassword());
        homePage = signupLoginPage.clickLoginButton();

        // ===== STEP 6: Verify 'Logged in as username' =====
        System.out.println("STEP 6: Verifying 'Logged in as username'...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Login failed!");

        // ===== STEP 7: Add products to cart =====
        System.out.println("STEP 7: Adding products to cart...");
        homePage.addToCartFromOverlayByIndex(productsIndexes.getFirstProductIndex());
        homePage.addToCartFromOverlayByIndex(productsIndexes.getSecondProductIndex());

        // ===== STEP 8: Click 'Cart' button =====
        System.out.println("STEP 8: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickViewCartButton();

        // ===== STEP 9: Verify cart page is displayed =====
        System.out.println("STEP 9: Verifying cart page is displayed...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed!");

        // ===== STEP 10: Click Proceed To Checkout =====
        System.out.println("STEP 10: Clicking 'Proceed To Checkout'...");
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // ===== STEP 11: Verify Address Details and Review Your Order =====
        System.out.println("STEP 11: Verifying Address Details and Review Your Order...");
        Assert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address Details not displayed!");
        Assert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "Review Your Order not displayed!");

        // ===== STEP 12: Enter description and click 'Place Order' =====
        System.out.println("STEP 12: Entering description and clicking Place Order...");
        checkoutPage.writeOrderMessage(orderData.getDescription());
        PaymentPage paymentPage = checkoutPage.clickPlaceOrderButton();

        // ===== STEP 13: Enter payment details =====
        System.out.println("STEP 13: Entering payment details...");
        paymentPage.enterPaymentDetails(
                paymentData.getCardName(), paymentData.getCardNumber(),
                paymentData.getCvc(), paymentData.getExpiryMonth(), paymentData.getExpiryYear());

        // ===== STEP 14: Click 'Pay and Confirm Order' =====
        System.out.println("STEP 14: Clicking 'Pay and Confirm Order'...");
        OrderPlacedPage orderPlacedPage = paymentPage.clickPlaceOrder();

        // ===== STEP 15: Verify success message =====
        System.out.println("STEP 15: Verifying order success message...");
        Assert.assertTrue(orderPlacedPage.isOrderPlacedSuccessMsgDisplayed(), "Order not confirmed!");
        homePage = orderPlacedPage.clickContinue();

        // ===== STEP 16: Click 'Delete Account' =====
        System.out.println("STEP 16: Clicking 'Delete Account'...");
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();

        // ===== STEP 17: Verify 'ACCOUNT DELETED!' and click 'Continue' =====
        System.out.println("STEP 17: Verifying 'ACCOUNT DELETED!' and clicking Continue...");
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "Account deleted not displayed!");
        accountDeletedPage.clickContinue();

        System.out.println("✅ TC16 PASSED: Order placed after login before checkout.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ---------------- [TC23: Verify address details in checkout page] ----------------- */
    /* ================================================================================== */
    @Test(priority = 3)
    @Description("TC23: Verify address details in checkout page")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifyAddressDetailsInCheckout()
    {
        System.out.println("========== TC23: Verify address details in checkout ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that the home page is visible =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Click 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Fill all details in Signup and create an account =====
        System.out.println("STEP 5: Filling signup details and creating account...");
        signupLoginPage.enterSignupDetails(newUserData.getName(), newUserData.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        signupDetailsPage.selectTitle(newUserData.getTitle());
        signupDetailsPage.enterPassword(newUserData.getPassword());
        signupDetailsPage.selectDateOfBirth(newUserData.getDay(), newUserData.getMonth(), newUserData.getYear());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                newUserData.getFirstName(), newUserData.getLastName(), newUserData.getCompany(),
                newUserData.getAddress1(), newUserData.getAddress2(), newUserData.getCity(),
                newUserData.getCountry(), newUserData.getState(), newUserData.getZipcode(), newUserData.getMobileNumber());

        // ===== STEP 6: Verify 'ACCOUNT CREATED!' and click 'Continue' button =====
        System.out.println("STEP 6: Verifying 'ACCOUNT CREATED!' and clicking Continue...");
        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "Account not created!");
        homePage = accountCreatedPage.clickContinueButton();

        // ===== STEP 7: Verify 'Logged in as username' at top =====
        System.out.println("STEP 7: Verifying 'Logged in as username'...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Not logged in!");

        // ===== STEP 8: Add products to cart =====
        System.out.println("STEP 8: Adding products to cart...");
        homePage.addToCartFromOverlayByIndex(productsIndexes.getFirstProductIndex());
        homePage.addToCartFromOverlayByIndex(productsIndexes.getSecondProductIndex());

        // ===== STEP 9: Click 'Cart' button =====
        System.out.println("STEP 9: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickViewCartButton();

        // ===== STEP 10: Verify that the cart page is displayed =====
        System.out.println("STEP 10: Verifying cart page is displayed...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed!");

        // ===== STEP 11: Click Proceed To Checkout =====
        System.out.println("STEP 11: Clicking 'Proceed To Checkout'...");
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // ===== STEP 12: Verify delivery address matches registration data =====
        System.out.println("STEP 12: Verifying delivery address...");
        String expectedAddress = buildExpectedAddress(newUserData);
        String deliveryAddress = checkoutPage.getDeliveryAddressText();
        for (String field : expectedAddress.split(" ")) {
            Assert.assertTrue(deliveryAddress.contains(field), "Delivery address missing: " + field);
        }

        // ===== STEP 13: Verify billing address matches registration data =====
        System.out.println("STEP 13: Verifying billing address...");
        String billingAddress = checkoutPage.getBillingAddressText();
        for (String field : expectedAddress.split(" ")) {
            Assert.assertTrue(billingAddress.contains(field), "Billing address missing: " + field);
        }

        System.out.println("✅ TC23 PASSED: Address details verified in checkout.");
        System.out.println("======================================================\n");
    }


    /* ================================================================================== */
    /* ---------------- [TC24: Download Invoice after purchase order] ------------------- */
    /* ================================================================================== */
    @Test(priority = 4)
    @Description("TC24: Download Invoice after purchase order")
    @Severity(SeverityLevel.NORMAL)
    public void testDownloadInvoiceAfterPurchaseOrder()
    {
        System.out.println("========== TC24: Download Invoice after purchase order ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that the home page is visible =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Add products to cart =====
        System.out.println("STEP 4: Adding products to cart...");
        homePage.addToCartFromOverlayByIndex(productsIndexes.getFirstProductIndex());
        homePage.addToCartFromOverlayByIndex(productsIndexes.getSecondProductIndex());

        // ===== STEP 5: Click 'Cart' button =====
        System.out.println("STEP 5: Clicking 'Cart' button...");
        CartPage cartPage = homePage.clickViewCartButton();

        // ===== STEP 6: Verify that the cart page is displayed =====
        System.out.println("STEP 6: Verifying cart page is displayed...");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed!");

        // ===== STEP 7 & 8: Click Proceed To Checkout → Register / Login =====
        System.out.println("STEP 7 & 8: Proceeding to checkout and clicking Register/Login...");
        SignupLoginPage signupLoginPage = cartPage.proceedToCheckoutAndRegister();

        // ===== STEP 9: Fill all details in Signup and create an account =====
        System.out.println("STEP 9: Filling signup details and creating account...");
        signupLoginPage.enterSignupDetails(newUserData.getName(), newUserData.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        signupDetailsPage.selectTitle(newUserData.getTitle());
        signupDetailsPage.enterPassword(newUserData.getPassword());
        signupDetailsPage.selectDateOfBirth(newUserData.getDay(), newUserData.getMonth(), newUserData.getYear());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                newUserData.getFirstName(), newUserData.getLastName(), newUserData.getCompany(),
                newUserData.getAddress1(), newUserData.getAddress2(), newUserData.getCity(),
                newUserData.getCountry(), newUserData.getState(), newUserData.getZipcode(), newUserData.getMobileNumber());

        // ===== STEP 10: Verify 'ACCOUNT CREATED!' and click 'Continue' =====
        System.out.println("STEP 10: Verifying 'ACCOUNT CREATED!' and clicking Continue...");
        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "Account not created!");
        homePage = accountCreatedPage.clickContinueButton();

        // ===== STEP 11: Verify 'Logged in as username' at top =====
        System.out.println("STEP 11: Verifying 'Logged in as username'...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Not logged in!");

        // ===== STEP 12: Click 'Cart' button =====
        System.out.println("STEP 12: Clicking 'Cart' button...");
        cartPage = homePage.clickCartButton();

        // ===== STEP 13: Click Proceed To Checkout button =====
        System.out.println("STEP 13: Clicking 'Proceed To Checkout'...");
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // ===== STEP 14: Verify Address Details and Review Your Order =====
        System.out.println("STEP 14: Verifying Address Details and Review Your Order...");
        Assert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address details not displayed!");
        Assert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "Review Your Order not displayed!");

        // ===== STEP 15: Enter description and click 'Place Order' =====
        System.out.println("STEP 15: Entering description and clicking Place Order...");
        checkoutPage.writeOrderMessage(orderData.getDescription());
        PaymentPage paymentPage = checkoutPage.clickPlaceOrderButton();

        // ===== STEP 16: Enter payment details =====
        System.out.println("STEP 16: Entering payment details...");
        paymentPage.enterPaymentDetails(
                paymentData.getCardName(), paymentData.getCardNumber(),
                paymentData.getCvc(), paymentData.getExpiryMonth(), paymentData.getExpiryYear());

        // ===== STEP 17: Click 'Pay and Confirm Order' =====
        System.out.println("STEP 17: Clicking 'Pay and Confirm Order'...");
        OrderPlacedPage orderPlacedPage = paymentPage.clickPlaceOrder();

        // ===== STEP 18: Verify success message =====
        System.out.println("STEP 18: Verifying order success message...");
        Assert.assertTrue(orderPlacedPage.isOrderPlacedSuccessMsgDisplayed(), "Order not confirmed!");

        // ===== STEP 19: Click 'Download Invoice' button =====
        System.out.println("STEP 19: Clicking 'Download Invoice'...");
        orderPlacedPage.clickDownloadInvoice();

        // ===== STEP 20: Click 'Continue' button =====
        System.out.println("STEP 20: Clicking Continue...");
        homePage = orderPlacedPage.clickContinue();

        // ===== STEP 21: Click 'Delete Account' button =====
        System.out.println("STEP 21: Clicking 'Delete Account'...");
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();

        // ===== STEP 22: Verify 'ACCOUNT DELETED!' and click 'Continue' =====
        System.out.println("STEP 22: Verifying 'ACCOUNT DELETED!' and clicking Continue...");
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "Account not deleted!");
        accountDeletedPage.clickContinue();

        System.out.println("✅ TC24 PASSED: Invoice downloaded and order complete.");
        System.out.println("=========================================================\n");
    }

}
