package eu.factorx.citizens.service;

/**
 * Created by florian on 2/12/14.
 */
public interface TranslationService {

    public String getTranslation(String key, String language,String... params);
}
