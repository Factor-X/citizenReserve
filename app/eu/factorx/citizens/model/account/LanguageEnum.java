package eu.factorx.citizens.model.account;

/**
 * Created by florian on 5/12/14.
 */
public enum LanguageEnum {

    FRANCAIS("fr"),
    NEERDERLANDS("nl");

    private String abrv;
    private static LanguageEnum defaultLanguage;

    LanguageEnum(String abrv) {
        this.abrv = abrv;
    }

    public static LanguageEnum getDefaultLanguage() {
        return LanguageEnum.FRANCAIS;
    }

    public String getAbrv() {
        return abrv;
    }

    public static LanguageEnum getByAbvr(String languageAbvr) {
        for (LanguageEnum languageEnum : values()) {
            if (languageEnum.getAbrv().equals(languageAbvr)) {
                return languageEnum;
            }
        }
        return null;
    }
}
