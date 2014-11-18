package eu.factorx.citizenReserve.controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by florian on 18/11/14.
 */
public class ApplicationController extends Controller {

    public Result index(){
        return ok(eu.factorx.citizenReserve.views.html.index.render());
    }

}
