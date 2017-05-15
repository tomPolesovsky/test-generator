/*
 * ConstructorParser.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import com.bc.core.app.Utils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import test.annotation.TestParam;

/**
 * @author Tomáš Polešovský
 */
public class ConstructorParser {

	private MetaClass metaCls;

	public ConstructorParser(MetaClass metaCls) {
		this.metaCls = metaCls;
	}

	public MetaClass parse(Class cls) {
		Constructor[] constructors = cls.getConstructors();

		// constructor types
		Class[] paramTypes = constructors[0].getParameterTypes();
		Annotation[][] paramAnnots;

		String paramName;
		String[] paramDefs;

		for (int i = 0; i < paramTypes.length; i++) {
			paramName = Utils.getRandomString(8);
			paramDefs = new String[0];
			paramAnnots = constructors[0].getParameterAnnotations();

			for (Annotation paramAnnot : paramAnnots[i]) {
				if (paramAnnot instanceof TestParam) {
					TestParam test = (TestParam) paramAnnot;
					paramName = test.name();

					if (Utils.isSupportedType(paramTypes[i].getName())) {
						paramDefs = test.def();
					}
				}
			}

			this.metaCls.addConstructParams(new Parameter(paramTypes[i].getName(), paramName, paramDefs));
		}

		return this.metaCls;
	}

}
