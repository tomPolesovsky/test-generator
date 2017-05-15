/*
 * Loader.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import com.bc.core.app.Config;
import com.bc.core.scanner.ClassFile;
import com.tobedevoured.naether.DependencyException;
import com.tobedevoured.naether.URLException;
import com.tobedevoured.naether.api.Naether;
import com.tobedevoured.naether.impl.NaetherImpl;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * @author Tomáš Polešovský
 */
public class Loader {

	private Config config;

	private final ClassLoader depLoader;

	public Loader(Config config) {
		this.config = config;
		depLoader = this.initClassLoader();
	}

	private URL[] findDependencies() throws MalformedURLException {
		List<String> deps = new ArrayList();

		try {
			// pom.xml dependencies
			Reader reader = new FileReader(config.getPom());
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			Model model = xpp3Reader.read(reader);

			Naether naether = new NaetherImpl();

			for (Dependency dep : model.getDependencies()) {
				naether.addDependency(dep);
			}

			naether.resolveDependencies();

			for (Map.Entry<String, String> entry : naether.getDependenciesPath().entrySet()) {
				deps.add(entry.getValue());
			}
		} catch (IOException | XmlPullParserException | URLException | DependencyException ex) {
			Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
		}

		URL[] list = new URL[deps.size() + config.getDepends().size()];

		for (int i = 0; i < deps.size(); i++) {
			list[i] = (new File(deps.get(i))).toURI().toURL();
		}

		// config dependencies
		List<String> confDepends = config.getDepends();

		for (int i = 0; i < confDepends.size(); i++) {
			list[i] = (new File(confDepends.get(i))).toURI().toURL();
		}

		return list;
	}

	private ClassLoader initClassLoader() {
		ClassLoader classLoader = null;

		try {
			URL[] dependencies = this.findDependencies();

			classLoader = new URLClassLoader(dependencies);
		} catch (MalformedURLException ex) {
			Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
		}

		return classLoader;
	}

	public Class loadClass(ClassFile classFile) {
		Class cls = null;

		try {
			File path = new File(config.getTargetPath());

			URLClassLoader classLoader = new URLClassLoader(new URL[]{path.toURI().toURL()}, this.depLoader);
			cls = classLoader.loadClass(classFile.getPack() + "." + classFile.getName());
		} catch (MalformedURLException | ClassNotFoundException ex) {
			Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
		}

		return cls;
	}

}
