package com.snake.main.data.cell;

import com.snake.main.data.Directions;

public class VirtualSnakePart extends SnakePart {
    public VirtualSnakePart(int x, int y, Directions direction) {
        super(x, y, direction);
        isWalkable = true;
    }
}
