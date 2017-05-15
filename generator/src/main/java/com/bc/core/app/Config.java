/*
 * Config.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.Getter;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Tomáš Polešovský
 */
public class Config {

	private File config;

	@Getter
	private String inputPath = null;

	@Getter
	private String outputPath = null;

	@Getter
	private String targetPath = null;

	@Getter
	private String packIn = null;

	@Getter
	private String packOut = null;

	@Getter
	private String pom = null;

	@Getter
	private List<String> tests = null;

	@Getter
	private List<String> depends = null;

	public Config(File config) throws FileNotFoundException {
		if (!config.exists() || config.isDirectory()) {
			throw new FileNotFoundException("Config file " + config.getAbsolutePath() + " was not found.");
		}

		this.config = config;
		this.tests = new ArrayList();
		this.depends = new ArrayList();
	}

	private void parsePath(Document doc) {
		NodeList paths = doc.getElementsByTagName("path");

		for (int i = 0; i < paths.getLength(); i++) {
			Element eElement = (Element) paths.item(i);

			this.inputPath = eElement.getElementsByTagName("input").item(0).getTextContent();
			this.outputPath = eElement.getElementsByTagName("output").item(0).getTextContent();
			this.targetPath = eElement.getElementsByTagName("target").item(0).getTextContent();
			this.pom = eElement.getElementsByTagName("pom").item(0).getTextContent();
		}
	}

	private void parsePackage(Document doc) {
		NodeList packages = doc.getElementsByTagName("package");

		for (int i = 0; i < packages.getLength(); i++) {
			Element eElement = (Element) packages.item(i);

			this.packIn = eElement.getElementsByTagName("input").item(0).getTextContent();
			this.packOut = eElement.getElementsByTagName("output").item(0).getTextContent();
		}
	}

	private void parseTests(Document doc) {
		NodeList testsList = doc.getElementsByTagName("tests");

		for (int i = 0; i < testsList.getLength(); i++) {
			Element eElement = (Element) testsList.item(i);
			NodeList sets = eElement.getElementsByTagName("set");

			for (int j = 0; j < sets.getLength(); j++) {
				this.tests.add(sets.item(j).getTextContent());
			}
		}
	}

	private void parseDepends(Document doc) {
		NodeList depList = doc.getElementsByTagName("dependencies");

		for (int i = 0; i < depList.getLength(); i++) {
			Element eElement = (Element) depList.item(i);
			NodeList deps = eElement.getElementsByTagName("dependency");

			for (int j = 0; j < deps.getLength(); j++) {
				this.depends.add(deps.item(j).getTextContent());
			}
		}
	}

	public void parse() throws SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(this.config);

			if (!doc.getDocumentElement().getNodeName().equals("config")) {
				throw new SAXException("Element config was not found in config file");
			}

			// parse element path
			this.parsePath(doc);

			// parse element package
			this.parsePackage(doc);

			// parse element tests
			this.parseTests(doc);

			// parse element dependencies
			this.parseDepends(doc);

		} catch (SAXException | ParserConfigurationException | IOException ex) {
			throw new SAXException("Can't parse " + config.getName() + " config file.");
		}
	}

}
