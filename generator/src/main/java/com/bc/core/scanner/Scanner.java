/*
 * Scanner.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.scanner;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.NoSuchElementException;
import java.util.Stack;
import com.bc.core.app.Config;
import com.bc.core.app.Utils;
import java.util.Arrays;

/**
 * @author Tomáš Polešovský
 */
public class Scanner {

	public static final String EXTENSION = "java";

	private Stack<File> stack = null;

	private ClassFile cached = null;

	private Config config;

	public Scanner(Config config) throws NotDirectoryException {
		File dir = new File(config.getInputPath());

		if (!dir.isDirectory()) {
			throw new NotDirectoryException(dir + "is not directory");
		}

		this.config = config;
		stack = new Stack();
		stack.push(dir);
	}

	public boolean hasNext() {
		if (cached != null) {
			return true;
		}

		try {
			cached = this.next();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public ClassFile next() {
		ClassFile cach = cached;

		if (cached != null) {
			cached = null;

			return cach;
		}

		File node;

		while (!stack.isEmpty()) {
			node = stack.pop();

			if (node.isFile() && Utils.getExtension(node) != null && Utils.getExtension(node).equals(EXTENSION)) {
				return new ClassFile(node, config);
			}

			if (node.isDirectory()) {
				File[] files = node.listFiles();

				stack.addAll(Arrays.asList(files));
			}
		}

		if (stack.isEmpty()) {
			throw new NoSuchElementException();
		}

		return null;
	}

}
