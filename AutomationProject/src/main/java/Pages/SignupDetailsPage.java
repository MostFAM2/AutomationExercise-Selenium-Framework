package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupDetailsPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public SignupDetailsPage(FrameWork fw)
    {
        this.fw = fw;
    }

    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By enterAccountInfoLocator = By.cssSelector("div.login-form > h2 > b");

    private final By titleMrLocator = By.cssSelector("input#id_gender1");
    private final By titleMrsLocator = By.cssSelector("input#id_gender2");

    private final By nameLocator = By.cssSelector("input[data-qa=\"name\"]");
    private final By emailLocator = By.cssSelector("input[data-qa=\"email\"]");
    private final By passwordLocator = By.cssSelector("input[data-qa=\"password\"]");

    private final By dropdownDayLocator = By.cssSelector("select[data-qa=\"days\"]");
    private final By dropdownMonthLocator = By.cssSelector("select[data-qa=\"months\"]");
    private final By dropdownYearLocator = By.cssSelector("select[data-qa=\"years\"]");

    private final By newsLetterCheckBoxLocator = By.cssSelector("input#newsletter");
    private final By specialOfferCheckBoxLocator = By.cssSelector("input#optin");

    private final By firstNameLocator = By.cssSelector("input[data-qa=\"first_name\"]");
    private final By lastNameLocator = By.cssSelector("input[data-qa=\"last_name\"]");
    private final By companyLocator = By.cssSelector("input[data-qa=\"company\"]");
    private final By address1Locator = By.cssSelector("input[data-qa=\"address\"]");
    private final By address2Locator = By.cssSelector("input[data-qa=\"address2\"]");
    private final By countryLocator = By.cssSelector("select[data-qa=\"country\"]");
    private final By stateLocator = By.cssSelector("input[data-qa=\"state\"]");
    private final By cityLocator = By.cssSelector("input[data-qa=\"city\"]");
    private final By zipcodeLocator = By.cssSelector("input[data-qa=\"zipcode\"]");
    private final By mobileNumberLocator = By.cssSelector("input[data-qa=\"mobile_number\"]");

    private final By createAccountLocator = By.cssSelector("button[data-qa=\"create-account\"]");

    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Verifies that 'Enter Account Information' title is visible.
     *
     * @return true if the title is displayed, false otherwise
     */
    public boolean isEnterAccountInfoDisplayed()
    {
        return fw.isDisplayed(enterAccountInfoLocator);
    }

    /**
     * Selects the title (Mr/Mrs) for the account.
     *
     * @param title "Mr" or "Mrs"
     */
    public void selectTitle(String title)
    {
        if (title.equalsIgnoreCase("Mr"))
        {
            fw.clickOnElement(titleMrLocator);
        }
        else
        {
            fw.clickOnElement(titleMrsLocator);
        }
    }

    /**
     * Enters the account password.
     *
     * @param password Account password
     */
    public void enterPassword(String password)
    {
        fw.sendString(passwordLocator, password);
    }

    /**
     * Selects date of birth from dropdowns using framework's select methods.
     *
     * @param day   Day value attribute (e.g., "15")
     * @param month Month visible text (e.g., "June")
     * @param year  Year value attribute (e.g., "1990")
     */
    public void selectDateOfBirth(String day, String month, String year)
    {
        fw.selectByValue(dropdownDayLocator,day);
        fw.selectByVisibleText(dropdownMonthLocator,month);
        fw.selectByValue(dropdownYearLocator,year);
    }

    /**
     * Checks the Newsletter checkbox.
     */
    public void subscribeToNewsLetter()
    {
        fw.clickOnElement(newsLetterCheckBoxLocator);
    }

    /**
     * Checks the Special Offers checkbox.
     */
    public void receiveSpecialOffers()
    {
        fw.clickOnElement(specialOfferCheckBoxLocator);
    }

    /**
     * Fills all account information fields.
     *
     * @param firstName    First name
     * @param lastName     Last name
     * @param company      Company name
     * @param address1     Primary address
     * @param address2     Secondary address
     * @param country      Country name (selected by visible text)
     * @param state        State name
     * @param city         City name
     * @param zipcode      Zip/postal code
     * @param mobileNumber Mobile phone number
     */
    public void fillAccountDetails(String firstName, String lastName, String company,
                                   String address1, String address2, String city,
                                   String country, String state, String zipcode, String mobileNumber)
    {
        fw.sendString(firstNameLocator,firstName);
        fw.sendString(lastNameLocator,lastName);
        fw.sendString(companyLocator,company);
        fw.sendString(address1Locator,address1);
        fw.sendString(address2Locator,address2);
        fw.sendString(cityLocator,city);
        fw.selectByVisibleText(countryLocator,country);
        fw.sendString(stateLocator,state);
        fw.sendString(zipcodeLocator,zipcode);
        fw.sendString(mobileNumberLocator,mobileNumber);
    }

    /**
     * Clicks the 'Create Account' button and navigates to confirmation page.
     *
     * @return AccountCreatedPage object for verification
     */
    public AccountCreatedPage clickCreateAccountButton()
    {
        fw.clickOnElement(createAccountLocator);
        return new AccountCreatedPage(fw);
    }




}
