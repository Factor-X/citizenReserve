package eu.factorx.citizens.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.citizens.Global;
import eu.factorx.citizens.controllers.technical.AbstractController;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.Logger;
import play.mvc.Result;

public class TranslationController extends AbstractController {

	public Result get() {
		Logger.info("TranslationController.get()");

		ObjectMapper mapper = new ObjectMapper();
        try {
            response().setHeader("Content-Type", "application/json; charset=utf-8");
			return ok(mapper.writeValueAsString(Global.TRANSLATIONS));
		} catch (JsonProcessingException e) {
			throw new MyRuntimeException(e, e.getMessage());
		}
	}

}
