/*
 * Generator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.title;

import com.bc.core.app.Config;
import com.bc.core.generator.AbstractGenerator;
import com.bc.core.generator.GeneratorTable;
import com.bc.core.generator.IScenarioGenerator;
import com.bc.core.generator.ITestSetGenerator;
import com.bc.core.generator.ScenarioTemplate;
import com.bc.core.generator.ScenarioWriter;
import com.bc.core.generator.TestSetWriter;
import com.bc.core.parser.Loader;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Method;
import com.bc.core.parser.Parser;
import com.bc.core.scanner.Scanner;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tomáš Polešovský
 */
public class Generator extends AbstractGenerator {

	private Parser parser;

	private Loader loader;

	public Generator(Config config) {
		super(config);

		try {
			this.parser = new Parser(this.config, new Scanner(this.config));
			this.loader = new Loader(this.config);
			this.parser.parse(this.loader);
		} catch (NotDirectoryException ex) {
			Logger.getLogger(com.bc.core.test.object.Generator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static boolean isValidateMethod(MetaClass metaCls, Method method) {
		return method.getName().equals("title") && method.getDefaultVals().length > 0;
	}

	public static boolean isValidateObject(MetaClass metaCls) {
		List<Method> methods = metaCls.getMethods();

		for (Method method : methods) {
			if (Generator.isValidateMethod(metaCls, method)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void generate() {
		// generator tables
		List<GeneratorTable> tableList = new ArrayList();
		tableList.add(new GeneratorTable());

		// generator for scenario & test sets
		IScenarioGenerator scenarioGenerator = new ScenarioGenerator(this.parser);
		ITestSetGenerator testSetGenerator = new TestSetGenerator(this.parser, tableList.get(0));

		// create scenario template
		com.bc.core.test.title.ScenarioFactory factory = new com.bc.core.test.title.ScenarioFactory();
		factory.setGenerator(tableList);

		ScenarioTemplate template = factory.createScenario();

		// generate scenarios & test sets
		scenarioGenerator.generateScenarios(template, new ScenarioWriter(template, this.config));

		testSetGenerator.generateTestSets(new TestSetWriter(this.config));
	}
}
