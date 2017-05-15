/*
 * ScenarioFactory.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.validate;

import com.bc.core.generator.AbstractScenarioFactory;
import com.bc.core.generator.ScenarioBuilder;
import com.bc.core.generator.ScenarioTemplate;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioFactory extends AbstractScenarioFactory {

	@Override
	public ScenarioTemplate createScenario() {
		ScenarioBuilder scenario = new ScenarioBuilder("validateMethod_{variable.name}");

		scenario.setFeature("Validate the {variable.name} of the {class.name}");
		scenario.setScenario("Validate the {variable.name} and receive error message");

		scenario.addGiven("I have the value \"{variable.default.value}\" for a {variable.name.quo}");
		scenario.addWhen("I validate the {variable.name.quo} of the {class.name}");
		scenario.addThen("I receive the error message");

		return scenario.build(this.generator);
	}

}
