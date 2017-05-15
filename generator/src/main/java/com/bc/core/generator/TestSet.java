/*
 * TestSet.java
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
public class TestSet {

	@Getter
	@Setter
	private String name;

	@Getter
	private final List<String> libraries;

	@Getter
	private final List<String> attributes;

	@Getter
	private final List<TestMethod> methods;

	public TestSet(String name) {
		this.name = name;
		this.libraries = new ArrayList();
		this.attributes = new ArrayList();
		this.methods = new ArrayList();
	}

	public void addLibrary(String lib) {
		this.libraries.add("import " + lib);
	}

	public void addLibrary(String lib, boolean importStatic) {
		if (importStatic) {
			this.libraries.add("import static " + lib);
		} else {
			this.addLibrary(lib);
		}
	}

	public void addAttribute(String var) {
		this.attributes.add(var);
	}

	public TestMethod addMethod(String line) {
		TestMethod method = new TestMethod(line);
		this.methods.add(method);

		return method;
	}

}
