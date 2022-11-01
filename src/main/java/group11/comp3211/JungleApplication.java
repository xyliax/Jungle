package group11.comp3211;

import group11.comp3211.controller.GameManager;
import group11.comp3211.view.JungleIO;

public final class JungleApplication {
    public static void main(String[] args) {
        JungleIO.getInstance().waitKey('E');
        System.out.println("READ E");
        GameManager.getInstance().boot();
    }
}