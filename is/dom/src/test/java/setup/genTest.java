/*
 * genTest.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package setup;

import com.bc.core.app.Runner;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomáš Polešovský
 */
public class genTest {

	@Before
	public void setUp() {
		try {
			(new Runner()).run();
		} catch (Exception e) {
			System.out.println("[WARNING] Test generator was not correctly loaded");
			System.err.println("[WARNING] Exception: " + e.getMessage());
		}
	}

	@Test
	public void test() {
	}

}
