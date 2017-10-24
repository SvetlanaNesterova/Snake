package com.snake.main.data.cell;

import com.snake.main.data.Directions;

public class SnakeHead extends SnakePart {

    public SnakeHead(int x, int y) {
        super(x, y);
    }

    public SnakeHead(int x, int y, Directions direction){
        super(x, y, direction);
    }
}
