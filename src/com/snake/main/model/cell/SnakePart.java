package com.snake.main.model.cell;

import com.snake.main.model.Directions;

public class SnakePart extends Cell {
    protected Directions direction = null;
    protected int position = -1;

    public SnakePart(int x, int y) {
        super(x, y);
        name = "SnakePart";
    }

    public SnakePart(int x, int y, Directions direction){
        super(x, y);
        isWalkable = false;
        this.direction = direction;
        name = "SnakePart";
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
