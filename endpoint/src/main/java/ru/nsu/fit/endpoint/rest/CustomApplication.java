package ru.nsu.fit.endpoint.rest;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class CustomApplication extends ResourceConfig {
    public CustomApplication() {
        packages("ru.nsu.fit.endpoint.rest");
        register(LoggingFilter.class);
        register(AuthenticationFilter.class);
        register(GsonMessageBodyHandler.class);
    }
}
