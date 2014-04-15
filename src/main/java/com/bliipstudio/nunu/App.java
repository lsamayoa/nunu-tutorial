package com.bliipstudio.nunu;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.Configuration.ClassList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {
	public static void main(String[] args) {
		//Create Jetty Server
		Server server = new Server(9080);
		
		// We add env configuration and plus configuration to enable jndi in
		// embedded jetty :)
		ClassList classlist = ClassList.setServerDefault(server);
		classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
				"org.eclipse.jetty.plus.webapp.EnvConfiguration",
				"org.eclipse.jetty.plus.webapp.PlusConfiguration");

		// Create Web App context
		WebAppContext appContext = new WebAppContext();
		appContext.setServer(server);
		appContext.setContextPath("/");
		appContext.setResourceBase("src/main/webapp");
		appContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		server.setHandler(appContext);

		// Create a servlet container that will contain the jersey app in the
		// context
		ServletHolder holder = appContext.addServlet(ServletContainer.class,
				"/api/*");
		// Configure jax-rs application
		holder.setInitParameter("javax.ws.rs.Application",
				"com.bliipstudio.nunu.NunuResourceConfig");
		try {
			System.out
					.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			// Start Jetty server
			server.start();
			while (System.in.available() == 0) {
				Thread.sleep(5000);
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}

