/*
 * ScenarioBuilder.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

import java.util.List;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioBuilder {

	private String feature = null;

	private String scenario = null;

	private final ScenarioTemplate template;

	public ScenarioBuilder(String name) {
		this.template = new ScenarioTemplate(name, null, null);
	}

	public ScenarioBuilder setFeature(String seq) {
		this.feature = seq;

		return this;
	}

	public ScenarioBuilder setScenario(String seq) {
		this.scenario = seq;

		return this;
	}

	public ScenarioBuilder addGiven(String given) {
		this.template.addGiven(given);

		return this;
	}

	public ScenarioBuilder addWhen(String when) {
		this.template.addWhen(when);

		return this;
	}

	public ScenarioBuilder addThen(String then) {
		this.template.addThen(then);

		return this;
	}

	public ScenarioTemplate build(List<GeneratorTable> tableList) {
		if (feature == null || scenario == null) {
			throw new NullPointerException("Feature or scenario is not set in ScenarioBuilder");
		}

		this.template.setScenario(scenario);
		this.template.setFeature(feature);
		this.template.setGeneratorTable(tableList);

		return this.template;
	}

}
