/*
 * GeneratorTable.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.generator;

import com.bc.core.app.Utils;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Method;
import com.bc.core.parser.Parameter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tomáš Polešovský
 */
public class GeneratorTable {

	public String getClassName(MetaClass metaCls) {
		return metaCls.getName();
	}

	public String getVariableName(MetaClass metaCls) {
		return metaCls.getMethods().get(metaCls.getSelectedMethod()).getName().replaceFirst("^validate", "").toLowerCase();
	}

	public String getVariableNameQuo(MetaClass metaCls) {
		return "\"" + this.getVariableName(metaCls) + "\"";
	}

	public String getMethodDefaultValue(MetaClass metaCls) {
		List<Method> methods = metaCls.getMethods();

		for (Method method : methods) {
			if (method.getName().equals("title")) {
				return method.getDefaultVals()[0];
			}
		}

		return null;
	}

	public String getVariableDefaultValue(MetaClass metaCls) {
		String attr = this.getVariableName(metaCls);
		List<Parameter> attrs = metaCls.getAttributes();

		for (Parameter at : attrs) {
			if (at.getName().equals(attr)) {
				return at.getDefaultVals()[0];
			}
		}

		return null;
	}

	public String getClassConstructParams(MetaClass metaCls) {
		StringBuilder sb = new StringBuilder();

		List<Parameter> params = metaCls.getConstructParams();
		String defVal;
		String[] defVals;

		Parameter param;

		int paramCounter = 0;
		int number;

		for (int i = 0; i < params.size(); i++) {
			if (Utils.isSupportedType(params.get(i).getType())) {

				param = params.get(i);

				if (paramCounter == 0) {
					sb.append("with ");
				}

				defVals = param.getDefaultVals();

				if (param.getValNum() >= defVals.length) {
					defVal = Utils.getRandomTypeVal(param.getType());
				} else {
					defVal = param.getDefaultVals()[param.getValNum()];
					number = param.getValNum();
					param.setValNum(++number);
				}

				sb.append(param.getName()).append(" ").append("\"").append(defVal).append("\"");

				if (i != params.size() - 1) {
					sb.append(" and ");
				}

				paramCounter++;
			}
		}

		return sb.toString();
	}

	public String find(String str, MetaClass metaCls) {
		str = str.substring(1, str.length() - 1);
		String[] parts = str.split("\\.");
		String method = "";

		for (String part : parts) {
			method += part.substring(0, 1).toUpperCase() + part.substring(1);
		}

		try {
			// find required method
			return (String) this.getClass().getMethod("get" + method, MetaClass.class).invoke(this, metaCls);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			Logger.getLogger(GeneratorTable.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	public String getConstructParams(MetaClass metaCls) {
		StringBuilder sb = new StringBuilder();
		List<Parameter> params = metaCls.getConstructParams();

		for (int i = 0; i < params.size(); i++) {
			if (Utils.isSupportedType(params.get(i).getType())) {
				sb.append(params.get(i).getType()).append(" ");
				sb.append(params.get(i).getName());

				if (i != params.size() - 1) {
					sb.append(", ");
				}
			}
		}

		return sb.toString();
	}

	public String getConstructObjParams(List<Parameter> objParams) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < objParams.size(); i++) {
			sb.append(objParams.get(i).getName());

			if (i != objParams.size() - 1) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	public String getConstructAnnotParams(MetaClass metaCls) {
		StringBuilder sb = new StringBuilder();
		List<Parameter> params = metaCls.getConstructParams();

		int paramCounter = 0;

		for (int i = 0; i < params.size(); i++) {
			if (Utils.isSupportedType(params.get(i).getType())) {
				if (paramCounter == 0) {
					sb.append(" with ");
				}

				sb.append(params.get(i).getName()).append(" ");

				// extern method for solving param type
				if (params.get(i).getType().equals("int")) {
					sb.append("\\\"(\\\\d+)\\\"");
				} else {
					sb.append("\\\"(.*)\\\"");
				}

				if (i != params.size() - 1) {
					sb.append(" and ");
				}

				paramCounter++;
			}
		}

		return sb.toString();
	}

	public boolean isSupportedObject(MetaClass metaCls) {
		List<Parameter> params = metaCls.getConstructParams();

		for (Parameter param : params) {
			if (!Utils.isSupportedType(param.getType())) {
				return false;
			}
		}

		return true;
	}
}
