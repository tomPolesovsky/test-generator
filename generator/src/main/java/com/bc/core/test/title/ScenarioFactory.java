/*
 * ScenarioFactory.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.title;

import com.bc.core.generator.AbstractScenarioFactory;
import com.bc.core.generator.ScenarioBuilder;
import com.bc.core.generator.ScenarioTemplate;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioFactory extends AbstractScenarioFactory 
{
	
	@Override
	public ScenarioTemplate createScenario()
	{
		ScenarioBuilder scenario = new ScenarioBuilder("setTitle");

		scenario.setFeature("Set the title of the {class.name}");
		scenario.setScenario("Set the title and receive string");
		
		scenario.addGiven("I have the object {class.name}");
		scenario.addWhen("I set the title of the {class.name}");
		scenario.addThen("I receive the string title");
		
		return scenario.build(this.generator);
	}
	
}
