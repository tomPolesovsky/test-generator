/*
 * Parameter.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tomáš Polešovský
 */
public class Parameter {

	@Getter
	private String type;

	@Getter
	private String name;

	@Getter
	private String[] defaultVals = null;

	@Getter
	@Setter
	private int valNum = 0;

	public Parameter(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public Parameter(String type, String name, String[] defaultVals) {
		this.type = type;
		this.name = name;
		this.defaultVals = defaultVals;
	}

}
