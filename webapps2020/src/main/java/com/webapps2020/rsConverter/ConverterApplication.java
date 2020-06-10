package com.webapps2020.rsConverter;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class ConverterApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        //register resource
        classes.add(Conversion.class);
        return classes;
    }
}
