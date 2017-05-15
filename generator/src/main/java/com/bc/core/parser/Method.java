/*
 * Method.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import java.util.List;
import lombok.Getter;

/**
 * @author Tomáš Polešovský
 */
public class Method {

	@Getter
	private String name;

	@Getter
	private String retType;

	@Getter
	private String[] defaultVals = null;

	@Getter
	private List<Parameter> params;

	public Method(String retType, String name) {
		this.retType = retType;
		this.name = name;
	}

	public Method(String type, String name, String[] defaultVals) {
		this.retType = type;
		this.name = name;
		this.defaultVals = defaultVals;
	}

}
