package playwright.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

    public static Properties getProperties() throws IOException {
        Properties prop=new Properties();
        FileInputStream fis=new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);
        return prop;
    }

}
