package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.util.Random;

public class MainScreen extends VBox {

    private final Canvas canvas;
    private final Affine affine;
    private final Simulation simulation;


    public MainScreen() {
        Button button = new Button("step");
        this.canvas = new Canvas(400,400);
        this.getChildren().addAll(button,this.canvas);
        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);
        int height = 10;
        int width = 10;
        this.simulation = new Simulation(width, height);

        initializeRandomBoard(width, height, simulation);
    }

    private static void initializeRandomBoard(int width, int height, Simulation simulation) {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int randomCellWidth = rand.nextInt(width);
            int randomCellHeight = rand.nextInt(height);
            simulation.setToAlive(randomCellWidth,randomCellHeight);
        }
    }

    public void draw() {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);

        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0,0,400,400);

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
        for (int x = 0; x <= this.simulation.width; x++) {
            graphicsContext.strokeLine(x,0,x,10);
        }
        for (int y = 0; y <= this.simulation.height; y++) {
            graphicsContext.strokeLine(0, y, 10, y);
        }

    }

}
