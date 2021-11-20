package sotd;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SotdModule extends AbstractModule {

    @Override
    protected void configure() {

        final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        final String appConfigPath = rootPath + "app.properties";
        try (FileInputStream stream = new FileInputStream(appConfigPath)) {

            final Properties appProps = new Properties();
            appProps.load(stream);
            Names.bindProperties(binder(), appProps);
        } catch (IOException exception) {
            System.out.println("Failed to load properties file: " + exception.getMessage());
        }
    }
}
