package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainScreen extends VBox {

    private Canvas canvas;
    private Affine affine;
    private Simulation simulation;
    private int drawMode = 1;

    public Simulation getSimulation() {
        return simulation;
    }

    public MainScreen() {

        int simulationWidth = 10, simulationHeight = 10;
        double canvasWidth = 400, canvasHeight = 400;

        Button generateRandomButton = new Button("generate");
        generateRandomButton.setOnAction(actionEvent -> {
            simulation.initializeRandomizedBoard();
            draw();
        });

        this.canvas = new Canvas(canvasWidth,canvasHeight);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.setOnKeyPressed(this::onKeyPressed);

        // TODO Toolbar causes NullPointerException
        Toolbar toolbar = new Toolbar(this);

        this.getChildren().addAll(toolbar,this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(canvasWidth / (double) simulationWidth, canvasHeight / (double) simulationHeight);

        this.simulation = new Simulation(simulationWidth,simulationHeight);
        //this.simulation.initializeRandomBoard();
    }

    private void handleDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        try {
            Point2D simCoordinates = this.affine.inverseTransform(mouseX, mouseY);

            int simX = (int) simCoordinates.getX();
            int simY = (int) simCoordinates.getY();

            this.simulation.setState(simX, simY, drawMode);
            draw();

        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.D) {
            this.drawMode = 1;
        } else if(keyEvent.getCode() == KeyCode.E) {
            this.drawMode = 0;
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
