package group11.comp3211;

import group11.comp3211.controller.GameManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JungleApplication {
    public static void main(String[] args) {
        log.info("abcde");
        log.warn("abcde");
        log.error("abcde");
        GameManager.getInstance().boot();
    }
}
