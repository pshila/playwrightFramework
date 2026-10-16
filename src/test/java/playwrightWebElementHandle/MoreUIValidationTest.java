package playwrightWebElementHandle;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MoreUIValidationTest
{
    Playwright playwright;
    Browser browser;
    BrowserContext newContext;
    Page page;
    Page newpage;

    @BeforeMethod(alwaysRun = true)
    public void setup(){

         playwright=Playwright.create();
         browser= playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
         newContext=browser.newContext();

        // Start tracing before creating / navigating a page. //trace.playwright.dev -use this site to see report
        newContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

         page=newContext.newPage();
         page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @Test
    public void childWindowHandling(){

       Locator blingText= page.locator(".blinkingText");
       newpage=newContext.waitForPage(()->blingText.first().click()); //parallel process will happen, while clicking first()/Last() link, control will go to child window
       newpage.waitForLoadState();

       //textContent will help to grab value from the DOM (which is present in differet-2 tag/attribute)
       String childText=newpage.locator(".red").textContent();
        //  System.out.println(childText);

        //email.split("at ")[1].split(" ")[0]  -- this also will give us email id from child window
       String username= childText.split(" ")[4];
       // System.out.println(username);

        //filling username in parant window for login
         page.getByLabel("username").fill(username);
         System.out.println(page.getByLabel("username").inputValue());
         page.waitForTimeout(3000);

    }

    @Test
    public void AriaRoleLocators(){

        Locator radiobutton=page.getByRole(AriaRole.RADIO,new Page.GetByRoleOptions().setName("User"));
        radiobutton.click();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Okay")).click();
        //verify radio button is checked or not
        Assert.assertTrue(radiobutton.isChecked());

        //deal with without lable and text dropdown
        page.getByRole(AriaRole.COMBOBOX).selectOption("Teacher");

        //deal with checkbox
        Locator checkBox=page.getByRole(AriaRole.CHECKBOX,new Page.GetByRoleOptions()
                .setName("I Agree to the terms and conditions"));
         checkBox.check();
         Assert.assertTrue(checkBox.isChecked());
         page.waitForTimeout(3000);

    }

    @AfterMethod
    public void tearDown(){

        // Stop tracing and export it into a zip archive.
        newContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));



    }


}
