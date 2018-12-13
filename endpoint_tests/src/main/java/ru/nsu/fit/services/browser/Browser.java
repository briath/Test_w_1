package ru.nsu.fit.services.browser;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.nsu.fit.shared.ImageUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Please read: https://github.com/SeleniumHQ/selenium/wiki/Grid2
 *
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class Browser implements Closeable {
    private WebDriver webDriver;

    public Browser() {
        // create web driver
        try {
            System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Browser openPage(String url) {
        webDriver.get(url);
        return this;
    }

    public Browser waitForElement(By element) {
        return waitForElement(element, 10);
    }

    public Browser waitForElement(final By element, int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeoutSec);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        return this;
    }

    public Browser click(By element) {
        webDriver.findElement(element).click();
        return this;
    }

    public Browser typeText(By element, String text) {
        webDriver.findElement(element).sendKeys(text);
        return this;
    }

    public WebElement getElement(By element) {
        return webDriver.findElement(element);
    }

    public String getValue(By element) {
        return getElement(element).getAttribute("value");
    }

    public List<WebElement> getElements(By element) {
        return webDriver.findElements(element);
    }

    public boolean isElementPresent(By element) {
        return getElements(element).size() != 0;
    }

    public byte[] makeScreenshot() {
        try {
            return ImageUtils.toByteArray(((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void close() {
        webDriver.close();
    }
}
