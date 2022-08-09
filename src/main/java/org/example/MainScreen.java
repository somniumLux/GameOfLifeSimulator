package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainScreen extends VBox {

    private Canvas canvas;
    private Affine affine;
    public Simulation simulation;


    public MainScreen() {
        int height = 10;
        int width = 10;
        this.simulation = new Simulation(width, height);

        Button stepButton = new Button("step");
        stepButton.setOnAction(actionEvent -> {
            simulation.step();
            draw();
        });

        Button generateBoardButton = new Button("generate");
        generateBoardButton.setOnAction(actionEvent -> {
            simulation.initializeRandomBoard(width, height);
            draw();

            System.out.println("---------------------------------");
            for (int x = 0; x < width; x++) {
                System.out.print("[ ");
                for (int y = 0; y < height; y++) {
                    System.out.print(" " + simulation.board[x][y] + ", ");
                }
                System.out.println(" ]");
            }
        });

        this.canvas = new Canvas(400,400);
        this.getChildren().addAll(stepButton,generateBoardButton,this.canvas);
        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        simulation.initializeRandomBoard(width, height);
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
