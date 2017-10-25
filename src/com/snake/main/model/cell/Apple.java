package com.snake.main.model.cell;

public class Apple extends Cell {

    public Apple(int x, int y) {
        super(x, y);
        isWalkable = true;
        name = "Apple";
    }
}
