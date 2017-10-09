package com.snake;

import com.snake.cell.SnakeHead;
import com.snake.cell.SnakePart;

import java.util.List;

public class Snake {
    public List<SnakePart> snakeParts;
    public SnakeHead snakeHead;

    public Snake(List<SnakePart> snakeParts) {
        this.snakeParts = snakeParts;
        snakeHead = (SnakeHead)snakeParts.get(0);
    }
}
