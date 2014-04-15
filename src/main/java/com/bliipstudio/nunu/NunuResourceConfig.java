package com.bliipstudio.nunu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.bliisptudio.nunu.service.SuperCrazyService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;

public class NunuResourceConfig extends ResourceConfig {

	/**
	 * It registers app's packages and necessary features
	 */
	public NunuResourceConfig() {
		// We add the ability to use multipart form data to Jersey
		register(MultiPartFeature.class);
		// We tell Jersey wich packages contains our annotated classes
		packages("com.bliipstudio.nunu.api");
	}

	/**
	 * 
	 * It creates the app's resource config. Also bridges guice and hk2
	 * 
	 * @param serviceLocator
	 *            injected hk2 serviceLocator
	 */
	@Inject
	public NunuResourceConfig(ServiceLocator serviceLocator) {
		this();
		System.out.println("Registering injectables...");

		// We use GuiceBridge singleton to create a initialize the bridge
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

		// We get the bridge created by the init
		GuiceIntoHK2Bridge guiceBridge = serviceLocator
				.getService(GuiceIntoHK2Bridge.class);

		// We inject our injector LOL
		guiceBridge.bridgeGuiceInjector(Guice
				.createInjector(new AbstractModule() {
					@Override
					protected void configure() {
						bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
						bind(SuperCrazyService.class);
					}
				}));
	}
}

