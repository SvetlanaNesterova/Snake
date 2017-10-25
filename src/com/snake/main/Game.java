package com.snake.main;

import com.snake.main.model.Field;
import com.snake.main.model.Snake;
import com.snake.main.model.cell.*;

import java.util.Random;

public class Game {
    private Field field;
    private Snake snake;
    private boolean isOver;

    public Game(Cell[][] field){
        this.field = new Field(field);
        snake = new Snake(this.field);
    }

    public void makeStep(){
        snake.makeMove();
        isOver = snake.isDead();
        if (!field.hasApple)
            addApple();
    }

    private void addApple() {
        Random random = new Random();
        int x = 0, y = 0;
        while (!(field.cellAt(x, y) instanceof Empty)) {
            x = random.nextInt(field.getWidth());
            y = random.nextInt(field.getHeight());
        }
        field.setCellAt(x, y, new Apple(x, y));
        field.hasApple = true;
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
}
