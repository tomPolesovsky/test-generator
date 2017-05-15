/*
 * Parser.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

import com.bc.core.scanner.ClassFile;
import java.util.HashMap;
import com.bc.core.app.Config;
import com.bc.core.scanner.Scanner;

/**
 * @author Tomáš Polešovský
 */
public class Parser {

	private Scanner scanner;

	private Config config;

	private HashMap<String, Class> classes;

	private HashMap<String, MetaClass> table;

	public Parser(Config config, Scanner scanner) {
		this.config = config;
		this.scanner = scanner;
		this.classes = new HashMap();
		this.table = new HashMap();
	}

	public void parse(Loader loader) {
		MetaClass metaCls;
		ClassFile clsFile;

		while (scanner.hasNext()) {
			clsFile = scanner.next();
			Class clsLoaded = loader.loadClass(clsFile);

			this.classes.put(clsLoaded.getName(), clsLoaded);

			metaCls = this.parseClass(clsFile, clsLoaded);

			if (metaCls != null) {
				this.table.put(metaCls.getName(), metaCls);
			}
		}
	}

	protected MetaClass parseClass(ClassFile clsFile, Class cls) {
		MetaClass metaClass = new MetaClass(clsFile.getName());

		// set properties
		metaClass.setRelativePath(clsFile.getRelativePath());
		metaClass.setAbsolutePath(clsFile.getAbsolutePath());
		metaClass.setPack(clsFile.getPack());

		// parse annotations
		metaClass = (new AnnotationParser(metaClass)).parse(cls);

		// parse class attributes
		metaClass = (new AttributeParser(metaClass)).parse(cls);

		// parse methods
		metaClass = (new MethodParser(metaClass)).parse(cls);

		// parse constructor
		metaClass = (new ConstructorParser(metaClass)).parse(cls);

		return metaClass;
	}

	public HashMap<String, Class> getClasses() {
		return this.classes;
	}

	public HashMap<String, MetaClass> getTable() {
		return this.table;
	}

}
