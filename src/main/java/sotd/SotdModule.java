package sotd;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SotdModule extends AbstractModule {

    @Override
    protected void configure() {

        final String appConfigPath = "app.properties";
        try (FileInputStream stream = new FileInputStream(appConfigPath)) {

            final Properties appProps = new Properties();
            appProps.load(stream);
            Names.bindProperties(binder(), appProps);
        } catch (IOException exception) {
            System.err.println("Failed to load properties file: " + exception.getMessage());
            System.exit(1);
        }
    }
}
