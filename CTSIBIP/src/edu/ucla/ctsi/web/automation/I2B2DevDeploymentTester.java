package edu.ucla.ctsi.web.automation;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class I2B2DevDeploymentTester {

    @Test
    public void seleniumTestCase() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://i2b2.ctsi.ucla.edu/webclient/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

        WebElement usernameElement = driver.findElement(By.name("uname"));
        usernameElement.clear();
        usernameElement.sendKeys("pdessai");
        WebElement passwordElement = driver.findElement(By.name("pword"));
        passwordElement.clear();
        passwordElement.sendKeys("Pr@l@v12");

        WebElement formElement = driver.findElement(By.name("loginForm"));
        formElement.submit();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement icdCodeTab = driver.findElement(By.xpath("//div[contains(@title, 'ICD-9-CM')]"));
        icdCodeTab.click();

        WebElement diseasesAndInjuries = driver.findElement(By
                .xpath("//div[contains(@title, 'ICD9 CLASSIFICATION\\Diseases and injuries\\')]"));
        diseasesAndInjuries.click();

        WebElement congenitalAnomalies = driver
                .findElement(By
                        .xpath("//div[contains(@title, 'ICD9 CLASSIFICATION\\Diseases and injuries\\Congenital anomalies(740-759)\\')]"));
        congenitalAnomalies.click();

        WebElement from = driver
                .findElement(By
                        .xpath("//div[contains(@title, 'ICD9 CLASSIFICATION\\Diseases and injuries\\Congenital anomalies(740-759)\\Anencephalus and similar anomalies(740)\\ - SHRINE|ICD9:740')]"));

        WebElement to = driver.findElement(By.xpath(".//*[@id='QPD1']"));

        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(from).moveToElement(to).release(to).build();
        dragAndDrop.perform();

        WebElement runBox = driver.findElement(By.xpath(".//*[@id='runBoxText']"));
        runBox.click();

        WebElement yuiButton = driver.findElement(By.xpath(".//*[@id='yui-gen5-button']"));
        yuiButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement infoQueryStatus = driver.findElement(By.xpath(".//*[@id='infoQueryStatusText']"));

        System.out.println(infoQueryStatus.getText());

    }
}
