package com.snake.data.cell;

import com.snake.data.Cell;

public class Wall extends Cell {
    public Wall(int x, int y) {
        super(x, y);
        setIsWalkable(false);
    }
}
