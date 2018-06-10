package mdhtr.webapplication.endtoend;

import io.github.bonigarcia.SeleniumExtension;
import mdhtr.webapplication.JettyServer;
import mdhtr.webapplication.persistence.H2InMemoryDb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SeleniumExtension.class)
class EndToEndTest {
    private static final int TEST_PORT = 9001;
    private static final String PAGE_URL = "http://localhost:" + TEST_PORT + "/";
    private static final String TEST_DB_URL = "jdbc:h2:mem:endtoend;DB_CLOSE_DELAY=-1";
    private static final String TEST_DB_USER = "";
    private static final String TEST_DB_PASSWORD = "";
    private static final int TIMEOUT_IN_SECONDS = 20;

    private JettyServer server;

    @BeforeEach
    void setUp() {
        H2InMemoryDb.initInstance(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASSWORD);
        server = new JettyServer(TEST_PORT);
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Exception while starting test server", e);
        }
    }

    @AfterEach
    void tearDown() {
        H2InMemoryDb.destroyInstance();
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Exception while stopping test server", e);
        }
    }

    @Test
    void afterMessageIsSubmitted_itIsListedOnPage(ChromeDriver driver) {
        String testMessage = "gui test message";

        openPage(driver);
        submitMessage(driver, testMessage);
        refreshPage(driver);
        List<WebElement> messages = getMessages(driver);

        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getText(), is(testMessage));
    }

    @Test
    void afterMultipleMessagesAreSubmitted_allAreListedOnPage(ChromeDriver driver) {
        String testMessage = "gui test message";

        openPage(driver);
        submitMessage(driver, testMessage);
        submitMessage(driver, testMessage);
        refreshPage(driver);
        List<WebElement> messages = getMessages(driver);

        assertThat(messages.size(), is(2));
        assertThat(messages.get(0).getText(), is(testMessage));
        assertThat(messages.get(1).getText(), is(testMessage));
    }

    private void openPage(ChromeDriver driver) {
        driver.get(PAGE_URL);
    }

    private void submitMessage(ChromeDriver driver, String message) {
        WebElement messageInput = driver.findElement(By.id("message"));
        messageInput.sendKeys(message);
        messageInput.submit();
        messageInput.clear();
    }

    private void refreshPage(ChromeDriver driver) {
        driver.navigate().refresh();
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#messages div"), 0));
    }

    private List<WebElement> getMessages(ChromeDriver driver) {
        return driver.findElement(By.id("messages")).findElements(By.tagName("div"));
    }
}
