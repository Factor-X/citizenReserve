package eu.factorx.citizenReserve.controllers;

import eu.factorx.citizenReserve.dto.technical.TranslationsDTO;
import eu.factorx.citizenReserve.util.exception.MyRuntimeException;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by florian on 18/11/14.
 */
public class TranslationController extends Controller {

    public Result get(String lang) {

        TranslationsDTO translationsDTO = new TranslationsDTO();

        //load file by lang
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("public/translation/en.properties"));

            for (String propertyName : properties.stringPropertyNames()) {
                translationsDTO.put(propertyName, properties.get(propertyName).toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
            throw new MyRuntimeException("Cannot found translations for language "+lang);
        }
        return ok(translationsDTO);
    }
}
