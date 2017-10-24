package com.snake.main;

import com.snake.main.data.Field;
import com.snake.main.data.Snake;
import com.snake.main.data.cell.Cell;

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
    }

    public boolean isOver() {
        return isOver;
    }
}
