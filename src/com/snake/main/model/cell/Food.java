package com.snake.main.model.cell;

import com.snake.main.model.Snake;

public abstract class Food extends Cell{

    public Food(int x, int y) {
        super(x, y);
        isWalkable = true;
    }

    public abstract void makeEffect(Snake snake);
}
