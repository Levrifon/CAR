package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import server.Serveur;
import car.tp2.Config;
import car.tp2.FTPResource;
public class TestInitServer {
	Serveur serv;
	FTPResource ressource;
	@Before
	public void init() {
		serv = new Serveur(new File("toto"), 4444);
		ressource = new FTPResource();
		Server server = new Server( 8080 );
        
 		final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
 		final ServletContextHandler context = new ServletContextHandler(); 		
 		context.setContextPath( "/" );
 		context.addServlet( servletHolder, "/rest/*" ); 	
 		context.addEventListener( new ContextLoaderListener() );
 		
 		context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
 		context.setInitParameter( "contextConfigLocation", Config.class.getName() );
 		 		
        server.setHandler( context );
        try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testInitialization() {
		assertTrue(serv.isOnline());
	}
	@Test
	public void testConnect() {
		String rightUser = "titi",rightPassword = "password";
		String wrongUser = "tonton",wrongPassword = "test";
		ressource.connect(wrongUser, wrongPassword);
		assertFalse(ressource.client.isConnected());
		ressource.connect(rightUser,wrongPassword);
		assertFalse(ressource.client.isConnected());
		ressource.connect(wrongUser,rightPassword);
		assertFalse(ressource.client.isConnected());
		ressource.connect(rightUser,rightPassword);
		assertTrue(ressource.client.isConnected());
	}
}
