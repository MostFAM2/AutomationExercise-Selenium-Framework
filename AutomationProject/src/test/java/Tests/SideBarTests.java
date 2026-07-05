package Tests;

import Pages.*;
import DTOs.SideBarDTO;

import Utils.JsonReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SideBarTests extends BaseTest
{

    private SideBarDTO sideBar;

    protected String testSideBarFile = "SideBarData.json";

    /* ================================================================================== */
    /* ------------------------------------ [SETUP] ------------------------------------- */
    /* ================================================================================== */
    /**
     * Runs before each test method.
     * Loads fresh test data.
     */
    @BeforeClass
    public void prepareTest()
    {
        sideBar = JsonReader.readJsonAsObject(testSideBarFile,  SideBarDTO.class);
        Assert.assertNotNull(sideBar, "Failed to load test data from: " +  testSideBarFile);
    }


    /* ================================================================================== */
    /* ---------------------------------- [CLEANUP] ------------------------------------- */
    /* ================================================================================== */



    /* ================================================================================== */
    /* ---------------------------------- [HELPERS] ------------------------------------- */
    /* ================================================================================== */


    /* ================================================================================== */
    /* ------------------------- [TC18: View Category Products] ------------------------- */
    /* ================================================================================== */
    @Test(priority = 0)
    @Description("TC18: View Category Products")
    @Severity(SeverityLevel.NORMAL)
    public void testViewCategoryProducts()
    {
        System.out.println("========== TC18: View Category Products ==========");

        // ===== STEP 1: Launch browser =====
        /* This Step is Done in the Base Test INITIALIZATION */
        System.out.println("STEP 1: Browser launched.");

        // ===== STEP 2: Open home page =====
        System.out.println("STEP 2: Opening home page...");
        HomePage homePage = new HomePage(fw);
        homePage.openHomePage();

        // ===== STEP 3: Verify that categories are visible on left sidebar =====
        System.out.println("STEP 3: Verifying categories sidebar is visible...");
        SideBarSection sideBarSection = homePage.getSidebar();
        Assert.assertTrue(sideBarSection.isSidebarDisplayed(), "Categories are not visible on left sidebar");

        // ===== STEP 4: Click on first category =====
        System.out.println("STEP 4: Clicking '" + sideBar.getFirstCategory() + "' category...");
        sideBarSection.clickCategory(sideBar.getFirstCategory());

        // ===== STEP 5: Click on subcategory under first category =====
        System.out.println("STEP 5: Clicking '" + sideBar.getFirstSubcategory() + "' subcategory...");
        sideBarSection.clickSubcategory(sideBar.getFirstSubcategory());

        // ===== STEP 6: Verify category page title =====
        System.out.println("STEP 6: Verifying category page title...");
        String categoryTitle1 = sideBarSection.getPageTitle();
        Assert.assertEquals(categoryTitle1, sideBar.getFirstExpectedTitle(),
                "Expected: " + sideBar.getFirstExpectedTitle() + " | Found: " + categoryTitle1);

        // ===== STEP 7: Click on second category and subcategory =====
        System.out.println("STEP 7: Clicking '" + sideBar.getSecondCategory() + "' → '" + sideBar.getSecondSubcategory() + "'...");
        sideBarSection.clickCategory(sideBar.getSecondCategory());
        sideBarSection.clickSubcategory(sideBar.getSecondSubcategory());

        // ===== STEP 8: Verify second category page title =====
        System.out.println("STEP 8: Verifying second category page title...");
        String categoryTitle2 = sideBarSection.getPageTitle();
        Assert.assertEquals(categoryTitle2, sideBar.getSecondExpectedTitle(),
                "Expected: " + sideBar.getSecondExpectedTitle() + " | Found: " + categoryTitle2);

        System.out.println("✅ TC18 PASSED: Categories Verified Successfully.");
        System.out.println("   " + sideBar.getFirstExpectedTitle() + " | " + sideBar.getSecondExpectedTitle());
        System.out.println("============================================\n");
    }


    /* ================================================================================== */
    /* ---------------------- [TC19: View & Cart Brand Products] ------------------------ */
    /* ================================================================================== */
    @Test(priority = 1)
    @Description("TC19: View & Cart Brand Products")
    @Severity(SeverityLevel.NORMAL)
    public void testViewCartBrandProducts()
    {
        System.out.println("========== TC19: View & Cart Brand Products ==========");

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

        // ===== STEP 4: Verify that Brands are visible on left side bar =====
        System.out.println("STEP 4: Verifying Brands section is visible...");
        SideBarSection sideBarSection = productPage.getSidebar();
        Assert.assertTrue(sideBarSection.isBrandsSectionDisplayed(), "Brands are not visible on left sidebar");

        // ===== STEP 5: Click on first brand =====
        System.out.println("STEP 5: Clicking '" + sideBar.getFirstBrand() + "' brand...");
        sideBarSection.clickBrand(sideBar.getFirstBrand());

        // ===== STEP 6: Verify first brand page =====
        System.out.println("STEP 6: Verifying first brand page title...");
        String brandTitle1 = sideBarSection.getPageTitle();
        Assert.assertEquals(brandTitle1, sideBar.getFirstBrandExpectedTitle(),
                "Expected: " + sideBar.getFirstBrandExpectedTitle() + " | Found: " + brandTitle1);

        // ===== STEP 7: Click on second brand =====
        System.out.println("STEP 7: Clicking '" + sideBar.getSecondBrand() + "' brand...");
        sideBarSection.clickBrand(sideBar.getSecondBrand());

        // ===== STEP 8: Verify second brand page =====
        System.out.println("STEP 8: Verifying second brand page title...");
        String brandTitle2 = sideBarSection.getPageTitle();
        Assert.assertEquals(brandTitle2, sideBar.getSecondBrandExpectedTitle(),
                "Expected: " + sideBar.getSecondBrandExpectedTitle() + " | Found: " + brandTitle2);

        System.out.println("✅ TC19 PASSED: Brands Verified Successfully.");
        System.out.println("   " + sideBar.getFirstBrandExpectedTitle() + " | " + sideBar.getSecondBrandExpectedTitle());
        System.out.println("============================================\n");
    }

}
