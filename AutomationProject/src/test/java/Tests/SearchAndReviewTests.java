package Tests;

import DTOs.ReviewDTO;
import Pages.*;
import DTOs.NewUserDTO;

import Utils.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchAndReviewTests extends BaseTest
{

    private NewUserDTO newUser;
    private ReviewDTO review;

    protected String testRegisterDataFile = "RegisterUserData.json" ;
    protected String testReviewDataFile = "ReviewData.json" ;

    protected String searchText = "T-Shirt";


    /* ================================================================================== */
    /* ------------------------------------ [SETUP] ------------------------------------- */
    /* ================================================================================== */
    @BeforeClass
    public void prepareTest()
    {
        newUser = JsonReader.readJsonAsObject( testRegisterDataFile , NewUserDTO.class );
        Assert.assertNotNull(newUser, "Failed To Load Data From: " + testRegisterDataFile);

        review = JsonReader.readJsonAsObject( testReviewDataFile , ReviewDTO.class );
        Assert.assertNotNull(review, "Failed To Load Data From: " + testReviewDataFile);

        registerUser();
    }


    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */

    @AfterClass
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
                System.out.println("Deleted: " + newUser.getEmail());
                System.out.println("==============================\n");
                return;
            }

            SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
            Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login form not visible!");
            signupLoginPage.enterLoginDetails(newUser.getEmail(), newUser.getPassword());
            homePage = signupLoginPage.clickLoginButton();

            if (!homePage.isLoggedAsDisplayed())
            {
                System.out.println("Account already deleted: " + newUser.getEmail() + " — skipping.");
                System.out.println("==============================\n");
                return;
            }

            AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
            accountDeletedPage.clickContinue();
            System.out.println("Deleted: " + newUser.getEmail());
            System.out.println("==============================\n");

        }
        catch (Exception e)
        {
            System.out.println("Could not delete " + newUser.getEmail() + ": " + e.getMessage());
        }
        System.out.println("==============================\n");
    }



    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */

    private void registerUser()
    {
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
        Assert.assertTrue(signupLoginPage.isSignupFormDisplayed(), "Signup form not displayed!");

        signupLoginPage.enterSignupDetails(newUser.getName(), newUser.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();
        Assert.assertTrue(signupDetailsPage.isEnterAccountInfoDisplayed(), "Account info not displayed!");

        signupDetailsPage.selectTitle(newUser.getTitle());
        signupDetailsPage.enterPassword(newUser.getPassword());
        signupDetailsPage.selectDateOfBirth(newUser.getDay(), newUser.getMonth(), newUser.getYear());
        signupDetailsPage.subscribeToNewsLetter();
        signupDetailsPage.receiveSpecialOffers();
        signupDetailsPage.fillAccountDetails(
                newUser.getFirstName(), newUser.getLastName(), newUser.getCompany(),
                newUser.getAddress1(), newUser.getAddress2(), newUser.getCity(),
                newUser.getCountry(), newUser.getState(), newUser.getZipcode(), newUser.getMobileNumber());

        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "Account not created!");

        homePage = accountCreatedPage.clickContinueButton();
        homePage.clickLogOutButton();

        System.out.println("[SETUP] User registered: " + newUser.getEmail());
        System.out.println("============================================");
    }



    /* ================================================================================== */
    /* -------------- [TC20: Search Products and Verify Cart After Login] --------------- */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC20: Search Products and Verify Cart After Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchProductsAndVerifyCartAfterLogin()
    {
        System.out.println("========== TC20: Search Products and Verify Cart After Login ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Click on 'Products' button =====
        System.out.println("STEP 3: Clicking 'Products' button...");
        ProductPage productPage = homePage.clickProductsButton();

        // ===== STEP 4: Verify ALL PRODUCTS page =====
        System.out.println("STEP 4: Verifying ALL PRODUCTS page...");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page not displayed!");

        // ===== STEP 5: Enter product name and click search =====
        System.out.println("STEP 5: Searching for '" + searchText + "'...");
        productPage.enterSearchBarText(searchText);
        productPage.clickSearchButton();

        // ===== STEP 6: Verify 'SEARCHED PRODUCTS' is visible =====
        System.out.println("STEP 6: Verifying 'SEARCHED PRODUCTS' is visible...");
        Assert.assertTrue(productPage.isSearchedProductsDisplayed(), "'SEARCHED PRODUCTS' is not visible!");

        // ===== STEP 7: Verify results are related =====
        System.out.println("STEP 7: Verifying results are related to search...");
        Assert.assertTrue(productPage.areAllProductsRelatedToSearch(searchText), "Search result not related to Search Text!");

        // ===== STEP 8: Add those products to cart =====
        System.out.println("STEP 8: Adding search results to cart...");
        List<String> addedProducts = new ArrayList<>();
        int searchResultCount = productPage.getSearchResultsCount();
        for (int i = 0; i < searchResultCount; i++)
        {
            addedProducts.add(productPage.gerProductNameByIndex(i));
            productPage.addToCartFromOverlayByIndex(i);
            productPage.clickContinueShoppingButton();
        }

        // ===== STEP 9: Click 'Cart' and verify products are visible =====
        System.out.println("STEP 9: Verifying products in cart...");
        CartPage cartPage = productPage.clickCartButton();
        for (String productName : addedProducts) {
            Assert.assertTrue(cartPage.getCartProductNames().contains(productName), "Product not found: " + productName);
        }

        // ===== STEP 10: Click 'Signup / Login' and login =====
        System.out.println("STEP 10: Logging in...");
        SignupLoginPage signupLoginPage = cartPage.clickSignupLoginButton();
        signupLoginPage.enterLoginDetails(newUser.getEmail(), newUser.getPassword());
        homePage = signupLoginPage.clickLoginButton();

        // ===== STEP 11: Go to Cart again =====
        System.out.println("STEP 11: Navigating to Cart after login...");
        cartPage = homePage.clickCartButton();

        // ===== STEP 12: Verify products are still in cart after login =====
        System.out.println("STEP 12: Verifying products persist after login...");
        for (String productName : addedProducts)
        {
            Assert.assertTrue(cartPage.getCartProductNames().contains(productName), "Product not found: " + productName);
        }

        System.out.println("✅ TC20 PASSED: " + addedProducts.size() + " products persist after login.");
        System.out.println("========================================================================\n");
    }


    /* ================================================================================== */
    /* ----------------------- [TC21: Add review on product] ---------------------------- */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC21: Add review on product")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddReviewOnProduct()
    {
        System.out.println("========== TC21: Add review on product ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Click on 'Products' button =====
        System.out.println("STEP 3: Clicking 'Products' button...");
        ProductPage productPage = homePage.clickProductsButton();

        // ===== STEP 4: Verify ALL PRODUCTS page =====
        System.out.println("STEP 4: Verifying ALL PRODUCTS page...");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "ALL PRODUCTS page not displayed!");

        // ===== STEP 5: Click on 'View Product' button =====
        System.out.println("STEP 5: Clicking 'View Product' on product index " + review.getProductIndex() + "...");
        ProductDetailsPage productDetailsPage = productPage.clickViewProductByIndex(review.getProductIndex());

        // ===== STEP 6: Verify 'Write Your Review' is visible =====
        System.out.println("STEP 6: Verifying 'Write Your Review' is visible...");
        Assert.assertTrue(productDetailsPage.isWriteYourReviewDisplayed(), "'Write Your Review' is not visible!");

        // ===== STEP 7: Enter name, email and review =====
        System.out.println("STEP 7: Filling review form...");
        productDetailsPage.fillReviewForm(review.getReviewName(), review.getReviewEmail(), review.getReviewText());

        // ===== STEP 8: Click 'Submit' button =====
        System.out.println("STEP 8: Clicking Submit...");
        productDetailsPage.clickReviewSubmit();

        // ===== STEP 9: Verify success message =====
        System.out.println("STEP 9: Verifying success message...");
        Assert.assertTrue(productDetailsPage.isReviewSuccessMessageDisplayed(), "'Thank you for your review.' is not visible!");

        System.out.println("✅ TC21 PASSED: Review submitted successfully.");
        System.out.println("   Product index: " + review.getProductIndex());
        System.out.println("   Reviewer: " + review.getReviewName());
        System.out.println("=========================================================\n");
    }



}
