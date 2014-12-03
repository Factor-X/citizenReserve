package eu.factorx.citizens.controllers;

import eu.factorx.citizens.service.TranslationService;

/**
 * Created by florian on 3/12/14.
 */
public class TranslationHelper {

    private TranslationService translationService;

    public TranslationHelper(TranslationService translationService) {
        this.translationService = translationService;
    }

    public String getMessage(String key, String... params) {
        return translationService.getTranslation(key, "fr", params);
    }

}
