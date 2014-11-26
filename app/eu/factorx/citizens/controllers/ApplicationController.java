package eu.factorx.citizens.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ApplicationController extends Controller {

    public Result index(){
        return ok(eu.factorx.citizens.views.html.index.render());
    }

}
