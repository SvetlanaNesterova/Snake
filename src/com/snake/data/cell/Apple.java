package com.snake.data.cell;

import com.snake.data.Cell;

public class Apple extends Cell {

    public Apple(int x, int y) {
        super(x, y);
        setIsWalkable(true);
    }
}
