package playwrightPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AdminPage {
    Page page;
    private static final String EVENT_TITLE="Title";
    private static final String ADMIN_TEXTAREA="#admin-event-form textarea";
    private static final String CATEGORY_LABLE="Category";
    private static final String CITY_LABLE="City";
    private static final String VENUE_LABLE="Venue";
    private static final String DATE_AND_TIME_LABLE= "Event Date & Time";
    private static final String PRICE_LOCATOR="input[id='price-($)']";
    private static final String TOTAL_SEAT="Total Seats";
    private static final String ADD_EVENT_BUTTON="add-event-btn";
    private static final String EVENT_CREAT_MSG="Event created!";

    public AdminPage(Page page) {
        this.page = page;
    }
    public void creatEvent(String title,String textArea,String category,String city,String venue,
                           String dateAndTime, String price, String totalSeat){

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Admin")).click();
        // In admin button, we have 2 link with "manage event" text
          //-----first way by using css selector
        // page.locator("a[href='/admin/events']").first().click();
        //-------second way to handle
        page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK,new Locator.GetByRoleOptions().setName("Manage Events")).click();

        // Step-1   Going To create new event

        page.getByLabel(EVENT_TITLE).fill(title);
        //"#admin-event-form" is parant tage id and "textarea" is child tag
        page.locator(ADMIN_TEXTAREA).fill(textArea);
        page.getByLabel(CATEGORY_LABLE).selectOption(category);
        page.getByLabel(CITY_LABLE).fill(city);
        page.getByLabel(VENUE_LABLE).fill(venue);
        page.getByLabel(DATE_AND_TIME_LABLE).fill(dateAndTime);
        page.locator(PRICE_LOCATOR).fill(price);
        page.getByLabel(TOTAL_SEAT).fill(totalSeat,new Locator.FillOptions().setTimeout(5000));
        page.waitForTimeout(1000);
        // here data testId is a attribute available in button -click(new Locator.ClickOptions().setTimeout(10000))
        page.getByTestId(ADD_EVENT_BUTTON).click();

        assertThat( page.getByText(EVENT_CREAT_MSG)).isVisible();

    }
}
