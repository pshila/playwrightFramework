package playwrightTestBase;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import playwright.Utils.DataProviderUtils;
import playwright.Utils.ReadProperties;
import playwrightPages.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class FrameWorkBuildDataDrivenTest extends TestBase{

   @DataProvider(name = "eventBookingData")
   public Object[][] getDataFromJson() throws IOException {

       return DataProviderUtils.getJsonDataToMap(ReadProperties.getProperties().getProperty("jsonDataFilePath"));
   }

    @Test(groups = {"smoke"},dataProvider="eventBookingData")
    public void eventMangement(HashMap<String,String> data){

        LoginPage loginPage=new LoginPage(page,baseUrl);
        HomePage homePage=loginPage.loginFunctionality();
        homePage.validateHomepage();
        AdminPage adminPage=new AdminPage(page);
        adminPage.creatEvent(data.get("title"),
                data.get("textArea"),
                data.get("category"),
                data.get("city"),
                data.get("venue"),
                data.get("dateAndTime"),
                data.get("price"),
                data.get("totalSeat"));

        EventPage eventPage=new EventPage(page);
        int beforeBookingSeat=eventPage.getSeatcount();
        BookingEventPage bookingEventPage=eventPage.clickOnBookButton();

         //Step-3 Book the new event

        for(int i=1;i<=2;i++){
        page.getByText("+").click();
        }
        page.waitForTimeout(1000);

        page.getByPlaceholder("Your full name").fill("Shila Patel");
        page.getByPlaceholder("you@email.com").fill("shila@gmail.com");
        page.getByLabel("Phone Number").fill("7415721774");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Confirm Booking")).click();
    page.waitForTimeout(1000);

    assertThat(page.getByText("Your tickets are reserved.")).isVisible();
    String bookingRefNo=page.locator(".booking-ref").innerText();
    System.out.println(bookingRefNo);
    page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("View My Bookings")).click();
    //navigate on my booking page and validate/assert that booking Referece no is present in this page or not
        assertThat(page.getByText("View and manage all your ticket bookings")).isVisible();
        assertThat(page.getByText(bookingRefNo)).isVisible();
        page.waitForTimeout(1000);

     //again go on event page and check

        page.getByTestId("nav-events").click();
        Locator targetEvent2=eventPage.clickAndWaitForEventToload().filter(new Locator.FilterOptions().setHasText("QA summit program"));
        String AfterBookingseatCount=eventPage.filterTheTargetEvent().getByText("seats available").innerText();
       int afterBookingSeat= Integer.parseInt(AfterBookingseatCount.split(" ")[0]);
       System.out.println(afterBookingSeat);
     //  Assert.assertTrue(beforeBookingSeat>afterBookingSeat);

    }

    @AfterMethod
    public void tearDown(){

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("eventhub-trace.zip")));
    }

}
