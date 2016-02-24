package edu.ucla.ctsi.web.automation;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.util.Strings;

/**
 * Prerequisites: WISE should be up and running on localhost. Run this as a
 * junit test.
 * 
 * 
 * @author pdessai
 * 
 */
public class WISEDevDeploymentTester {

    private static final String WISE_ADMIN_URL = "http://local.dev:8080/WISE/admin";
    private static final String WISE_ADMIN_USERNAME = "wisedev";
    private static final String WISE_ADMIN_PASSWORD = "password";

    private static final String WISE_SURVEY_USER_FIRSTNAME = "Pralav";
    private static final String WISE_SURVEY_USER_LASTNAME = "Dessai";
    private static final String WISE_SURVEY_USER_EMAIL = "pralavgoa@gmail.com";
    private static final String WISE_SURVEY_USER_PHONE = "9999999999";

    @Test
    public void seleniumTestCase() {
        WebDriver driver = new FirefoxDriver();

        this.login(driver);

        String anonymousInviteeSurveyUrl = this.getAnonymousInviteeUrl(driver);

        if (!Strings.isNullOrEmpty(anonymousInviteeSurveyUrl)) {
            driver.get(anonymousInviteeSurveyUrl);
        }

        this.enterInviteeInformation(driver);
    }

    private void login(WebDriver driver) {
        driver.get(WISE_ADMIN_URL);
        WebElement usernameElement = driver.findElement(By.name("username"));
        usernameElement.sendKeys(WISE_ADMIN_USERNAME);
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(WISE_ADMIN_PASSWORD);
        WebElement formElement = driver.findElement(By.name("login-form"));
        formElement.submit();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().contains("wise");
            }
        });
    }

    private String getAnonymousInviteeUrl(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.tagName("a"));

        String anonymousInviteeSurveyUrl = "";
        for (WebElement element : elements) {
            String anchorText = element.getText();
            if (anchorText.contains("survey?")) {
                anonymousInviteeSurveyUrl = anchorText;
            }
        }
        return anonymousInviteeSurveyUrl;
    }

    private void enterInviteeInformation(WebDriver driver) {
        WebElement firstnameElement = driver.findElement(By.name("firstname"));
        firstnameElement.sendKeys(WISE_SURVEY_USER_FIRSTNAME);
        WebElement lastnameElement = driver.findElement(By.name("lastname"));
        lastnameElement.sendKeys(WISE_SURVEY_USER_LASTNAME);
        WebElement emailElement = driver.findElement(By.name("email"));
        emailElement.sendKeys(WISE_SURVEY_USER_EMAIL);
        WebElement phoneNumberElement = driver.findElement(By.name("phone"));
        phoneNumberElement.sendKeys(WISE_SURVEY_USER_PHONE);

        WebElement formElement = driver.findElement(By.name("form2"));
        formElement.submit();
    }
}
