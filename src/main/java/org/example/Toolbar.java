package org.example;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainScreen mainScreen;

    public Toolbar(MainScreen mainScreen) {
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button generate = new Button("Generate");
        generate.setOnAction(this::handleGenerate);

        this.getItems().addAll(draw,erase,step,generate);
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Draw pressed");
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Erase pressed");
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Step pressed");
        this.mainScreen.getSimulation().step();
        this.mainScreen.draw();
    }

    private void handleGenerate(ActionEvent actionEvent) {
        System.out.println("Generate pressed");
    }


}
