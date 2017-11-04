package com.snake.main;

import com.snake.main.model.Directions;
import com.snake.main.model.Field;
import com.snake.main.model.Snake;
import com.snake.main.model.cell.*;

import java.util.Random;

public class Game {
    private Field field;
    private Snake snake;
    private Apple apple;
    private boolean isOver;
    private int score;
    private int ticks;
    public final static int TICKS_TO_ROT = 25;

    public Game(){
        createNewLevel();
    }

    public void makeStep(){
        snake.makeMove();
        isOver = snake.isDead();
        if (!field.hasApple) {
            addApple();
            score++;
            ticks = 0;
        }
        if (ticks > TICKS_TO_ROT) {
            ticks = 0;
            removeApple();
            addApple();
        }
        ticks++;
    }

    private void addApple() {
        Random random = new Random();
        int x = 0, y = 0;
        while (!(field.cellAt(x, y) instanceof Empty)) {
            x = random.nextInt(field.getWidth());
            y = random.nextInt(field.getHeight());
        }
        field.setCellAt(x, y, new Apple(x, y));
        apple = (Apple) field.cellAt(x, y);
        field.hasApple = true;
    }

    private void removeApple() {
        int x = apple.getX();
        int y = apple.getY();
        field.setCellAt(x, y, new Empty(x, y));
        score--;
    }

    public void createNewLevel() {
        int gameSize = 20;
        Cell[][] cells = new Cell[gameSize][gameSize];
        for (int i=0; i<gameSize; i++){
            for (int j=0; j<gameSize; j++) {
                cells[i][j] = new Empty(i, j);
                if (i == 0 || j == 0 || i == gameSize - 1 || j == gameSize - 1)
                    cells[i][j] = new Wall(i, j);
            }
        }
        Random random = new Random();
        int x = 0, y = 0;
        while (!(cells[x][y] instanceof Empty)) {
            x = random.nextInt(gameSize - gameSize / 2) + gameSize / 4;
            y = random.nextInt(gameSize - gameSize / 2) + gameSize / 4;
        }
        SnakeHead head = new SnakeHead(x, y);
        head.setDirection(Directions.random());
        cells[x][y] = head;
        int dx = head.getDirection().getVector().getX();
        int dy = head.getDirection().getVector().getY();
        cells[x+dx][y+dy] = new SnakePart(x+dx, y+dy);
        cells[x+2*dx][y+2*dy] = new SnakePart(x+2*dx, y+2*dy);
        field = new Field(cells);
        snake = new Snake(field);
        isOver = false;
        score = 0;
        ticks = 0;
        addApple();
    }

    public Field getField() {
        return field;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getTicks() { return ticks; }

    public boolean isOver() {
        return isOver;
    }

    public int getScore() { return score; }
}
