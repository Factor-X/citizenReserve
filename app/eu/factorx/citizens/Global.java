package eu.factorx.citizens;

import akka.actor.Cancellable;
import eu.factorx.citizens.dto.technical.ExceptionsDTO;
import eu.factorx.citizens.service.impl.BatchServiceImpl;
import eu.factorx.citizens.util.exception.MyRuntimeException;
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

    public static final String BUNDLES_LOCATION = "translation/";
    public static final String[] BUNDLES = {"Messages", "Interfaces", "Surveys", "Email"};
    //IMPORTANT : the first language is the reference language ('fr')
    public static final String[] LANGUAGES = {"fr", "nl"};

    //first key : language
    //second key : message key
    //value : translatable message
    public static final Map<String, Map<String, String>> TRANSLATIONS = new HashMap<>();

    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader request, Throwable t) {

        final ExceptionsDTO exceptionsDTO;

        if (t.getCause() instanceof MyRuntimeException) {
            Logger.error("JE SUIS MyRuntimeException !!!!!!");
            exceptionsDTO = new ExceptionsDTO(((MyRuntimeException) t.getCause()).getBusinessErrorType().getMessageReference());
        } else {
            exceptionsDTO = new ExceptionsDTO(t.getCause().getMessage());
        }

        Logger.error("ERROR into global : " + exceptionsDTO.getMessage());

        return F.Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO
        ));
    }


    @Override
    public void beforeStart(Application app) {
		Logger.info("Global.beforeStart - START");

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
                    if (!translationCache.containsKey(reference.getKey())) {
                        translationCache.put(reference.getKey(), reference.getValue());
                    }

                }

            }

            languageCounter++;
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
                            new BatchServiceImpl().run();
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
