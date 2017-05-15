/*
 * IScenarioGenerator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

/**
 * @author Tomáš Polešovský
 */
public interface IScenarioGenerator {

	public void generateScenarios(ScenarioTemplate template, ScenarioWriter writer);

}
