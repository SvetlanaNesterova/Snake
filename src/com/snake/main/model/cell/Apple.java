package com.snake.main.model.cell;

import com.snake.main.model.Snake;

public class Apple extends Food {

    public Apple(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake) {
        snake.addPart();
        snake.getField().hasApple = false;
    }

}
