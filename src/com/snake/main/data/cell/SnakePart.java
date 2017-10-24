package com.snake.main.data.cell;

import com.snake.main.data.Directions;

public class SnakePart extends Cell {
    private Directions direction = null;

    public SnakePart(int x, int y) {
        super(x, y);
    }

    public SnakePart(int x, int y, Directions direction){
        super(x, y);
        isWalkable = false;
        this.direction = direction;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
}
