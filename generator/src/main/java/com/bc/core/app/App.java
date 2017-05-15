/*
 * App.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */

package com.bc.core.app;

import com.bc.core.generator.AbstractGenerator;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tomáš Polešovský
 */
public class App {

	public static final String CONFIG_NAME = "config.xml";

	/**
	 * @param args the command line arguments 
	 * compilation: mvn clean compile assembly:single
	 */
	public static void main(String[] args) {
		try {
			URL path = new URL(App.class.getProtectionDomain().getCodeSource().getLocation(), "config.xml");
			Config config = new Config(new File(path.toURI()));
			config.parse();
			
			List<String> tests = config.getTests();
			AbstractGenerator generator;

			System.out.println("[INFO] Test generator started");

			GlueHelper helper = new GlueHelper(config);
			helper.backUpGlues();

			// run all the tests
			for (String set : tests) {
				generator = (AbstractGenerator) Class.forName(set).getConstructor(Config.class).newInstance(config);
				generator.generate();
				System.out.println("[INFO] Test set " + set + " was generated");
			}

			helper.restoreGlues();
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
