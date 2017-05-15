/*
 * ScenarioGenerator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.title;

import com.bc.core.generator.ScenarioWriter;
import com.bc.core.generator.IScenarioGenerator;
import com.bc.core.generator.ScenarioTemplate;
import com.bc.core.parser.ClassType;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Parser;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioGenerator implements IScenarioGenerator {

	private final HashMap<String, MetaClass> parserTable;

	public ScenarioGenerator(Parser parser) {
		this.parserTable = parser.getTable();
	}

	@Override
	public void generateScenarios(ScenarioTemplate template, ScenarioWriter writer) {
		MetaClass metaCls;

		for (Map.Entry<String, MetaClass> entry : this.parserTable.entrySet()) {
			metaCls = entry.getValue();

			if (metaCls.getType() == ClassType.DOMAIN_OBJECT && Generator.isValidateObject(metaCls)) {
				writer.writeScenario(metaCls); 
			}
		}
	}
}
