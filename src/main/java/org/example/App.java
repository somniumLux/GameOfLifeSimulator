package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainScreen mainScreen = new MainScreen();
        Scene scene = new Scene(mainScreen, 640, 480);
        stage.setScene(scene);
        stage.show();

        mainScreen.draw();
    }

    public static void main(String[] args) {
        launch();
    }

}