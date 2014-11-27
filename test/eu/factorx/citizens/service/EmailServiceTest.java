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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.citizens.util.email.messages.EmailMessage;
import eu.factorx.citizens.util.email.service.EmailService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

import com.fasterxml.jackson.databind.JsonNode;
import play.test.Helpers;


//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

public class EmailServiceTest {

	@BeforeClass
	public static void setUp() {
		FakeApplication app = Helpers.fakeApplication();
		Helpers.start(app);
	}

	@Test
	public void sendMail () throws Exception {

		EmailService emailService = new EmailService();
		EmailMessage message = new EmailMessage("gaston.hollands@factorx.eu", "Subject","Content");
		emailService.send(message);

		// wait
		Thread.sleep(10000);

	} // end of authenticateSuccess test

}

