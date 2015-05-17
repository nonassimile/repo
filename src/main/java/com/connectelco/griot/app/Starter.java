package com.connectelco.griot.app;

import java.io.InputStream;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.connectelco.griot.app.config.AppConfig;

public class Starter {
	public static void main( final String[] args ) throws Exception {
		Server server = new Server( 8080 );
		        
 		// Register and map the dispatcher servlet
 		final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
 		final ServletContextHandler context = new ServletContextHandler(); 		
 		context.setContextPath( "/" );
 		context.addServlet( servletHolder, "/rest/*" ); 	
 		context.addEventListener( new ContextLoaderListener() );
 		
 		context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
 		context.setInitParameter( "contextConfigLocation", AppConfig.class.getName() );
 		
 		//Adding static page handler to context
 		// Create the ResourceHandler. It is the object that will actually handle the request for a given file. It is
        // a Jetty Handler object so it is suitable for chaining with other handlers as you will see in other examples.
        ResourceHandler resource_handler = new ResourceHandler();
        // Configure the ResourceHandler. Setting the resource base indicates where the files should be served out of.
        // In this example it is the current directory but it can be configured to anything that the jvm has access to.
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        InputStream in = new Object(){}.getClass().getEnclosingClass().getClassLoader().getResourceAsStream("index.html");
        /*BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String strLine = "";
        while((strLine = reader.readLine())!= null)
        {
         System.out.println("line is: " + strLine);
        }*/
 	    
 	   String webDir = Starter.class.getProtectionDomain()
 	            .getCodeSource().getLocation().toExternalForm();
        resource_handler.setResourceBase("jar:"+webDir+":!/");
        System.out.println("serving: "+resource_handler.getBaseResource());
        ContextHandler context0 = new ContextHandler();
        context0.setContextPath("/");
        context0.setHandler(resource_handler);
        
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { context, context0 });
        
 		// Add Spring Security Filter by the name
 		context.addFilter(
 		    new FilterHolder( new DelegatingFilterProxy( "springSecurityFilterChain" ) ), "/*", 
 		    EnumSet.allOf( DispatcherType.class )
        );
 		HandlerList handlers = new HandlerList();
 	    handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
 	  
 	   WebAppContext webAppContext = new WebAppContext();
 	  webAppContext.setServer(server);
 	  webAppContext.setContextPath("/");
 	  webAppContext.setResourceBase(new ClassPathResource("webapp").getURI().toString());
 	  
        server.setHandler( webAppContext );
        server.start();
        server.join();	
	}
}

