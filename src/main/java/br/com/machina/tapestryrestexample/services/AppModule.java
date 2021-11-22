package br.com.machina.tapestryrestexample.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.commons.OrderedConfiguration;
import org.apache.tapestry5.http.services.HttpRequestBodyConverter;
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
    
    public static void contributeHttpRequestBodyConverter(
            OrderedConfiguration<HttpRequestBodyConverter> configuration) {
        
        configuration.addInstance("User", UserHttpRequestBodyConverter.class);
    }
    
    @SuppressWarnings("rawtypes")
    public void contributeComponentEventResultProcessor(
            MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
        configuration.addInstance(User.class, UserComponentEventResultProcessor.class);
    }
    
}
