/*
 * AnnotationParser.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import java.lang.annotation.Annotation;

/**
 * @author Tomáš Polešovský
 */
public class AnnotationParser {

	private MetaClass metaCls;

	public AnnotationParser(MetaClass metaCls) {
		this.metaCls = metaCls;
	}

	public MetaClass parse(Class cls) {
		ClassType classType = ClassType.UNDEFINED;

		for (Annotation annotation : cls.getAnnotations()) {
			Class<? extends Annotation> type = annotation.annotationType();

			// it can be extended for other domain objects
			if (type.getSimpleName().equals("DomainObject")) {
				classType = ClassType.DOMAIN_OBJECT;
			}
		}

		this.metaCls.setType(classType);

		return this.metaCls;
	}

}
