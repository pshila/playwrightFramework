package playwrightWebElementHandle;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class UiValidationcontinue
{

    Playwright playwright;
    Browser browser;
    Page page;


    @BeforeMethod
    public void setup(){

        playwright=Playwright.create();
        browser= playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page=browser.newPage();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");
    }

    @Test
    public void popUpValidation(){
        //dealing hide and show textBox
        assertThat(page.getByPlaceholder("Hide/Show Example")).isVisible();
        page.locator("#hide-textbox").click();
        assertThat(page.getByPlaceholder("Hide/Show Example")).isHidden();

        //dealing Alert popUp
        page.onDialog(dialog -> dialog.accept()); // onDialog is listener used to handle popupAlert

        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Alert")).click();
        page.waitForTimeout(3000);

        //mouse hover operation
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Mouse Hover")).hover();
        page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Top")).click();


    }

    @Test
    public void handleFrame()  {

        FrameLocator framePage=page.frameLocator("#courses-iframe");
        framePage.getByRole(AriaRole.LINK,new FrameLocator.GetByRoleOptions().setName("Learning paths")).click();
        String frameText=framePage.locator(".inner-box h1").textContent();
        System.out.println(frameText);
    }

    @Test
    public void handleWebTableAndValidateData(){
        Locator courseTable=page.locator("#product[name='courses']");
        String textToFind="Advanced Selenium Framework Pageobject, TestNG, Maven, Jenkins,C";
        int matchingColumn=-1;
        int matchingDataRow=-1;
        Locator tableRows=courseTable.locator("tr");
        for(int row=1; row<tableRows.count() && matchingColumn==-1; row++){
            Locator cells=tableRows.nth(row).locator("td");
            for(int column=0; column<cells.count(); column++){
                if(cells.nth(column).innerText().trim().equals(textToFind)){
                    matchingColumn=column;
                    matchingDataRow=row;
                    break;
                }
            }
        }

        Assert.assertTrue(matchingColumn!=-1, "Text was not found in the web table: " + textToFind);
        System.out.println("Text is present in data row number: " + matchingDataRow
                + " (table row including header: " + (matchingDataRow + 1)
                + "), column number: " + (matchingColumn + 1));
    }

    @Test
    public void takeScreenShots(){
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pageScreenshot.png")));
        Locator hideEditBox= page.getByPlaceholder("Hide/Show Example");
        hideEditBox.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("editBox.png")));
    }



}
