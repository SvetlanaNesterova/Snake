package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

public class Snake {
    private ArrayList<SnakePart> snakeParts;
    private SnakeHead snakeHead;
    private Field field;
    private boolean isDead;
    private int score = 0;
    private int eatenApples = 0;
    private SnakeSpeed speed;
    private int timeToNormal;
    private int ticksMod6 = 0;

    public Snake(Field field) {
        this.field = field;
        this.snakeParts = findSnake();
        this.snakeHead = (SnakeHead)this.snakeParts.get(0);
    }

    public boolean isDead(){
        return isDead;
    }

    public int getLength() {
        return snakeParts.size();
    }

    public void changeHeadDirection(Directions direction){
        snakeHead.setDirection(direction);
    }

    public Directions getSnakeDirection() {
        return snakeHead.getDirection();
    }

    public Field getField(){
        return field;
    }

    public enum SnakeSpeed {
        Slow, Normal, Fast;
    }

    public void setSpeed(SnakeSpeed speed, int time) {
        timeToNormal = time;
        this.speed = speed;
    }

    private SnakeHead findSnakeHead(){
        for (int i=0; i<field.getWidth(); i++){
            for (int j=0; j<field.getHeight(); j++)
                if (field.cellAt(i, j) instanceof SnakeHead)
                    return (SnakeHead) field.cellAt(i, j);
        }
        return null;
    }

    private SnakePart findNextPart(SnakePart part){
        for (Directions direction : Directions.values()){
            Cell cell = field.getNeighbor(part, direction);
            if (cell instanceof SnakePart && ((SnakePart) cell).getDirection() == null){
                ((SnakePart) cell).setDirection(direction.opposite());
                return (SnakePart) cell;
            }
        }
        return null;
    }

    private ArrayList<SnakePart> findSnake(){
        int position = 0;
        ArrayList<SnakePart> snake = new ArrayList<>();
        SnakeHead head = findSnakeHead();
        SnakePart currentPart = head;
        assert head != null;
        head.setDirection(Directions.Right);
        head.setPosition(position++);
        for (Directions direction : Directions.values()){
            Cell cell = field.getNeighbor(head, direction);
            if (cell instanceof SnakePart)
                head.setDirection(direction.opposite());
        }
        while (currentPart != null){
            snake.add(currentPart);
            currentPart.setPosition(position++);
            currentPart = findNextPart(currentPart);
        }
        return snake;
    }

    public void makeMove() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        if (timeToNormal > 0)
            timeToNormal--;
        else
            speed = SnakeSpeed.Normal;

        ticksMod6++;
        ticksMod6 %= 6;
        if (speed == SnakeSpeed.Slow && ticksMod6 % 6 == 0 ||
                speed == SnakeSpeed.Normal && ticksMod6 % 3 == 0 ||
                speed == SnakeSpeed.Fast && ticksMod6 % 2 == 0) {
            move();
        }
    }

    private void move()
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        tryAddVirtualPart();
        for (int i=0; i<this.getLength(); i++)
            moveSnakePart(snakeParts.get(i), i);
    }

    private void moveSnakePart(SnakePart part, int index)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Directions direction = part.getDirection();
        Cell targetCell = field.getNeighbor(part, direction);
        if (!targetCell.isWalkable())
            isDead = true;
        else {
            if (targetCell instanceof Food) {
                ((Food) targetCell).makeEffect(this);
            }
            field.setCellAt(part.getX(), part.getY(), new Empty(part.getX(), part.getY()));
            part.setX(targetCell.getX());
            part.setY(targetCell.getY());
            if (index!=0)
                part.setDirection(getNeighborSide(part, snakeParts.get(index - 1)));
            field.setCellAt(targetCell.getX(), targetCell.getY(), part);
        }
    }

    private Directions getNeighborSide(SnakePart you, SnakePart neighbor){
        int dx = neighbor.getX()-you.getX();
        int dy = neighbor.getY()-you.getY();
        if (dx != 0) {
            if (Math.abs(dx) > 1)
                dx = dx*(-1);
            dx = dx / Math.abs(dx);
        }
        if (dy != 0) {
            if (Math.abs(dy)>1)
                dy = dy*(-1);
            dy = dy / Math.abs(dy);
        }
        return new Vector(dx, dy).toDirection();
    }

    public void addPart(){
        SnakePart virtual = snakeParts.get(this.getLength() - 1);
        int x = virtual.getX();
        int y = virtual.getY();
        SnakePart newPart = new SnakePart(x, y, virtual.getDirection());
        field.setCellAt(x, y, newPart);
        snakeParts.set(this.getLength() - 1, newPart);
        newPart.setPosition(this.getLength() - 1);
        tryAddVirtualPart();
    }

    private void tryAddVirtualPart(){
        SnakePart last = snakeParts.get(this.getLength() - 1);
        if (last instanceof VirtualSnakePart)
            return;
        int x = (last.getX() + last.getDirection().opposite().getVector().getX()+field.getWidth())%field.getWidth();
        int y = (last.getY() + last.getDirection().opposite().getVector().getY()+field.getHeight())%field.getHeight();
        if (Objects.equals(field.cellAt(x, y).getClass().getSimpleName(), "Empty")) {
            SnakePart newPart = new VirtualSnakePart(x, y, last.getDirection());
            snakeParts.add(newPart);
        }
    }

    public void reverse(){
        SnakePart tail = this.snakeParts.get(this.snakeParts.size()-1);
        field.setCellAt(snakeHead.getX(), snakeHead.getY(), new SnakePart(snakeHead.getX(), snakeHead.getY()));
        field.setCellAt(tail.getPosition(), tail.getY(), new SnakeHead(tail.getX(), tail.getY()));
        snakeParts = findSnake();
        snakeHead = (SnakeHead)this.snakeParts.get(0);
    }

    public void reduce(){

    }

    public int getScore(){
        return score;
    }

    public void changeScore(int delta) {
        score += delta;
    }

    public int getEatenApples(){
        return eatenApples;
    }

    public void changeEatenApples(int delta) {
        eatenApples += delta;
    }
}

