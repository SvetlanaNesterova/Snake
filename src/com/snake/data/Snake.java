package com.snake.data;

import com.snake.data.cell.SnakeHead;
import com.snake.data.cell.SnakePart;

import java.util.ArrayList;

public class Snake {
    private ArrayList<SnakePart> snakeParts;
    private SnakeHead snakeHead;

    public Snake(ArrayList<SnakePart> snakeParts) {
        this.snakeParts = snakeParts;
        snakeHead = (SnakeHead)snakeParts.get(0);
    }

    public ArrayList<SnakePart> getSnakeParts() {
        return snakeParts;
    }

    protected void setSnakeParts(ArrayList<SnakePart> snakeParts){
        this.snakeParts = snakeParts;
    }

    public SnakeHead getSnakeHead() {
        return snakeHead;
    }

    protected void setSnakeHead(SnakeHead snakeHead){
        this.snakeHead = snakeHead;
    }

    public void move() throws Exception {
        SnakePart oldTail = snakeParts.get(snakeParts.size()-1);
        for (int i=snakeParts.size()-1; i>0; i--) {
            SnakePart currentSnakePart = snakeParts.get(i);
            Field.moveCell(currentSnakePart.getX(), currentSnakePart.getY(), currentSnakePart.getDirection());
            snakeParts.set(i, (SnakePart)snakeParts.get(i - 1));
        }
        SnakeHead oldHead = (SnakeHead)snakeParts.get(0);
        //проверять на то, стоит ли что-то на пути змеи
        switch (snakeParts.get(0).getDirection()){
            case Up:
                snakeParts.set(0, new SnakeHead(oldHead.getX(),  oldHead.getY()-1, Directions.Up));
                break;
            case Down:
                snakeParts.set(0, new SnakeHead(oldHead.getX(),  oldHead.getY()+1, Directions.Down));
                break;
            case Right:
                snakeParts.set(0, new SnakeHead(oldHead.getX()+1,  oldHead.getY(), Directions.Right));
                break;
            case Left:
                snakeParts.set(0, new SnakeHead(oldHead.getX()-1,  oldHead.getY(), Directions.Left));
                break;

        }
    }
}
