package Tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class ScrollUITests extends BaseTest
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
    /* ----------------- [TC25: Verify Scroll Up using 'Arrow' button] ------------------ */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC25: Verify Scroll Up using 'Arrow' button")
    @Severity(SeverityLevel.MINOR)
    public void testScrollUpUsingArrowButton()
    {
        System.out.println("========== TC25: Scroll Up using Arrow button ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Scroll down to bottom of page =====
        System.out.println("STEP 4: Scrolling down to footer...");
        FooterSection footer = homePage.getFooter();
        footer.scrollToFooter();

        // ===== STEP 5: Verify 'SUBSCRIPTION' is visible =====
        System.out.println("STEP 5: Verifying 'SUBSCRIPTION' is visible...");
        Assert.assertTrue(footer.isSubscriptionTextDisplayed(), "'SUBSCRIPTION' not visible!");

        // ===== STEP 6: Click on arrow at bottom right side to move upward =====
        System.out.println("STEP 6: Clicking scroll-up arrow...");
        homePage.clickScrollUpArrow();

        // ===== STEP 7: Verify that page is scrolled up and text is visible =====
        System.out.println("STEP 7: Verifying top text is visible...");
        Assert.assertTrue(homePage.isAutomationEngineersTextDisplayed(),
                "'Full-Fledged practice website for Automation Engineers' text not visible!");

        System.out.println("✅ TC25 PASSED: Scroll up using arrow button.");
        System.out.println("==============================================\n");
    }

    /* ================================================================================== */
    /* -------------- [TC26: Verify Scroll Up without 'Arrow' button] ------------------- */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC26: Verify Scroll Up without 'Arrow' button")
    @Severity(SeverityLevel.MINOR)
    public void testScrollUpWithoutArrowButton()
    {
        System.out.println("========== TC26: Scroll Up without Arrow button ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that home page is visible successfully =====
        System.out.println("STEP 3: Verifying home page is visible...");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed!");

        // ===== STEP 4: Scroll down to bottom of page =====
        System.out.println("STEP 4: Scrolling down to footer...");
        FooterSection footer = homePage.getFooter();
        footer.scrollToFooter();

        // ===== STEP 5: Verify 'SUBSCRIPTION' is visible =====
        System.out.println("STEP 5: Verifying 'SUBSCRIPTION' is visible...");
        Assert.assertTrue(footer.isSubscriptionTextDisplayed(), "'SUBSCRIPTION' not visible!");

        // ===== STEP 6: Scroll up to top of page =====
        System.out.println("STEP 6: Scrolling up to top...");
        homePage.scrollToTop();

        // ===== STEP 7: Verify that page is scrolled up and text is visible =====
        System.out.println("STEP 7: Verifying top text is visible...");
        Assert.assertTrue(homePage.isAutomationEngineersTextDisplayed(),"'Full-Fledged practice website for Automation Engineers' text not visible!");

        System.out.println("✅ TC26 PASSED: Scroll up without arrow button.");
        System.out.println("===================================================\n");
    }
}