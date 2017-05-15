/*
 * MetaClass.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tomáš Polešovský
 */
public class MetaClass {

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String relativePath;

	@Getter
	@Setter
	private String absolutePath;

	@Getter
	@Setter
	private String pack;

	@Getter
	@Setter
	private ClassType type;

	@Getter
	private List<Parameter> attributes;

	@Getter
	private List<Parameter> constructParams;

	@Getter
	private List<Method> methods;

	@Getter
	@Setter
	private int selectedMethod = 0;

	public MetaClass(String name) {
		this.name = name;
		this.type = ClassType.UNDEFINED;
		this.attributes = new ArrayList();
		this.constructParams = new ArrayList();
		this.methods = new ArrayList();
	}

	public void addAttribute(Parameter param) {
		this.attributes.add(param);
	}

	public void addConstructParams(Parameter param) {
		this.constructParams.add(param);
	}

	public void addMethod(Method method) {
		this.methods.add(method);
	}

}
