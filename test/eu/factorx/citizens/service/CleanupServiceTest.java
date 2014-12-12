/*
 *
 * Instant Play Framework
 * AWAC
 *
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.citizens.service;

import eu.factorx.citizens.service.impl.CleanupServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;


public class CleanupServiceTest {

	@BeforeClass
	public static void setUp() {
		FakeApplication app = Helpers.fakeApplication();
		Helpers.start(app);
	}

	@Test
	public void runCleanup() throws Exception {

		CleanupService svc = new CleanupServiceImpl();
		svc.run();

	} // end of runCleanup test

}

