package br.com.machina.tapestryrestexample.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.http.services.HttpRequestBodyConverter;

import br.com.machina.tapestryrestexample.entities.User;

/**
 * Converts the body of HTTP requests to {@link User} instances. It delegates this task
 * to {@link UserService#toObject(String)}, which uses Jackson Databind.
 */
public class UserHttpRequestBodyConverter implements HttpRequestBodyConverter {
    
    private final UserService userService;

    public UserHttpRequestBodyConverter(UserService userService) {
        super();
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(HttpServletRequest request, Class<T> type) {
        T value = null;
        if (User.class.equals(type)) {
            try {
                value = (T) userService.toObject(IOUtils.toString(request.getReader()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return value;
    }

}
