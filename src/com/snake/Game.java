package com.snake;

import com.snake.data.Field;
import com.snake.data.Snake;

public class Game {
    private Field field;
    private Snake snake;

    public Game(String fieldFileName){
        field = new Field(fieldFileName);
        snake = new Snake(field.findSnake());
    }
}
