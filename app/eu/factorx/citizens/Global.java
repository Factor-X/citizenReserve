package eu.factorx.citizens;

import eu.factorx.citizens.dto.technical.ExceptionsDTO;
import org.apache.commons.lang3.StringEscapeUtils;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;
import play.mvc.SimpleResult;

import java.util.*;

public class Global extends GlobalSettings {

    public static final String   BUNDLES_LOCATION = "translation/";
    public static final String[] BUNDLES          = {"Messages", "Interfaces", "Surveys"};
    public static final String[] LANGUAGES        = {"fr", "nl"};

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
        for (String lang : LANGUAGES) {
            HashMap<String, String> translationCache = new HashMap<>();
            TRANSLATIONS.put(lang, translationCache);
            for (String bundleName : BUNDLES) {
                ResourceBundle bundle = ResourceBundle.getBundle(BUNDLES_LOCATION + bundleName, Locale.forLanguageTag(lang));
                Enumeration<String> bundleKeys = bundle.getKeys();
                while (bundleKeys.hasMoreElements()) {
                    String key = bundleKeys.nextElement();
                    String value = bundle.getString(key);

                    value = StringEscapeUtils.escapeHtml4(value);
                    value = value.replace("&lt;", "<");
                    value = value.replace("&gt;", ">");

                    System.out.println(value);
                    translationCache.put(key, value);
                }
            }
        }
        Logger.info("Global.onStart - END");
    }

}
