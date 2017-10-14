package com.snake.data.cell;

public class Empty extends Cell {
    public Empty(int x, int y) {
        super(x, y);
        setIsWalkable(true);
    }
}
