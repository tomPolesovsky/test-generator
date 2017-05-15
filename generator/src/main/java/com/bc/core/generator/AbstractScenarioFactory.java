/*
 * AbstractScenarioFactory.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tomáš Polešovský
 */
public abstract class AbstractScenarioFactory {

	@Setter
	@Getter
	protected List<GeneratorTable> generator;

	public AbstractScenarioFactory() {
		this.generator = new ArrayList();
	}

	public void addGeneratorTable(GeneratorTable generator) {
		this.generator.add(generator);
	}

	public abstract ScenarioTemplate createScenario();

}
