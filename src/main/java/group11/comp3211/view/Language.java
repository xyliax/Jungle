package group11.comp3211.view;

import java.io.Serializable;

/**
 * The enum Language.
 */
public enum Language implements Serializable {
    /**
     * English language.
     */
    ENGLISH,
    /**
     * Chinese simple language.
     */
    CHINESE_SIMPLE,
    /**
     * Chinese traditional language.
     */
    CHINESE_TRADITIONAL;
    /**
     * The constant online.
     */
    public static final Language[] online = new Language[]{ENGLISH, CHINESE_TRADITIONAL};
}
 