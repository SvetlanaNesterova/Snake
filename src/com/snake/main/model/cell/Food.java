package com.snake.main.model.cell;

import com.snake.main.model.Field;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public abstract class Food extends Cell{

    public Food(int x, int y) {
        super(x, y);
        isWalkable = true;
    }

    public abstract void makeEffect(Snake snake)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException;

    public void reincarnate(Field field)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        field.removeFood(this);
        field.addFood(this.getClass());
    }
}
