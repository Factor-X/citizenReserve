package eu.factorx.citizens.controllers;

import eu.factorx.citizens.model.account.LanguageEnum;
import eu.factorx.citizens.service.TranslationService;

/**
 * Created by florian on 3/12/14.
 */
public class TranslationHelper {

    private TranslationService translationService;
    private LanguageEnum language;

    public TranslationHelper(TranslationService translationService,LanguageEnum language) {
        this.translationService = translationService;
        this.language = language;
    }

    public String getMessage(String key, String... params) {
        return translationService.getTranslation(key, language.getAbrv(), params);
    }

}
