package eu.factorx.citizens.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.citizens.Global;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class TranslationController extends Controller {

    public Result get(String lang) {
        Logger.info("TranslationController.get(lang='{}')", lang);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return ok(mapper.writeValueAsString(Global.TRANSLATIONS));
        } catch (JsonProcessingException e) {
            throw new MyRuntimeException(e, e.getMessage());
        }
    }

}
