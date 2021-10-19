/**
 * 
 */
package br.com.machina.tapestryrestexample.jetty;

import org.apache.tapestry5.test.JettyRunner;

/**
 * Runs the webapp with an embedded Jetty instance.
 */
public class RunWithJetty {

    public static void main(String[] args) throws Exception {
        new JettyRunner("src/main/webapp", "/", 8080, 8443);
    }

}
