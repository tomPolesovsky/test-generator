/*
 * Utils.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.app;

import java.io.File;
import java.util.Random;

/**
 * @author Tomáš Polešovský
 */
public class Utils {

	public static String getExtension(File file) {
		String name = file.getName();

		if (name.lastIndexOf(".") == -1 || name.lastIndexOf(".") == 0) {
			return null;
		}

		return name.substring(name.lastIndexOf(".") + 1);
	}

	public static String getRandomString(int length) {
		Random rand = new Random();

		String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length - 1; i++) {
			sb.append(characters.charAt(rand.nextInt((characters.length() - 1))));
		}

		return "v" + sb.toString();
	}

	public static String getRandomNumber(int length) {
		Random rand = new Random();

		String characters = "123456789";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length - 1; i++) {
			sb.append(characters.charAt(rand.nextInt((characters.length() - 1))));
		}

		return sb.toString();
	}

	public static String getRandomTypeVal(String type) {
		if (type.equals("java.lang.String")) {
			return Utils.getRandomString(5);
		} else if (type.equals("int")) {
			return Utils.getRandomNumber(5);
		}

		return null;
	}

	public static boolean isSupportedType(String type) {
		String[] primitives = {"byte", "short", "long", "float", "double", "boolean", "char",
			"java.lang.String", "int", "boolean"};

		for (String primitive : primitives) {
			if ((type.trim()).equals(primitive)) {
				return true;
			}
		}

		return false;
	}

}
