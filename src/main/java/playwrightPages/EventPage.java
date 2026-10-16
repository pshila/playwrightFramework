package playwrightPages;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventPage {
    Page page;
    public EventPage(Page page) {
        this.page = page;
    }

    public Locator clickAndWaitForEventToload(){

       page.getByTestId("nav-events").click();
       Locator eventCard=page.getByTestId("event-card"); //4 or more event card will present in page
        //we can count the event for that we use {events.count()}
        assertThat(eventCard.first()).isVisible();
        return eventCard;
    }

    public Locator filterTheTargetEvent(){

        Locator eventCard=clickAndWaitForEventToload(); //calling uper method
        Locator targetEvent=eventCard.filter(new Locator.FilterOptions().setHasText("QA summit program"));
        //specific assert condition for modified wait condition
        assertThat(targetEvent).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(1000));
        return targetEvent;

    }
    public int getSeatcount(){

        Locator targetEvent = filterTheTargetEvent(); //calling uper method
        String seatCount=targetEvent.getByText("seats available").innerText();
        System.out.println(seatCount);
        int beforeBookingSeat=Integer.parseInt(seatCount.split(" ")[0]);
        System.out.println(beforeBookingSeat);
        return beforeBookingSeat;

    }
    public BookingEventPage clickOnBookButton(){
        Locator targetEvent = filterTheTargetEvent();
        targetEvent.getByTestId("book-now-btn").click();
        assertThat(page.getByText("Book Tickets")).isVisible();
        return new BookingEventPage(page);
    }

}
