package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Field field;
    private Snake snake;
    private boolean isOver;
    public final static int TICKS_TO_ROT = 100;

    public Game() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        try {
            createNewLevel();
        }
        catch (Exception e){
            throw e;
        }
    }

    public void makeStep() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        snake.makeMove();
        isOver = snake.isDead();
        field.apple.increaseTicks();
        if (field.apple.getTicks() >= TICKS_TO_ROT) {
            field.apple.fade(snake);
        }
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

    public int getTicks(){
        return field.apple.getTicks();
    }

    public int getScore() { return snake.getScore(); }

    public int getEatenApples() { return snake.getEatenApples(); }
}
