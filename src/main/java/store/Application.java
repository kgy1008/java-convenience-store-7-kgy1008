package store;

import store.config.AppConfig;
import store.controller.ConvenienceController;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        ConvenienceController convenienceController = appConfig.convenienceController();
        convenienceController.run();
    }
}
