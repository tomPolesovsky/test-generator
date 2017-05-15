/*
 * ScenarioWriter.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

import com.bc.core.app.Config;
import com.bc.core.parser.MetaClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioWriter {

	protected ScenarioTemplate template;

	protected Config config;

	public ScenarioWriter(ScenarioTemplate template, Config config) {
		this.template = template;
		this.config = config;
	}

	private String replaceParameters(String str, MetaClass metaCls) {
		Pattern pattern = Pattern.compile("\\{.*?\\}");
		Matcher matcher = pattern.matcher(str);

		// it can be extended for multiple tables
		GeneratorTable generatorTable = template.getGeneratorTable().get(0);

		boolean find = matcher.find();

		if (find) {
			StringBuilder sb = new StringBuilder();
			char[] buffer = str.toCharArray();
			int position = 0;

			while (find) {
				// replace all parameters
				sb.append(buffer, position, matcher.start() - position).append(generatorTable.find(matcher.group(), metaCls));
				position += (matcher.start() - position) + (matcher.group()).length();

				find = matcher.find();
			}

			if (position != buffer.length) {
				sb.append(buffer, position, (buffer.length) - position);
			}

			return sb.toString();
		}

		return str;
	}

	private List<String> prepareScenarioToFile(MetaClass metaCls) {
		List<String> list = new ArrayList();

		// Feature & Scenario
		list.add("Feature: " + this.replaceParameters(template.getFeature(), metaCls));
		list.add("\t@integration");
		list.add("\t\tScenario: " + this.replaceParameters(template.getScenario(), metaCls));

		// given
		for (String given : template.getGiven()) {
			list.add("\t\t\tGiven " + this.replaceParameters(given, metaCls).trim());
		}

		// when
		for (String when : template.getWhen()) {
			list.add("\t\t\tWhen " + this.replaceParameters(when, metaCls).trim());
		}

		// then
		for (String then : template.getThen()) {
			list.add("\t\t\tThen " + this.replaceParameters(then, metaCls).trim());
		}

		return list;
	}

	public void writeScenario(MetaClass metaCls) {
		File scenarioFile = new File(config.getOutputPath() + "/specs/modules/" + metaCls.getRelativePath() + "/"
				+ metaCls.getName() + "Spec_" + this.replaceParameters(template.getName(), metaCls) + ".feature");

		List<String> lines = new ArrayList();

		if (scenarioFile.isFile()) {
			// for existing scenario
			try {
				BufferedReader bufferedReader;
				bufferedReader = new BufferedReader(new FileReader(scenarioFile));
				String changed = null;

				Pattern pattern = Pattern.compile("#@changed");
				Matcher matcher;
				String line;

				while ((line = bufferedReader.readLine()) != null) {
					matcher = pattern.matcher(line);

					if (matcher.find()) {
						changed = line;
						continue;
					}

					if (changed != null) {
						lines.add(changed + System.lineSeparator() + line);
						changed = null;
					} else {
						lines.add(null);
					}

				}
			} catch (IOException ex) {
				Logger.getLogger(ScenarioWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			// create new scenario folder
			scenarioFile.getParentFile().mkdirs();
		}

		try {
			PrintWriter writer = new PrintWriter(scenarioFile);

			List<String> list = this.prepareScenarioToFile(metaCls);

			for (int i = 0; i < list.size(); i++) {
				if (i < lines.size() && lines.get(i) != null) {
					writer.println(lines.get(i));
				} else {
					writer.println(list.get(i));
				}
			}

			writer.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ScenarioWriter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
