package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainScreen extends VBox {

    private Canvas canvas;
    private Affine affine;
    private Simulation simulation;

    public MainScreen() {

        int simulationWidth = 10, simulationHeight = 10;
        double canvasWidth = 400, canvasHeight = 400;

        Button stepButton = new Button("step");
        stepButton.setOnAction(actionEvent -> {
            simulation.step();
            draw();
            }
        );

        Button generateRandomButton = new Button("generate");
        generateRandomButton.setOnAction(actionEvent -> {
            simulation.initializeRandomBoard();
            draw();
        });

        this.canvas = new Canvas(canvasWidth, canvasHeight);
        this.canvas.setOnMousePressed(this::handleDraw);

        this.getChildren().addAll(stepButton,generateRandomButton,this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(canvasWidth / (double) simulationWidth, canvasHeight / (double) simulationHeight);

        this.simulation = new Simulation(simulationWidth,simulationHeight);
        this.simulation.initializeRandomBoard();
    }

    private void handleDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        //System.out.println(mouseX + ", " + mouseY);

        try {
            Point2D simCoordinates = this.affine.inverseTransform(mouseX, mouseY);
            System.out.println(simCoordinates);

            int simX = (int) simCoordinates.getX();
            int simY = (int) simCoordinates.getY();

            System.out.println(simX + ", " + simY);

        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
        }
    }

    public void draw() {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);

        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0,0,450,450);

        graphicsContext.setFill(Color.DARKBLUE);
        for (int x = 0; x < this.simulation.width; x++) {
            for (int y = 0; y < this.simulation.height; y++) {
                if(this.simulation.getState(x,y) == CellState.ALIVE_CELL.getState()) {
                    graphicsContext.fillRect(x,y,1,1);
                }
            }
        }

        graphicsContext.setStroke(Color.LIGHTSKYBLUE);
        graphicsContext.setLineWidth(0.05);
        for (int x = 0; x <= this.simulation.width; x++)
            graphicsContext.strokeLine(x,0,x,10);

        for (int y = 0; y <= this.simulation.height; y++)
            graphicsContext.strokeLine(0, y, 10, y);
    }

}
