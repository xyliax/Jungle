package group11.comp3211.view;

import java.io.Serializable;

public enum Language implements Serializable {
    ENGLISH, CHINESE_SIMPLE, CHINESE_TRADITIONAL;
    public static final Language[] online = new Language[]{ENGLISH, CHINESE_TRADITIONAL};
}
