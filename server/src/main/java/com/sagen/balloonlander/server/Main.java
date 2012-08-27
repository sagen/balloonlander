
package com.sagen.balloonlander.server;


import com.sun.grizzly.http.SelectorThread;
import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.http.servlet.ServletAdapter;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import javax.jws.WebService;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class Main {

    private static int getPort(int defaultPort) {
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    protected static GrizzlyWebServer startServer() throws IOException, ServletException {
        ServletAdapter adapter = new ServletAdapter();
        adapter.addInitParameter("com.sun.jersey.config.property.packages", "com.sagen.balloonlander.server");
        adapter.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        adapter.setProperty("load-on-startup", "1");
        adapter.setContextPath("/");
        ServletContainer servletContainer = new ServletContainer();
        adapter.setServletInstance(servletContainer);
        GrizzlyWebServer webServer = new GrizzlyWebServer(9998);
        webServer.addGrizzlyAdapter(adapter, new String[]{"/"});
        webServer.start();
        return webServer;
    }
    
    public static void main(String[] args) throws IOException, ServletException {
         GrizzlyWebServer httpServer = startServer();
        System.in.read();
        httpServer.stop();
    }
}
