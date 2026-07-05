package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class SignupLoginPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public SignupLoginPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By loginFormTitleLocator = By.cssSelector("div.login-form > h2");
    private final By loginEmailLocator = By.cssSelector("input[data-qa=\"login-email\"]");
    private final By loginPasswordLocator = By.cssSelector("input[data-qa=\"login-password\"]");
    private final By loginButtonLocator = By.cssSelector("button[data-qa=\"login-button\"]");

    private final By signupFormTitleLocator = By.cssSelector("div.signup-form > h2");
    private final By signupNameLocator = By.cssSelector("input[data-qa=\"signup-name\"]");
    private final By signupEmailLocator = By.cssSelector("input[data-qa=\"signup-email\"]");
    private final By signupButtonLocator = By.cssSelector("button[data-qa=\"signup-button\"]");

    private final By loginErrorMsgLocator = By.xpath("//p[contains(text(), 'Your email or password is incorrect!')]");
    private final By signupErrorMsgLocator = By.xpath("//p[contains(text(), 'Email Address already exist!')]");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the 'New User Signup!' form title is visible.
     *
     * @return true if signup form is displayed, false otherwise
     */
    public boolean isSignupFormDisplayed()
    {
        return fw.isDisplayed(signupFormTitleLocator);
    }

    /**
     * Fills the signup form with name and email.
     *
     * @param name  User's full name
     * @param email User's email address
     */
    public void enterSignupDetails(String name, String email)
    {
        fw.sendString(signupNameLocator, name);
        fw.sendString(signupEmailLocator, email);
    }

    /**
     * Clicks the Signup button and navigates to the registration form.
     *
     * @return SignupDetailsPage object for filling account details
     */
    public SignupDetailsPage clickSignupButton()
    {
        fw.clickOnElement(signupButtonLocator);
        return new SignupDetailsPage(fw);
    }

    /**
     * Checks if signup error message is visible after singUp attempt.
     *
     * @return true if error message is displayed, false otherwise
     */
    public boolean isSignupErrorMsgDisplayed()
    {
        return fw.isDisplayed(signupErrorMsgLocator);
    }

    /**
     * Checks if the 'Login to your account' form title is visible.
     *
     * @return true if login form is displayed, false otherwise
     */
    public boolean isLoginFormDisplayed()
    {
        return fw.isDisplayed(loginFormTitleLocator);
    }

    /**
     * Fills the login form with email and password.
     *
     * @param email    User's registered email
     * @param password User's account password
     */
    public void enterLoginDetails(String email, String password)
    {
        fw.sendString(loginEmailLocator, email);
        fw.sendString(loginPasswordLocator, password);
    }

    /**
     * Clicks the Login button to submit the login form.
     *
     * @return HomePage Object.
     */
    public HomePage clickLoginButton()
    {
        fw.clickOnElement(loginButtonLocator);
        return new HomePage(fw);
    }

    /**
     * Checks if login error message is visible after failed login attempt.
     *
     * @return true if error message is displayed, false otherwise
     */
    public boolean isLoginErrorMsgDisplayed()
    {
        return fw.isDisplayed(loginErrorMsgLocator);
    }


}
