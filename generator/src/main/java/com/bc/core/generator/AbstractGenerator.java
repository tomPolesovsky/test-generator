/*
 * AbstractGenerator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

import com.bc.core.app.Config;
import com.bc.core.app.Utils;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Parameter;
import java.util.List;

/**
 * @author Tomáš Polešovský
 */
public abstract class AbstractGenerator {

	protected Config config;

	public AbstractGenerator(Config config) {
		this.config = config;
	}

	public static boolean isSupportedObject(MetaClass metaCls) {
		List<Parameter> params = metaCls.getConstructParams();

		for (Parameter param : params) {
			if (!Utils.isSupportedType(param.getType())) {
				return false;
			}
		}

		return true;
	}

	public abstract void generate();

}
