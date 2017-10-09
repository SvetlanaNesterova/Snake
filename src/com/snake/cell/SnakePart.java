package com.snake.cell;

import com.snake.Cell;
import com.snake.Directions;

public class SnakePart extends Cell {
    public Directions direction;

    public SnakePart(int x, int y) {
        super(x, y);
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
}
