package store;

import store.controller.ConvenienceController;

public class Application {
    public static void main(String[] args) {
        ConvenienceController convenienceController = new ConvenienceController();
        convenienceController.run();
    }
}
