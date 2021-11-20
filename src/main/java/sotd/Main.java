package sotd;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new SotdModule());
        Executor executor = injector.getInstance(Executor.class);
        executor.execute();

    }
}
