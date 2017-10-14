package com.snake.data.cell;

import com.snake.data.Directions;

public class VirtualSnakePart extends SnakePart {
    public VirtualSnakePart(int x, int y, Directions direction) {
        super(x, y, direction);
        isWalkable = true;
    }
}
