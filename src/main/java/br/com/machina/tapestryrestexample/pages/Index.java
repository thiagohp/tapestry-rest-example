package br.com.machina.tapestryrestexample.pages;

import java.io.IOException;
import java.util.List;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import br.com.machina.tapestryrestexample.pages.rest.UserRest;
import br.com.machina.tapestryrestexample.rest.entities.User;
import br.com.machina.tapestryrestexample.services.AppModule;
import br.com.machina.tapestryrestexample.services.UserService;

/**
 * Remember this page, both template and class, aren't really part of the
 * example, being just an easy way to interact with the REST endpoints
 * without needing any other external tool. 
 * The real examples are the {@link UserRest} and {@link AppModule} classes.
 */
public class Index {
    
    @Property
    private User newUser;
    
    @Property
    private User user;
    
    @Inject
    private UserService userService;
    
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
    
    @Inject
    private AlertManager alertManager;
    
    void onActivate() {
        if (newUser == null) {
            newUser = new User();
        }
    }
    
    // This simulates some code which isn't part of this application making a POST
    // to the user endpoint based on the form in the page. This isn't an example to be
    // followed, just a way to show, without needing some external tool to make a POST 
    // request with a body containing a JSON object how to write REST endpoints .
    void onSuccess() {
        
        final String json = userService.toJsonString(newUser);
        final String endpointUrl = pageRenderLinkSource.createPageRenderLink(UserRest.class).toAbsoluteURI();
        
        try {
            final HttpResponse response = Request
                .put(endpointUrl)
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();
            alertManager.info("Request status code: " + response.getCode());
        } catch (IOException e) {
            alertManager.error(e.getMessage());
        }
        
    }

    public List<User> getUsers() {
        return userService.getAll();
    }
    
}
