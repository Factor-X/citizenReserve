package eu.factorx.citizens.service;

import eu.factorx.citizens.model.account.LanguageEnum;
import eu.factorx.citizens.util.BusinessErrorType;

/**
 * Created by florian on 2/12/14.
 */
public interface TranslationService {

    public String getTranslation(String key, String language,String... params);

    public String getTranslation(BusinessErrorType errorType, LanguageEnum language,String... params);
}
