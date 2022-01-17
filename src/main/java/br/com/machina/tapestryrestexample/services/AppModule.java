package br.com.machina.tapestryrestexample.services;

import java.text.SimpleDateFormat;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.commons.OrderedConfiguration;
import org.apache.tapestry5.http.services.HttpRequestBodyConverter;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.jacksondatabind.services.ObjectMapperSource;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.machina.tapestryrestexample.rest.entities.User;

public class AppModule {
    
    public static void bind(ServiceBinder binder) {
        binder.bind(UserService.class, UserServiceImpl.class);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.PUBLISH_OPENAPI_DEFINITON, true);
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "I need some REST!");
        configuration.add(SymbolConstants.OPENAPI_TITLE, "Tapestry REST support example");
        configuration.add(SymbolConstants.OPENAPI_APPLICATION_VERSION, "0.0.1");
    }
    
    /**
     * Example of customizing the Jackson Databinding's {@link ObjectMapper}, specifically the
     * date format.
     */
    public static void contributeObjectMapperSource(OrderedConfiguration<ObjectMapperSource> configuration)
    {
        configuration.add("Custom", new CustomObjectMapperSource());
    }
    
    private static final class CustomObjectMapperSource implements ObjectMapperSource {

        final private ObjectMapper mapper;
        
        public CustomObjectMapperSource() {
            mapper = new ObjectMapper(/* ... */);
            mapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"));
            // Any other customizations go here.
        }
        
        @Override
        public ObjectMapper get(Class<?> clasz) {
            // You could check the class to provide class-specific ObjectMapper 
            // instances here, but this isn't the case for this example
            return mapper;
        }
        
    }
    
}
