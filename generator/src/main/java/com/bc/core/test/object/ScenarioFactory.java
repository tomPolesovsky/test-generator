/*
 * ScenarioFactory.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.object;

import com.bc.core.generator.AbstractScenarioFactory;
import com.bc.core.generator.ScenarioBuilder;
import com.bc.core.generator.ScenarioTemplate;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioFactory extends AbstractScenarioFactory {

	@Override
	public ScenarioTemplate createScenario() {
		ScenarioBuilder scenario = new ScenarioBuilder("listAllAndCreate");

		scenario.setFeature("List and Create new {class.name} Objects");
		scenario.setScenario("Existing {class.name} objects can be listed and new ones created");

		scenario.addGiven("I have 0 {class.name} objects");
		scenario.addWhen("I create a {class.name} object {class.construct.params}")
				.addWhen("I create a {class.name} object {class.construct.params}");
		scenario.addThen("I have 2 {class.name} objects");

		return scenario.build(this.generator);
	}

}
