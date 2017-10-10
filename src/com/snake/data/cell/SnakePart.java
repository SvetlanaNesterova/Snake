package com.snake.data.cell;

import com.snake.data.Directions;
import com.snake.data.Cell;

public class SnakePart extends Cell {
    private Directions direction;

    public SnakePart(int x, int y) {
        super(x, y);
    }

    public SnakePart(int x, int y, Directions direction){
        super(x, y);
        setIsWalkable(false);
        this.direction = direction;
    }

    public Directions getDirection() {
        return direction;
    }

    protected void setDirection(Directions direction) {
        this.direction = direction;
    }
}
