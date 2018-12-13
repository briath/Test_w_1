package ru.nsu.fit.tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.fit.services.browser.Browser;
import ru.nsu.fit.services.browser.BrowserService;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

/**
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class AcceptanceTest {
    private Browser browser = null;

    @BeforeClass
    public void beforeClass() {
        browser = BrowserService.openNewBrowser();
    }

    @Test
    @Title("Create customer")
    @Description("Create customer via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void createCustomer() {
        Browser browser = BrowserService.openNewBrowser();

        // login to admin cp
        browser.openPage("http://localhost:8080/endpoint");
        browser.waitForElement(By.id("email"));

        browser.getElement(By.id("email")).sendKeys("admin");
        browser.getElement(By.id("password")).sendKeys("setup");

        browser.getElement(By.id("login")).click();

        // create customer
        browser.getElement(By.id("add_new_customer")).click();

        browser.getElement(By.id("first_name_id")).sendKeys("John");
        browser.getElement(By.id("last_name_id")).sendKeys("Weak");
        browser.getElement(By.id("email_id")).sendKeys("email@example.com");
        browser.getElement(By.id("password_id")).sendKeys("strongpass");

        browser.getElement(By.id("create_customer_id")).click();
    }

    @Test(dependsOnMethods = "createCustomer")
    @Title("Check login")
    @Description("Get customer id by login")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Customer feature")
    public void checkCustomer() {
       // TODO: need implement
    }

    @AfterClass
    public void afterClass() {
        if (browser != null)
            browser.close();
    }
}
