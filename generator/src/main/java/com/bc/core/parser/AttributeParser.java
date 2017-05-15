/*
 * AttributeParser.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import com.bc.core.app.Utils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import test.annotation.TestVar;

/**
 * @author Tomáš Polešovský
 */
public class AttributeParser {

	private MetaClass metaCls;

	public AttributeParser(MetaClass metaCls) {
		this.metaCls = metaCls;
	}

	public MetaClass parse(Class cls) {
		Annotation[] annots;
		Field[] fields = cls.getDeclaredFields();
		Field field;
		String paramName;
		String[] paramDefs;

		for (Field field1 : fields) {
			field = field1;
			paramName = field.getName();
			paramDefs = new String[0];

			annots = field.getDeclaredAnnotations();

			for (Annotation annot : annots) {
				if (annot instanceof TestVar) {
					TestVar test = (TestVar) annot;
					paramName = test.name();

					if (Utils.isSupportedType(field.getType().toString())) {
						paramDefs = test.def();
					}
				}
			}

			this.metaCls.addAttribute(new Parameter(field.getType().toString(), paramName, paramDefs));
		}

		return this.metaCls;
	}

}
