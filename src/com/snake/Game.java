package com.snake;

import com.snake.data.Field;
import com.snake.data.Snake;
import com.snake.data.cell.Cell;

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
