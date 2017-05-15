/*
 * ClassType.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.parser;

/**
 * @author Tomáš Polešovský
 */
public enum ClassType {

	UNDEFINED,
	DOMAIN_OBJECT,
	DOMAIN_SERVICE_VIEW,
	DOMAIN_SERVICE_VIEW_MENU_ONLY,
	DOMAIN_SERVICE_VIEW_CONTRIBUTIONS_ONLY,
	DOMAIN_SERVICE_VIEW_REST_ONLY,
	VIEW_OBJECT
}
