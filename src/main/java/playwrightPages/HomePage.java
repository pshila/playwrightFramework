package playwrightPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage {
   Page page;
    public HomePage(Page page) {
        this.page=page;
    }

    public void validateHomepage(){

        Locator EventPage = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"));
        assertThat(EventPage).isVisible();

    }
}
