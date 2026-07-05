package Tests;

import DTOs.ExistingUserDTO;
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

import java.util.List;


public class UserAuthTests extends BaseTest
{
    private NewUserDTO newUser;

    private ExistingUserDTO existingUser1;
    private ExistingUserDTO existingUser2;

    protected String testRegisterDataFile = "RegisterUserData.json";
    protected String testExistingDataFile = "ExistingUserData.json";


    /* ================================================================================== */
    /* ------------------------------------ [SETUP] ------------------------------------- */
    /* ================================================================================== */
    @BeforeClass
    public void prepareTest()
    {
        newUser = JsonReader.readJsonAsObject(testRegisterDataFile, NewUserDTO.class);
        Assert.assertNotNull(newUser, "Failed to load test data from: " + testRegisterDataFile);


        List<ExistingUserDTO> existingUsers = JsonReader.readJsonAsList(testExistingDataFile, ExistingUserDTO.class);
        Assert.assertNotNull(existingUsers, "Failed to load Data From: " + testExistingDataFile);
        Assert.assertTrue(existingUsers.size() >= 2, "Need at least 2 users in JSON!");

        existingUser1 = existingUsers.get(0);
        existingUser2 = existingUsers.get(1);

        System.out.println("[SETUP] User1: " + existingUser1.getEmail());
        System.out.println("==============================");
        registerUser(existingUser1);

        System.out.println("[SETUP] User2: " + existingUser2.getEmail());
        System.out.println("==============================");
        registerUser(existingUser2);

        System.out.println("[SETUP] Both users registered. Ready for tests.");
        System.out.println("==============================");

    }



    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */

    /**
     * Deletes both existing user accounts after all tests complete.
     */
    @AfterClass
    public void cleanupExistingAccounts()
    {
        deleteUser(existingUser1);
        deleteUser(existingUser2);

        System.out.println("[CLEANUP] Both existing accounts deleted.");
        System.out.println("==============================");

    }


    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */

    /**
     * Registers a single user account.
     *
     * @param user The user DTO to register
     */
    private void registerUser(ExistingUserDTO user)
    {
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
        Assert.assertTrue(signupLoginPage.isSignupFormDisplayed(), "Signup Form not displayed!");

        signupLoginPage.enterSignupDetails(user.getName(), user.getEmail());
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        Assert.assertTrue(signupDetailsPage.isEnterAccountInfoDisplayed(), "Account Info not displayed!");

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
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(), "Account Created not displayed!");

        homePage = accountCreatedPage.clickContinueButton();
        homePage.clickLogOutButton();

