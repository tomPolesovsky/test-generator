/*
 * ScenarioTemplate.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tomáš Polešovský
 */
public class ScenarioTemplate implements Serializable {

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String feature;

	@Getter
	@Setter
	private String scenario;

	@Getter
	@Setter
	private List<GeneratorTable> generatorTable;

	@Getter
	private final List<String> given;

	@Getter
	private final List<String> when;

	@Getter
	private final List<String> then;

	public ScenarioTemplate(String name, String feature, String scenario) {
		this.name = name;
		this.feature = feature;
		this.scenario = scenario;
		this.generatorTable = new ArrayList();

		this.given = new ArrayList();
		this.when = new ArrayList();
		this.then = new ArrayList();
	}

	public void addGiven(String given) {
		this.given.add(given);
	}

	public void addWhen(String when) {
		this.when.add(when);
	}

	public void addThen(String then) {
		this.then.add(then);
	}

}
