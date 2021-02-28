package spotify.oauth;

import common.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class Constants {

    public static final String CLIENT_ID = "5442acc3a29d44eca84a0d6b68dd9cf7";

    private static String clientSecret;

    static {
        try {
            final Properties properties = PropertiesLoader.loadProperties("secret.properties");
            clientSecret = properties.getProperty("client-secret");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exiting due to failure loading properties file");
            System.exit(1);
        }
    }

    public static String getClientSecret() {
        return clientSecret;
    }
}
