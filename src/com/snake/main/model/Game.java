package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class Game implements Serializable {
    private Field field;
    private Snake snake;
    private boolean isOver;
    public final static int APPLES_TO_NEXT_LEVEL = 100;

    private static Game instance;

    private Game() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        createNewLevel();
    }

    public static Game getInstance() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        if (instance==null){
            instance = new Game();
        }
        return instance;
    }

    public static Game getNewInstance() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        instance = new Game();
        return instance;
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        instance = this;
    }
     public void makeStep() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        snake.makeMove();
        isOver = snake.isDead();
        field.apple.increaseTicks();
    }

    public void createNewLevel()
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        field = FieldGenerator.getInstance().generateMaze();
        snake = new Snake(field);
        addFoods();
    }

    private void addFoods()
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        field.addFood(Apple.class);
        field.addFood(Accelerator.class);
        field.addFood(Retarder.class);
        field.addFood(Reverser.class);
    }

    public Field getField() {
        return field;
    }

    public Snake getSnake() {
        return snake;
    }

    public boolean isOver() {
        return isOver;
    }

    public int getTicks() {
        return field.apple.getTicks();
    }

    public int getScore() {
        return snake.getScore();
    }

    public int getEatenApples() {
        return snake.getEatenApples();
    }

}
