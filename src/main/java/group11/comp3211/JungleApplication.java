package group11.comp3211;

import group11.comp3211.controller.GameManager;

/**
 * Program Launcher
 */
public final class JungleApplication {
    public static void main(String[] args) {
        GameManager.getInstance().boot();
    }
}
