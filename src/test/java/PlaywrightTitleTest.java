import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.After;
import org.junit.Before;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PlaywrightTitleTest {
    private String browserType;

    public PlaywrightTitleTest(String browserType) {
        this.browserType = browserType;
    }

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private final String URL = "https://playwright.dev/";
    private final String EXPECTED_TITLE = "Fast and reliable end-to-end testing for modern web apps | Playwright";

    @Parameterized.Parameters(name = "{index}: Browser={0}")
    public static Object[] getData() {
        return new Object[]{"chromium", "firefox"};
    }

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = createBrowser(browserType);
        page = browser.newPage();
    }

    @Test
    public void playwrightTitleTest() {
        page.navigate(URL);
        assertThat(page).hasTitle(EXPECTED_TITLE);
    }

    @After
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    private Browser createBrowser(String browserType) {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        if ("chromium".equals(browserType)) {
            return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        } else if ("firefox".equals(browserType)) {
            return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserType);
        }
    }
}
