package eu.factorx.citizens.controllers;

import eu.factorx.citizens.controllers.technical.AbstractController;
import play.mvc.Result;

public class ApplicationController extends AbstractController {

	public Result index() {
		return ok(eu.factorx.citizens.views.html.index.render());
	}

}
