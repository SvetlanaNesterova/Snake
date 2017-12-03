package com.snake.main.model.cell;

import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Retarder extends Food{
    public Retarder(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        reincarnate(snake.getField());
    }
}
