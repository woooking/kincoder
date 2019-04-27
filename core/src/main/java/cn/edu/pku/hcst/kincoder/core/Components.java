package cn.edu.pku.hcst.kincoder.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Components {
    private final Injector injector = Guice.createInjector(new CoreModule());

    public <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }
}
