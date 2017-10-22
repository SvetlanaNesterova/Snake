package com.snake;

import com.snake.data.Field;
import com.snake.data.TextParser;
import com.snake.data.cell.Cell;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import javax.swing.*;

import static javax.swing.GroupLayout.*;

public class GameForm extends Application {

    private Cell[][] field;
    private Stage currentStage;
    private final static int CELL_SIZE = 30;
    private Stage primary;
    private Scene mainScene;
    private GridPane mainPane;
    private Canvas fieldCanvas, background;
    private GraphicsContext fieldContext, backgroundContext;
    private AnimationTimer gameThread;

    public static void main(String[] args) {
        launch(args);
    }

    private void createGame(){
        Cell[][] field;
        TextParser tp = new TextParser();
        try {
            field = tp.parseTextField("Level.txt");
        }catch (Exception e){
            System.out.println("Incorrect filename");
            return;
        }
        Game game = new Game(field);
        this.field = field;
    }

    @Override
    public void start(Stage primaryStage) {
        currentStage = primaryStage;
        createGame();
        int width = field[0].length * CELL_SIZE;
        int height = field.length * CELL_SIZE;
        this.primary = primaryStage;

        fieldCanvas = new Canvas(width, height);
        background = new Canvas(width, height);
        mainPane = new GridPane();
        fieldContext = fieldCanvas.getGraphicsContext2D();
        backgroundContext = background.getGraphicsContext2D();
        mainScene = new Scene(mainPane, width, height);
        gameThread = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };
        primaryStage.setTitle("Snake");
        primaryStage.getIcons().add(new Image("\\com\\snake\\assets\\snake.png"));
        primaryStage.show();
    }

    private void drawField(){

    }
}
