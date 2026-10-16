package playwrightPages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    Page page;
    String baseUrl;
    private static final String emailLable="Email";
    private static final String PasswordLabel="Password";

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public HomePage loginFunctionality(){

        page.navigate(baseUrl);
        String title=page.title();

        assertThat(page).hasTitle("EventHub — Discover & Book Events");

        //login functionality
        page.getByLabel(emailLable).fill("pshila0027@gmail.com");
        page.getByLabel(PasswordLabel).fill("Drishi@7415");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Sign In")).click();

        //ceating instance for HomePage(next one )
       HomePage homePage=new HomePage(page);
       return homePage;


    }
}
