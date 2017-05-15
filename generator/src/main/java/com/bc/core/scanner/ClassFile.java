/*
 * ClassFile.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.scanner;

import java.io.File;
import lombok.Getter;
import com.bc.core.app.Config;
import com.bc.core.app.Utils;

/**
 *
 * @author Tomáš Polešovský
 */
public class ClassFile {

	private File file;

	private Config config;

	@Getter
	private String absolutePath;

	@Getter
	private String relativePath;

	@Getter
	private String pack;

	@Getter
	private String name;

	public ClassFile(File file, Config config) {
		this.file = file;
		this.config = config;

		this.absolutePath = file.getAbsolutePath();
		this.relativePath = this.findRelativePath();
		this.name = this.findClassName();
		this.pack = this.findPackage();
	}

	private String findRelativePath() {
		String base = config.getInputPath();
		String path = file.getParent();

		String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

		if (relative.endsWith("/")) {
			relative = relative.substring(0, relative.length() - 1);
		}

		return relative;
	}

	private String findClassName() {
		return file.getName().substring(0, file.getName().lastIndexOf("."));
	}

	private String findPackage() {
		String pck = config.getPackIn();

		if (!relativePath.equals("")) {
			pck += ".";
		}

		return pck + relativePath.replace("/", ".");
	}

	public String getExtension() {
		return Utils.getExtension(file);
	}

}
