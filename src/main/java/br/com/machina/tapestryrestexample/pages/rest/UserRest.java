package br.com.machina.tapestryrestexample.pages.rest;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.RequestBody;
import org.apache.tapestry5.annotations.RestInfo;
import org.apache.tapestry5.annotations.StaticActivationContextValue;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.HttpStatus;

import br.com.machina.tapestryrestexample.rest.entities.User;
import br.com.machina.tapestryrestexample.services.UserService;

/**
 * Page implementing {@link User} REST endpoints.
 * Notice the the getExample and onHttpGet methods have the same number of
 * parameters, so Tapestry calls them in alphabetical order. getExample needs
 * to be called first because otherwise onHttpGet would always get the request
 * first and getExample wouldn't be called. 
 */
public class UserRest {

    @Inject private UserService userService;
    
    @RestInfo(produces = "application/json")
    User onHttpGet(Long id) {
        return userService.get(id).get();
    }
    
    @OnEvent(EventConstants.HTTP_PUT) 
    Object save(@RequestBody User user) {
        userService.save(user);
        return HttpStatus.created();
    }
    
    @OnEvent(EventConstants.HTTP_GET)
    User getExample(@StaticActivationContextValue("example") String ignored) {
        User user = new User();
        user.setId(42l);
        user.setEmail("fulano@machina.com.br");
        user.setName("Fulano");
        user.setUsername("fulano");
        return user;
    }
    
}
