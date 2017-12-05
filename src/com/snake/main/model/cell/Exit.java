package com.snake.main.model.cell;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Exit extends Cell {
    private Game game;

    public Exit(int x, int y, Game game) {
        super(x, y);
        this.game = game;
    }

    public void use(Snake snake)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        game.restartWithEntrance(snake);
    }
}
