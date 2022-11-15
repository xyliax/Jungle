package group11.comp3211;

import group11.comp3211.controller.GameManager;

import java.io.IOException;

public final class JungleApplication {
    public static void main(String[] args) throws IOException {
        GameManager.getInstance().boot();
    }
}
