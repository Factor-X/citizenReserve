package eu.factorx.citizens.service.impl;

import eu.factorx.citizens.Global;
import eu.factorx.citizens.service.TranslationService;
import play.Logger;

/**
 * Created by florian on 2/12/14.
 */
public class TranslationServiceImpl implements TranslationService {

    @Override
    public String getTranslation(String key, String language, String... params) {

        if (Global.TRANSLATIONS.get(language) != null) {
            if (Global.TRANSLATIONS.get(language).get(key) != null) {

                String content = Global.TRANSLATIONS.get(language).get(key);
                Logger.error("Founded : "+key+"=>"+content);
                for (int i = 0; i < params.length; i++) {
                    content = content.replace("{" + i + "}", params[i]);
                }
                return content;
            }
        }
        return null;
    }
}
