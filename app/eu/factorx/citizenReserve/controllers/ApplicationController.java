package eu.factorx.citizenReserve.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ApplicationController extends Controller {

    public Result index(){
        return ok(eu.factorx.citizenReserve.views.html.index.render());
    }

}
