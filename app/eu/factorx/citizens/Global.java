package eu.factorx.citizens;

import akka.actor.Cancellable;
import eu.factorx.citizens.dto.technical.ExceptionsDTO;
import org.apache.commons.lang3.StringEscapeUtils;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;
import play.mvc.SimpleResult;
import scala.concurrent.duration.Duration;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
		Logger.info("Global.beforeStart - START");

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
                    try {
                        value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    translationCache.put(key, value);
                }
            }
        }
        Logger.info("Global.beforeStart - END");
    }

	@Override
	public void onStart(Application app) {

		Logger.info("Global.onStart - START");


		// start Akka task every 24 hours to compute consolidation statistics
		Cancellable schedule = Akka.system().scheduler().schedule(
				Duration.create(10, TimeUnit.SECONDS),
				Duration.create(1, TimeUnit.MINUTES),
				new Runnable() {
					public void run() {
						try {
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date date = new Date();
							Logger.info("Consolidation Batch Started @" + dateFormat.format(date));
							// run batch here
							date = new Date();
							Logger.info("Consolidation Batch Ended @" + dateFormat.format(date));

						} catch (Exception e) {
							Logger.info("batch exception...", e);
						}
					}
				},
				Akka.system().dispatchers().defaultGlobalDispatcher()
		);

		Logger.info("Global.onStart - END");
	}

}
