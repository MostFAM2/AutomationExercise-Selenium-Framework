package Tests;

import DTOs.ContactUsDTO;
import Pages.*;
import Utils.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class ContactUsAndTestCasesTests extends BaseTest
{
    /* ================================================================================== */
    /* ------------------------------------ [SETUP] ------------------------------------- */
    /* ================================================================================== */

    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */

    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */


    /* ================================================================================== */
    /* ----------------------------- [TC6: Contact Us Form] ----------------------------- */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC6: Contact Us Form")
    @Severity(SeverityLevel.NORMAL)
    public void testContactUs()
    {
        System.out.println("========== TC6: Contact Us Form ==========");

        ContactUsDTO contactUsData = JsonReader.readJsonAsObject("ContactUsData.json", ContactUsDTO.class);
        Assert.assertNotNull(contactUsData, "Failed to load contact us data!");

        // ===== STEP 1: Launch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "HomePage is not displayed!");

        // ===== STEP 4: Click on 'Contact us' button =====
        System.out.println("STEP 4: Clicking 'Contact us' button...");
        ContactUsPage contactUsPage = homePage.clickContactUsButton();

        // ===== STEP 5: Verify 'GET IN TOUCH' is visible =====
        System.out.println("STEP 5: Verifying 'GET IN TOUCH' is visible...");
        Assert.assertTrue(contactUsPage.isGetInTouchDisplayed(), "'Get In Touch' is not displayed!");

        // ===== STEP 6: Enter name, email, subject and message =====
        System.out.println("STEP 6: Filling contact form details...");
        contactUsPage.fillInContactUsForm(
                contactUsData.getName(),
                contactUsData.getEmail(),
                contactUsData.getSubject(),
                contactUsData.getMessage());

        // ===== STEP 7: Upload file =====
        System.out.println("STEP 7: Uploading attachment...");
        String absolutePath = Paths.get(System.getProperty("user.dir"), contactUsData.getAttachmentPath()).toAbsolutePath().toString();
        contactUsPage.uploadAttachment(absolutePath);

        // ===== STEP 8: Click 'Submit' button =====
        System.out.println("STEP 8: Clicking Submit button...");
        contactUsPage.clickOnSubmitButton();

        // ===== STEP 9: Click OK on alert =====
        System.out.println("STEP 9: Accepting confirmation alert...");
        contactUsPage.acceptConfirmationAlert();

        // ===== STEP 10: Verify success message is visible =====
        System.out.println("STEP 10: Verifying success message is visible...");
        Assert.assertTrue(contactUsPage.isSuccessMessageDisplayed(),"'Success! Your details have been submitted successfully.' is not visible!");

        // ===== STEP 11: Click 'Home' button and verify landing on home page =====
        System.out.println("STEP 11: Clicking Home button and verifying home page...");
        homePage = contactUsPage.clickOnHomeButton();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "HomePage is not displayed!");

        System.out.println("✅ TC6 PASSED: Contact Us Form submitted successfully.");
        System.out.println("========================================================\n");
    }


    /* ================================================================================== */
    /* ---------------------- [TC7: Verify Test Cases Page] ----------------------------- */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC7: Verify Test Cases Page")
    @Severity(SeverityLevel.NORMAL)
    public void testTestCasesPage()
    {
        System.out.println("========== TC7: Verify Test Cases Page ==========");

        // ===== STEP 1: Launch / Setup Browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "HomePage is not displayed!");

        // ===== STEP 4: Click on 'Test Cases' button =====
        System.out.println("STEP 4: Clicking 'Test Cases' button...");
        TestCasesPage testCasesPage = homePage.clickTestCasesButton();

        // ===== STEP 5: Verify user is navigated to test cases page successfully =====
        System.out.println("STEP 5: Verifying Test Cases page is displayed...");
        Assert.assertTrue(testCasesPage.isTestCasesPageDisplayed(), "'Test Cases Page' is not displayed!");

        System.out.println("✅ TC7 PASSED: Test Cases Page open successfully.");
        System.out.println("========================================================\n");
    }
}
