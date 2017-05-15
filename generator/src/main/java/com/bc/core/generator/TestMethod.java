/*
 * TestMethod.java
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
public class TestMethod {

	@Getter
	@Setter
	private String signature;

	@Getter
	private List<String> annots;

	@Getter
	private List<String> lines;

	public TestMethod(String signature) {
		this.signature = signature;
		this.annots = new ArrayList();
		this.lines = new ArrayList();
	}

	public TestMethod addAnnot(String annot) {
		this.annots.add(annot);

		return this;
	}

	public TestMethod addLine(String line) {
		this.lines.add(line);

		return this;
	}

}
