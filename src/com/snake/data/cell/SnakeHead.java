package com.snake.data.cell;

import com.snake.data.Directions;

public class SnakeHead extends SnakePart {

    public SnakeHead(int x, int y) {
        super(x, y);
    }

    public SnakeHead(int x, int y, Directions direction){
        super(x, y, direction);
    }
}
