package com.snake.main.model.cell;

import com.snake.main.model.Field;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Apple extends Food {
    private int ticks = 0;
    public Apple(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        snake.addPart();
        snake.changeScore(1);
        snake.changeEatenApples(1);
        reincarnate(snake.getField());
    }

    public void fade(Snake snake)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        snake.changeScore(-1);
        Field field = snake.getField();
        field.removeFood(this);
        field.addFood(Apple.class);
    }

    public int getTicks() {
        return ticks;
    }

    public void increaseTicks() {
        ticks++;
    }
}
