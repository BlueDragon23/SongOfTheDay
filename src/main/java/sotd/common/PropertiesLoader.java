package sotd.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private PropertiesLoader() {
        // DNI
    }

    public static Properties loadProperties(final String name) throws IOException {
        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(name)) {

            final Properties appProps = new Properties();
            appProps.load(inputStream);
            return appProps;
        }
    }
}
