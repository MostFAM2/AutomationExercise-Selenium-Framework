package Pages;

import Utils.FrameWork;
import org.openqa.selenium.By;

public class ContactUsPage
{
    /* =================================================================================== */
    /* ----------------------------- [INITIALIZATION] ------------------------------------ */
    /* =================================================================================== */
    private final FrameWork fw;
    public ContactUsPage(FrameWork fw)
    {
        this.fw = fw;
    }


    /* =================================================================================== */
    /* -------------------------------- [LOCATORS] --------------------------------------- */
    /* =================================================================================== */
    private final By getInTouchLocator = By.cssSelector("div.contact-form > h2");
    private final By nameLocator = By.cssSelector("input[data-qa=\"name\"]");
    private final By emailLocator = By.cssSelector("input[data-qa=\"email\"]");
    private final By subjectLocator = By.cssSelector("input[data-qa=\"subject\"]");
    private final By messageLocator = By.cssSelector("textarea[data-qa=\"message\"]");
    private final By attachmentLocator = By.cssSelector("input[type=\"file\"]");
    private final By submitLocator = By.cssSelector("input[data-qa=\"submit-button\"]");
    private final By successMsgLocator = By.cssSelector("div.status.alert.alert-success");
    private final By homeButtonLocator = By.cssSelector("div#form-section a.btn");


    /* =================================================================================== */
    /* -------------------------------- [ACTIONS] ---------------------------------------- */
    /* =================================================================================== */

    /**
     * Checks if the 'GET IN TOUCH' title is visible.
     *
     * @return true if the title is displayed, false otherwise
     */
    public boolean isGetInTouchDisplayed()
    {
        return fw.isDisplayed(getInTouchLocator);
    }

    /**
     * Fills the contact form with the user's details.
     *
     * @param name    User's full name
     * @param email   User's email address
     * @param subject Message subject
     * @param message Message body text
     */
    public void fillInContactUsForm(String name, String email, String subject, String message)
    {
        fw.sendString(nameLocator, name);
        fw.sendString(emailLocator, email);
        fw.sendString(subjectLocator, subject);
        fw.sendString(messageLocator, message);
    }

    /**
     * Uploads a file attachment by sending the file path directly to the input element.
     *
     * @param filePath Absolute path to the file to upload.
     */
    public void uploadAttachment(String filePath)
    {
        fw.sendString(attachmentLocator, filePath);
    }

    /**
     * Clicks the Submit button to send the contact form.
     * An alert popup will appear asking for confirmation.
     */
    public void clickOnSubmitButton()
    {
        fw.clickOnElement(submitLocator);
    }

    /**
     * Accepts the browser alert that appears after submitting the form.
     */
    public void acceptConfirmationAlert()
    {
        fw.acceptAlert();
    }

    /**
     * Checks if the success message is visible after form submission.
     *
     * @return true if success message is displayed, false otherwise
     */
    public boolean isSuccessMessageDisplayed()
    {
        return fw.isDisplayed(successMsgLocator);
    }

    /**
     * Clicks the Home button to return to the home page.
     *
     * @return HomePage object for further navigation
     */
    public HomePage clickOnHomeButton()
    {
        fw.clickOnElement(homeButtonLocator);
        return new HomePage(fw);
    }
}