        System.out.println("Registered: " + user.getEmail());
        System.out.println("==============================");
    }

    /**
     * Logs out the current user if they are logged in.
     * Does nothing if user already logged out.
     *
     * @param homePage Object From HomePage
     */
    private void logoutIfNeeded(HomePage homePage)
    {
        if (homePage.isLoggedAsDisplayed())
        {
            homePage.clickLogOutButton();

            System.out.println("Logged out before test.");
            System.out.println("=======================");
        }
    }

    /**
     * Logs in with specified credentials if not already logged in.
     *
     * @param homePage HomePage object
     * @param email    User email
     * @param password User password
     */
    private void loginIfNeeded(HomePage homePage, String email, String password)
    {
        if (!homePage.isLoggedAsDisplayed())
        {
            SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
            Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login Form not visible!");
            signupLoginPage.enterLoginDetails(email, password);
            signupLoginPage.clickLoginButton();
            Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Login failed!");
        }
    }

    /**
     * Deletes a single user account.
     *
     * @param user The user DTO to delete
     */
    private void deleteUser(ExistingUserDTO user)
    {
        try
        {
            HomePage homePage = new HomePage(fw);
            homePage.openHomePage();

            if (homePage.isLoggedAsDisplayed())
            {
                AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
                accountDeletedPage.clickContinue();
                System.out.println("Deleted: " + user.getEmail());
                System.out.println("==============================\n");
                return;
            }

            SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();
            Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login form not visible!");
            signupLoginPage.enterLoginDetails(user.getEmail(), user.getPassword());
            homePage = signupLoginPage.clickLoginButton();

            if (!homePage.isLoggedAsDisplayed())
            {
                System.out.println("Account already deleted: " + user.getEmail() + " — skipping.");
                System.out.println("==============================\n");
                return;
            }

            AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();
            accountDeletedPage.clickContinue();
            System.out.println("Deleted: " + user.getEmail());
            System.out.println("==============================\n");

        }
        catch (Exception e)
        {
            System.out.println("Could not delete " + user.getEmail() + ": " + e.getMessage());
        }
        System.out.println("==============================\n");
    }


    /* ================================================================================== */
    /* ------------------------------ [TC1: Register User] ------------------------------ */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC1: Register User")
    @Severity(SeverityLevel.CRITICAL)
    public void testRegisterUser()
    {
        System.out.println("========== TC1: Register User ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is Not Displayed!");

        // ===== STEP 4: Click on 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Verify 'New User Signup!' is visible =====
        System.out.println("STEP 5: Verifying 'New User Signup!' is visible...");
        Assert.assertTrue(signupLoginPage.isSignupFormDisplayed(),"Signup Form Title is not Displayed!");

        // ===== STEP 6: Enter name and email address, click Signup =====
        System.out.println("STEP 6: Entering name and email address...");
        signupLoginPage.enterSignupDetails(newUser.getName(), newUser.getEmail());

        // ===== STEP 7: Click Signup =====
        System.out.println("STEP 7: Clicking Signup button...");
        SignupDetailsPage signupDetailsPage = signupLoginPage.clickSignupButton();

        // ===== STEP 8: Verify that 'ENTER ACCOUNT INFORMATION' is visible =====
        System.out.println("STEP 8: Verifying 'ENTER ACCOUNT INFORMATION' is visible...");
        Assert.assertTrue(signupDetailsPage.isEnterAccountInfoDisplayed(),"'Enter Account Information' Title is not Displayed!");

        // ===== STEP 9: Fill account details: Title, Name, Email, Password, Date of birth =====
        System.out.println("STEP 9: Filling account details...");
        signupDetailsPage.selectTitle(newUser.getTitle());
        signupDetailsPage.enterPassword(newUser.getPassword());
        signupDetailsPage.selectDateOfBirth(newUser.getDay(),  newUser.getMonth(), newUser.getYear());

        // ===== STEP 10: Select checkbox 'Sign up for our newsletter!' =====
        System.out.println("STEP 10: Selecting 'Sign up for our newsletter!'...");
        signupDetailsPage.subscribeToNewsLetter();

        // ===== STEP 11: Select checkbox 'Receive special offers from our partners!' =====
        System.out.println("STEP 11: Selecting 'Receive special offers from our partners!'...");
        signupDetailsPage.receiveSpecialOffers();

        // ===== STEP 12: Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number =====
        System.out.println("STEP 12: Filling address details...");
        signupDetailsPage.fillAccountDetails(
                newUser.getFirstName(), newUser.getLastName(), newUser.getCompany(),
                newUser.getAddress1(), newUser.getAddress2(), newUser.getCity(),
                newUser.getCountry(), newUser.getState(), newUser.getZipcode(), newUser.getMobileNumber());

        // ===== STEP 13: Click Create Account =====
        System.out.println("STEP 13: Clicking Create Account...");
        AccountCreatedPage accountCreatedPage = signupDetailsPage.clickCreateAccountButton();

        // ===== STEP 14: Verify 'Account Created!' is visible =====
        System.out.println("STEP 14: Verifying 'Account Created!' is visible...");
        Assert.assertTrue(accountCreatedPage.isAccountCreatedDisplayed(),"'Account Created' Title is not Displayed!");

        // ===== STEP 15: Click Continue =====
        System.out.println("STEP 15: Clicking Continue...");
        homePage = accountCreatedPage.clickContinueButton();

        // ===== STEP 16: Verify that 'Logged in as username' is visible =====
        System.out.println("STEP 16: Verifying 'Logged in as username' is visible...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(),"'Logged In As' is not Displayed!");

        // ===== STEP 17: Click 'Delete Account' button =====
        System.out.println("STEP 17: Clicking 'Delete Account'...");
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();

        // ===== STEP 18: Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button =====
        System.out.println("STEP 18: Verifying 'ACCOUNT DELETED!' and clicking Continue...");
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "'Account Deleted' is not Visible!");
        homePage = accountDeletedPage.clickContinue();

        System.out.println("✅ TC1 PASSED: User Registered Successfully.");
        System.out.println("============================================\n");
    }


    /* ================================================================================== */
    /* ---------------- [TC2: Login User with correct email and password] --------------- */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC2: Login User with correct email and password")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithCorrectCredentials()
    {
        System.out.println("========== TC2: Login with correct credentials ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is Not Displayed!");

        // ===== STEP 4: Click on 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Verify 'Login to your account' is visible =====
        System.out.println("STEP 5: Verifying 'Login to your account' is visible...");
        Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login Form Title is not Visible!");

        // ===== STEP 6: Enter correct email address and password =====
        System.out.println("STEP 6: Entering correct email and password...");
        signupLoginPage.enterLoginDetails(existingUser1.getEmail(), existingUser1.getPassword());

        // ===== STEP 7: Click 'login' button =====
        System.out.println("STEP 7: Clicking Login button...");
        homePage = signupLoginPage.clickLoginButton();

        // ===== STEP 8: Verify that 'Logged in as username' is visible =====
        System.out.println("STEP 8: Verifying 'Logged in as username' is visible...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Login failed!");

        // ===== STEP 9: Click 'Delete Account' button =====
        System.out.println("STEP 9: Clicking 'Delete Account'...");
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountButton();

        // ===== STEP 10: Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button =====
        System.out.println("STEP 10: Verifying 'ACCOUNT DELETED!' and clicking Continue...");
        Assert.assertTrue(accountDeletedPage.isAccountDeletedVisible(), "'Account Deleted' is not Visible!");
        homePage = accountDeletedPage.clickContinue();

        System.out.println("✅ TC2 PASSED: Login User with correct email and password Successfully.");
        System.out.println("=======================================================================\n");
    }

    /* ================================================================================== */
    /* --------------- [TC3: Login User with incorrect email and password] -------------- */
    /* ================================================================================== */
    @Test(priority = 2)
    @Description("TC3: Login User with incorrect email and password")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithIncorrectCredentials()
    {
        System.out.println("========== TC3: Login with incorrect credentials ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is Not Displayed!");

        // ===== STEP 4: Click on 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Verify 'Login to your account' is visible =====
        System.out.println("STEP 5: Verifying 'Login to your account' is visible...");
        Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login Form Title is not Visible!");

        // ===== STEP 6: Enter incorrect email address and password =====
        System.out.println("STEP 6: Entering incorrect email and password...");
        signupLoginPage.enterLoginDetails("Wrong@email.com", "WrongPassword");

        // ===== STEP 7: Click 'login' button =====
        System.out.println("STEP 7: Clicking Login button...");
        signupLoginPage.clickLoginButton();

        // ===== STEP 8: Verify error 'Your email or password is incorrect!' is visible =====
        System.out.println("STEP 8: Verifying error message is visible...");
        Assert.assertTrue(signupLoginPage.isLoginErrorMsgDisplayed(),"Login Success!");

        System.out.println("✅ TC3 PASSED: Login with incorrect credentials shows error.");
        System.out.println("============================================================\n");
    }


    /* ================================================================================== */
    /* ------------------------------ [TC4: Logout User] -------------------------------- */
    /* ================================================================================== */
    @Test(priority = 3)
    @Description("TC4: Logout User")
    @Severity(SeverityLevel.NORMAL)
    public void testLogout()
    {
        System.out.println("========== TC4: Logout User ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is Not Displayed!");

        // ===== STEP 4: Click on 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Verify 'Login to your account' is visible =====
        System.out.println("STEP 5: Verifying 'Login to your account' is visible...");
        Assert.assertTrue(signupLoginPage.isLoginFormDisplayed(), "Login Form Title is not Visible!");

        // ===== STEP 6: Enter correct email address and password =====
        System.out.println("STEP 6: Entering correct email and password...");
        signupLoginPage.enterLoginDetails(existingUser2.getEmail(), existingUser2.getPassword() );

        // ===== STEP 7: Click 'login' button =====
        System.out.println("STEP 7: Clicking Login button...");
        homePage = signupLoginPage.clickLoginButton();

        // ===== STEP 8: Verify that 'Logged in as username' is visible =====
        System.out.println("STEP 8: Verifying 'Logged in as username' is visible...");
        Assert.assertTrue(homePage.isLoggedAsDisplayed(), "Login failed!");

        // ===== STEP 9: Click 'Logout' button =====
        System.out.println("STEP 9: Clicking Logout button...");
        homePage.clickLogOutButton();

        // ===== STEP 10: Verify that user is navigated to login page =====
        System.out.println("STEP 10: Verifying user is navigated to home page...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is Not Displayed!");

        System.out.println("✅ TC4 PASSED: Logout successful.");
        System.out.println("==================================\n");
    }


    /* ================================================================================== */
    /* ------------------ [TC5: Register User with existing email] ---------------------- */
    /* ================================================================================== */
    @Test(priority = 4)
    @Description("TC5: Register User with existing email")
    @Severity(SeverityLevel.NORMAL)
    public void testRegisterUserWithExistingEmail()
    {
        System.out.println("========== TC5: Register with existing email ==========");

        // ===== STEP 1: Lunch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is Not Displayed!");

        // ===== STEP 4: Click on 'Signup / Login' button =====
        System.out.println("STEP 4: Clicking 'Signup / Login' button...");
        SignupLoginPage signupLoginPage = homePage.clickSignupLoginButton();

        // ===== STEP 5: Verify 'New User Signup!' is visible =====
        System.out.println("STEP 5: Verifying 'New User Signup!' is visible...");
        Assert.assertTrue(signupLoginPage.isSignupFormDisplayed(), "Signup Form Title is not Visible!");

        // ===== STEP 6: Enter name and already registered email address =====
        System.out.println("STEP 6: Entering name and already registered email...");
        signupLoginPage.enterSignupDetails(existingUser2.getName(), existingUser2.getEmail());

        // ===== STEP 7: Click 'Signup' button =====
        System.out.println("STEP 7: Clicking Signup button...");
        signupLoginPage.clickSignupButton();

        // ===== STEP 8: Verify error 'Email Address already exist!' is visible =====
        System.out.println("STEP 8: Verifying error message is visible...");
        Assert.assertTrue(signupLoginPage.isSignupErrorMsgDisplayed(),"Signup Success!");

        System.out.println("✅ TC5 PASSED: Register User with existing email shows error.");
        System.out.println("=============================================================\n");
    }

}
