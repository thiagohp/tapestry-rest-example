package br.com.machina.tapestryrestexample.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.commons.ObjectLocator;
import org.apache.tapestry5.commons.services.CoercionTuple;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import br.com.machina.tapestryrestexample.entities.User;

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
    
    @SuppressWarnings("rawtypes")
    public static void contributeTypeCoercer(
            MappedConfiguration<CoercionTuple.Key, CoercionTuple> configuration,
            ObjectLocator objectLocator) {
        
        // Injecting UserService directly as a parameter causes a circular dependency,
        // so we work around that by injecting ObjectLocator.
        final UserService userService = objectLocator.getService(UserService.class);
        
        // Converts from String to User using Jackson Databind
        CoercionTuple.add(configuration, String.class, User.class, (s) -> userService.toObject(s));
    }
    
    @SuppressWarnings("rawtypes")
    public void contributeComponentEventResultProcessor(
            MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
        configuration.addInstance(User.class, UserComponentEventResultProcessor.class);
    }
    
}
