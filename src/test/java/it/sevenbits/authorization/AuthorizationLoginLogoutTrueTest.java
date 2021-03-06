package it.sevenbits.authorization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


import javax.validation.constraints.Null;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by booktina on 07.08.14.
 */
public class AuthorizationLoginLogoutTrueTest {
    private WebDriver driver;
    private String baseUrl;
    private String email;
    private String password;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://naturalexchange.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Authorization authorization = new Authorization();
        email = authorization.getEmailTrue();
        password = authorization.getPasswordTrue();

    }

    @Test
    public void testAuthorization() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Вход")).click();
        driver.findElement(By.id("entry-email")).clear();
        driver.findElement(By.id("entry-email")).sendKeys(email);
        driver.findElement(By.id("entry-pass")).clear();
        driver.findElement(By.id("entry-pass")).sendKeys(password);
        driver.findElement(By.id("entry")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/ul[1]/li[2]/a")).click();
        driver.findElement(By.linkText("Выход")).click();

    }

    @After
    public void tearDown() throws Exception {
       driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
