/*
 * TestSetWriter.java
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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tomáš Polešovský
 */
public class TestSetWriter {

	protected Config config;

	public TestSetWriter(Config config) {
		this.config = config;
	}

	private List<String> prepareHeaderTestSetToFile(TestSet testSet, MetaClass metaCls) {
		List<String> list = new ArrayList();

		// set package
		String pack = metaCls.getPack().substring(config.getPackIn().length() + 1, metaCls.getPack().length());

		list.add("package " + config.getPackOut() + ".specglue.modules." + pack + ";" + System.lineSeparator());
		testSet.addLibrary("changed.annotation.TestChanged");

		// add libraries
		for (String library : testSet.getLibraries()) {
			list.add(library + ";");
		}

		list.add(System.lineSeparator());

		// set class 
		list.add("public class " + metaCls.getName() + "Glue extends CukeGlueAbstract");
		list.add("{" + System.lineSeparator());

		return list;
	}

	private List<String> prepareTestSetToFile(TestSet testSet, List<String> changedMethods) {
		List<String> list = new ArrayList();
		Pattern pattern = Pattern.compile("^public void (.*)\\(");
		Matcher matcherSign;

		for (String attribute : testSet.getAttributes()) {
			list.add("\t" + attribute + ";\n");
		}

		for (TestMethod method : testSet.getMethods()) {
			matcherSign = pattern.matcher((method.getSignature()).trim());

			if (matcherSign.find() && !changedMethods.contains(matcherSign.group(1))) {

				// annotations
				for (String annot : method.getAnnots()) {
					list.add("\t" + annot);
				}

				// signatures
				list.add("\t" + method.getSignature());
				list.add("\t{");

				// lines of code
				for (String line : method.getLines()) {
					list.add("\t\t" + line);
				}

				list.add("\t}" + System.lineSeparator());
			}
		}

		return list;
	}

	private boolean inCurrentMethods(TestSet testSet, String method) {
		List<TestMethod> methods = testSet.getMethods();

		Pattern patternMethod = Pattern.compile("^public void (.*)\\(");
		Matcher matcher;

		for (TestMethod md : methods) {
			matcher = patternMethod.matcher(md.getSignature().trim());

			if (matcher.find() && method.equals(matcher.group(1))) {
				return true;
			}
		}

		return false;
	}

	public void writeTestSet(TestSet testSet, MetaClass metaCls) {
		File testSetFileBackup = new File(config.getOutputPath() + "/specglue/_modules/" + metaCls.getRelativePath() + "/"
				+ metaCls.getName() + "Glue.java");

		File testSetFile = new File(config.getOutputPath() + "/specglue/modules/" + metaCls.getRelativePath() + "/"
				+ metaCls.getName() + "Glue.java");

		PrintWriter writer = null;

		List<String> fileLines;
		List<String> backupLines = new ArrayList();
		List<String> tempBackupLines = new ArrayList();
		List<String> changedMethods = new ArrayList();

		Pattern patternMethod = Pattern.compile("^public void (.*)\\(");
		Matcher matcherLine;
		boolean proceedBackup = true;

		if (testSetFileBackup.isFile()) {

			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(testSetFileBackup));
				int counter = 0;

				Pattern pattern = Pattern.compile("@TestChanged\\(number=(\\d+)\\)");
				Matcher matcher;
				String line;

				while ((line = bufferedReader.readLine()) != null) {
					matcher = pattern.matcher(line);

					// counter != 0 => backup methods with annotation @TestChanged
					if (counter != 0) {
						if (proceedBackup) {
							tempBackupLines.add(line);
						}

						matcherLine = patternMethod.matcher(line.trim());

						if (matcherLine.find()) {
							if (this.inCurrentMethods(testSet, matcherLine.group(1))) {
								changedMethods.add(matcherLine.group(1));
							} else {
								proceedBackup = false;
								tempBackupLines.clear();
							}
						}

						counter--;
						continue;
					}

					backupLines.addAll(tempBackupLines);
					tempBackupLines.clear();
					proceedBackup = true;

					if (matcher.find()) {
						counter = Integer.parseInt(matcher.group(1));
						tempBackupLines.add(System.lineSeparator() + line);
					}
				}
			} catch (IOException ex) {
				Logger.getLogger(ScenarioWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		String content = "";

		if (!testSetFile.isFile()) {
			testSetFile.getParentFile().mkdirs();

			fileLines = this.prepareHeaderTestSetToFile(testSet, metaCls);

			for (String line : fileLines) {
				content += line + System.lineSeparator();
			}
		} else {
			try {
				content = new String(Files.readAllBytes(testSetFile.toPath()));

				// remove last }
				content = content.replaceAll("}\\s*$", "");
			} catch (IOException ex) {
				Logger.getLogger(TestSetWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		List<String> list = this.prepareTestSetToFile(testSet, changedMethods);

		// write regular methods
		for (String line : list) {
			content += line + System.lineSeparator();
		}

		// write changed methods
		for (String line : backupLines) {
			content += line + System.lineSeparator();
		}

		try {
			content += "}";
			writer = new PrintWriter(testSetFile);
			writer.write(content);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(TestSetWriter.class.getName()).log(Level.SEVERE, null, ex);
		}

		writer.close();
	}

}
