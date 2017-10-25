package com.snake.main.model.cell;

import com.snake.main.model.Directions;

public class VirtualSnakePart extends SnakePart {
    public VirtualSnakePart(int x, int y, Directions direction) {
        super(x, y, direction);
        isWalkable = true;
        name = "VirtualSnakePart";
    }
}
