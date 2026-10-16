package playwrightTestBase;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.BeforeMethod;
import playwright.Utils.ReadProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;
    String baseUrl;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws IOException {
        //reading properties file for input Data
        playwright=Playwright.create();
        String browserName=System.getProperty("browser")!=null?System.getProperty("browser"):ReadProperties.getProperties().getProperty("browser");
     //   String browserName= ReadProperties.getProperties().getProperty("browser");
        String envName=System.getProperty("env")!=null?System.getProperty("env"):ReadProperties.getProperties().getProperty("env");

       //mvn test -Psmoke -Dbrowser=chrome -Denv=qa

        if(browserName.equalsIgnoreCase("chrome"))
        {
           // browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            browser=playwright.chromium().launch();
        }
        else if (browserName.equalsIgnoreCase("firfox")) {
          browser=  playwright.firefox().launch();
        }
        else if (browserName.equalsIgnoreCase("Safari")) {
            browser= playwright.webkit().launch();

        }

        context=browser.newContext();
        page=context.newPage();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

         baseUrl=ReadProperties.getProperties().getProperty(envName+".Base_Url");
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);//degault time out for Assertion 5 sencond and settting 7 seconds
        page.setDefaultTimeout(7000); //default time out for Action 10 seconds and i am stting 7 seconds

    }

}
