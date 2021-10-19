package br.com.machina.tapestryrestexample.pages.rest;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.StaticActivationContextValue;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;
import org.slf4j.LoggerFactory;

/**
 * Some mostly useless endpoints just so we have another RESt endpoint page.
 */
public class InfoRest {

    @Inject
    private SymbolSource symbolSource;
    
    @Inject
    private Messages messages;

    // /version
    @OnEvent(EventConstants.HTTP_GET)
    public StreamResponse getApiVersion(@StaticActivationContextValue("version") String path) {
        return createTextResponse(getApiVersion());
    }

    // /title
    @OnEvent(EventConstants.HTTP_GET)
    public StreamResponse getApiTitle(@StaticActivationContextValue("title") String path) {
        return createTextResponse(getApiTitle());
    }

    // /description
    @OnEvent(EventConstants.HTTP_GET)
    public StreamResponse getApiDescription(@StaticActivationContextValue("description") String path) {
        return createTextResponse(getApiDescription());
    }
    
    // / (root page URL)
    @OnEvent(EventConstants.HTTP_GET)
    public StreamResponse getFullInfo() {
        return new TextStreamResponse("application/json", 
                new JSONObject("version", getApiVersion(), 
                        "title", getApiTitle(), 
                        "description", getApiDescription()).toCompactString());
    }
    
    private String getApiVersion() {
        return getValue(SymbolConstants.OPENAPI_APPLICATION_VERSION);
    }

    private String getApiTitle() {
        return getValue(SymbolConstants.OPENAPI_TITLE);
    }

    private String getApiDescription() {
        return getValue(SymbolConstants.OPENAPI_DESCRIPTION);
    }

    private String getValue(String key) {
        // OpenAPI message keys don't have the "tapestry." prefix as the
        // corresponding configuration symbols do
        String value = messages.get(key.replace("tapestry.", ""));
        
        // Checking whether the message was actually found
        if (value.startsWith("[[") && value.endsWith("[[")) {
            try {
                value = symbolSource.valueForSymbol(value);
            } catch (Exception e) {
                LoggerFactory.getLogger(InfoRest.class).warn(
                        String.format("Symbol %s not defined", key));
                value = "(undefined)";
            }
        }
        return value;
    }

    private TextStreamResponse createTextResponse(String value) {
        return new TextStreamResponse("text/plain", value);
    }
    
}
