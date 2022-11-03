package group11.comp3211;

import group11.comp3211.controller.GameManager;
import lombok.extern.slf4j.Slf4j;

public final class JungleApplication {
    public static void main(String[] args) {
        if(System.console() == null) {
            System.out.println("看readme运行");
            System.exit(1);
        }
        GameManager.getInstance().boot();
    }
}
