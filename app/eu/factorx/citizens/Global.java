package eu.factorx.citizens;

import eu.factorx.citizens.dto.technical.ExceptionsDTO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;
import play.mvc.SimpleResult;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class Global extends GlobalSettings {

    public static final String BUNDLES_LOCATION = "translation/";
    public static final String[] BUNDLES = {"Messages", "Interfaces", "Surveys","Email"};
    //IMPORTANT : the first language is the reference language ('fr')
    public static final String[] LANGUAGES = {"fr", "nl"};

    //first key : language
    //second key : message key
    //value : translatable message
    public static final Map<String, Map<String, String>> TRANSLATIONS = new HashMap<>();

    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader request, Throwable t) {
        ExceptionsDTO exceptionsDTO = new ExceptionsDTO(t.getCause().getMessage());

        Logger.error("ERROR into global : " + exceptionsDTO.getMessage());

        return F.Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO
        ));
    }


    @Override
    public void beforeStart(Application app) {
        System.out.println("Global.onStart - START");

        // Put all translations in memory
        int languageCounter = 0;
        for (String lang : LANGUAGES) {

            //first language = reference language
            HashMap<String, String> translationCache = new HashMap<>();
            TRANSLATIONS.put(lang, translationCache);
            for (String bundleName : BUNDLES) {
                ResourceBundle bundle = ResourceBundle.getBundle(BUNDLES_LOCATION + bundleName, Locale.forLanguageTag(lang));
                Enumeration<String> bundleKeys = bundle.getKeys();
                while (bundleKeys.hasMoreElements()) {
                    String key = bundleKeys.nextElement();
                    String value = bundle.getString(key);
                    try {
                        value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    translationCache.put(key, value);
                }
            }

            if (languageCounter > 0) {

                //complete hole by comparison with reference language
                for (Map.Entry<String, String> reference : TRANSLATIONS.get(LANGUAGES[0]).entrySet()) {
                    if(!translationCache.containsKey(reference.getKey())){
                        translationCache.put(reference.getKey(),reference.getValue());
                    }

                }

            }

            languageCounter++;
        }
        Logger.info("Global.onStart - END");
    }

}
