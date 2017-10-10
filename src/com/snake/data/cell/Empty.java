package com.snake.data.cell;

import com.snake.data.Cell;

public class Empty extends Cell {
    public Empty(int x, int y) {
        super(x, y);
        setIsWalkable(true);
    }
}
