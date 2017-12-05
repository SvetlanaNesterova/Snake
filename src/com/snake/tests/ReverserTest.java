package com.snake.tests;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;
import com.snake.main.model.Vector;
import com.snake.main.model.cell.Reverser;
import com.snake.main.model.cell.SnakeHead;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;

public class ReverserTest {
    private Game game;

    void putSnakeAndItem() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        game = new Game();
        Reverser Reverser = TestHelper.findReverser(game.getField());
        SnakeHead head = TestHelper.findSnakeHead(game.getField());
        game.getField().removeFood(Reverser);
        Vector dir = head.getDirection().getVector();
        int x = head.getX() + dir.getX();
        int y = head.getY() + dir.getY();
        game.getField().setCellAt(x, y, new Reverser(x, y));
    }

    @Test
    void testReverserAppearsFirstTime() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Game game = new Game();
        Reverser ret = TestHelper.findReverser(game.getField());
        assertNotNull(ret);
    }

    @Test
    void testReverse() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        putSnakeAndItem();
        TestHelper.makeMove(game);
    }

    @Test
    void testReverserAppearsAfterEating() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        putSnakeAndItem();
        TestHelper.makeMove(game);
        Reverser ret = TestHelper.findReverser(game.getField());
        assertNotNull(ret);
    }
}
