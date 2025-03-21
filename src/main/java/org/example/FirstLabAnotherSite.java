package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class FirstLabAnotherSite {
    private WebDriver firefoxDriver;
    private static final String baseUrl = "https://starylev.com.ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-fullscreen");
        firefoxDriver = new FirefoxDriver(options);
    }

    @BeforeMethod
    public void preconditions() {
        firefoxDriver.get(baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        firefoxDriver.quit();
    }

    @Test
    public void testHeaderExists() {
        WebElement header = firefoxDriver.findElement(By.cssSelector("header"));
        Assert.assertTrue(header.isDisplayed(), "Header is not displayed");
    }


    @Test
    public void testSearchField() {
        WebDriverWait wait = new WebDriverWait(firefoxDriver, Duration.ofSeconds(20));

            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/section/header/div[1]/div/div[2]/div[1]/div[1]/input")));
            String inputValue = "I need info";
            searchField.sendKeys(inputValue);
            Assert.assertEquals(searchField.getAttribute("value"), inputValue, "wrong text");
            searchField.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(baseUrl)));
            Assert.assertNotEquals(firefoxDriver.getCurrentUrl(), baseUrl, "search error");
    }

    @Test
    public void testConditionCheck() {
        WebElement banner = firefoxDriver.findElement(By.xpath("//div[contains(@class, 'banner')]"));
        Assert.assertTrue(banner.isDisplayed(), "Banner is not displayed");
    }
    @Test
    public void testClick() {
        WebElement account = firefoxDriver.findElement(By.xpath("/html/body/div/section/header/div[1]/nav/div/div[2]/ul/li[1]/a"));
        Assert.assertNotNull(account);
        account.click();

        Assert.assertNotEquals(firefoxDriver.getCurrentUrl(), baseUrl, "URL did not change after click");
    }
}