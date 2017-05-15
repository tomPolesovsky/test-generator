/*
 * GlueHelper.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.app;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * @author Tomáš Polešovský
 */
public class GlueHelper {

	private Config config;

	public GlueHelper(Config config) {
		this.config = config;
	}

	public void backUpGlues() {
		try {
			FileUtils.copyDirectory(new File(config.getOutputPath() + "/specglue/modules"), new File(config.getOutputPath() + "/specglue/_modules"));
			FileUtils.cleanDirectory(new File(config.getOutputPath() + "/specglue/modules"));
		} catch (IOException ex) {
			Logger.getLogger(GlueHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void restoreGlues() {
		try {
			FileUtils.deleteDirectory(new File(config.getOutputPath() + "/specglue/_modules"));
		} catch (IOException ex) {
			Logger.getLogger(GlueHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
