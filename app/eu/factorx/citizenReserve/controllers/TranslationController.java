package eu.factorx.citizenReserve.controllers;

import eu.factorx.citizenReserve.dto.technical.TranslationsDTO;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationController extends Controller {

    public static final String BUNDLES_LOCATION = "public/translation/";
    public static final String[] BUNDLES = {"Messages", "Interfaces", "Surveys"};

    public Result get(String lang) {
        // Get Locale
        Locale locale = Locale.forLanguageTag(lang);
        if (locale == null) {
            Logger.warn("Cannot get java.util.Locale for language code: '{}'", lang);
        }
        Logger.info("locale = {}", locale);

        // Get TranslationsDTO
        TranslationsDTO translationsDTO = new TranslationsDTO(lang);
        for (String bundleName : BUNDLES) {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLES_LOCATION + bundleName, locale);
            Enumeration<String> bundleKeys = bundle.getKeys();
            while(bundleKeys.hasMoreElements()) {
                String key = bundleKeys.nextElement();
                translationsDTO.put(key, bundle.getString(key));
            }
        }
        return ok(translationsDTO);
    }

}
