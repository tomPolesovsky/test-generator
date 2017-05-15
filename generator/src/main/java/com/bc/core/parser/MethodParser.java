/*
 * MethodParser.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import java.lang.annotation.Annotation;
import test.annotation.TestMethod;

/**
 * @author Tomáš Polešovský
 */
public class MethodParser {

	private MetaClass metaCls;

	public MethodParser(MetaClass metaCls) {
		this.metaCls = metaCls;
	}

	public MetaClass parse(Class cls) {
		java.lang.reflect.Method[] methods = cls.getDeclaredMethods();

		Annotation[] annots;
		String name;
		String[] paramDefs;

		for (java.lang.reflect.Method md : methods) {
			name = md.getName();
			paramDefs = new String[0];

			annots = md.getDeclaredAnnotations();

			for (Annotation annot : annots) {
				if (annot instanceof TestMethod) {
					TestMethod test = (TestMethod) annot;
					name = test.name();
					paramDefs = test.def();
				}
			}

			this.metaCls.addMethod(new Method(md.getReturnType().toString(), name, paramDefs));
		}

		return this.metaCls;
	}

}
