package br.com.machina.tapestryrestexample.services;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.tapestry5.http.ContentType;
import org.apache.tapestry5.http.TapestryHttpSymbolConstants;
import org.apache.tapestry5.http.services.Response;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import br.com.machina.tapestryrestexample.entities.User;

/**
 * Handles {@link User} instances when they're returned by event handler methods.
 * This could be easily be parameterized so it handles different Jackson-mapped classes
 * by using Jackson directly instead of going through {@link UserService}.
 * Heavily inspired by {@link JSONCollectionEventResultProcessor} from Tapestry itself.
 */
final public class UserComponentEventResultProcessor 
        implements ComponentEventResultProcessor<User> {

    private final Response response;

    private final ContentType contentType;
    
    private final UserService userService;

    public UserComponentEventResultProcessor(Response response,
            @Symbol(TapestryHttpSymbolConstants.CHARSET) String outputEncoding,
            UserService userService)    {
        this.response = response;
        this.userService = userService;
        contentType = new ContentType(InternalConstants.JSON_MIME_TYPE).withCharset(outputEncoding);
    }

    public void processResultValue(User user) throws IOException
    {
        PrintWriter pw = response.getPrintWriter(contentType.toString());
        pw.write(userService.toJsonString(user));
        pw.close();
    }        
}