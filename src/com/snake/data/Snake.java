package com.snake.data;

import com.snake.data.cell.*;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;

public class Snake {
    private ArrayList<SnakePart> snakeParts;
    private Field field;
    private boolean isDead;

    public Snake(Field field) {
        this.field = field;
        this.snakeParts = findSnake();
    }

    public ArrayList<SnakePart> getSnakeParts() {
        return snakeParts;
    }

    protected void setSnakeParts(ArrayList<SnakePart> snakeParts){
        this.snakeParts = snakeParts;
    }

    public boolean isDead(){
        return isDead;
    }

    public void makeMove(){
        for (SnakePart snakePart : snakeParts){
            moveSnakePart(snakePart);
        }
        tryAddVirtualPart();
    }

    private SnakeHead findSnakeHead(){
        for (int i=0; i<=field.getWidth(); i++){
            for (int j=0; j<field.getHeight(); j++)
                if (field.getField()[i][j] instanceof SnakeHead)
                    return (SnakeHead) field.getField()[i][j];
        }
        return null;
    }

    private SnakePart findNextPart(SnakePart part){
        for (Directions direction : Directions.VALUES){
            Cell cell = field.getNeighbor(part, direction);
            if (cell instanceof SnakePart && ((SnakePart) cell).getDirection() == null){
                ((SnakePart) cell).setDirection(direction.opposite());
                return (SnakePart) cell;
            }
        }
        return null;
    }

    private ArrayList<SnakePart> findSnake(){
        ArrayList<SnakePart> snake = new ArrayList<>();
        SnakeHead head = findSnakeHead();
        SnakePart currentPart = head;
        for (Directions direction : Directions.VALUES){
            Cell cell = field.getNeighbor(head, direction);
            if (cell instanceof SnakePart)
                head.setDirection(direction.opposite());
        }
        while (currentPart != null){
            snake.add(currentPart);
            currentPart = findNextPart(currentPart);
        }
        return snake;
    }

    private void moveSnakePart(SnakePart part) {
        Directions direction = part.getDirection();
        Cell targetCell = field.getNeighbor(part, direction);
        if (!targetCell.isWalkable())
            isDead = true;
        else {
            if (targetCell instanceof Apple)
                addPart();
            field.getField()[part.getX()][part.getY()] = new Empty(part.getX(), part.getY());
            part.setX(targetCell.getX());
            part.setY(targetCell.getY());
            field.getField()[targetCell.getX()][targetCell.getY()] = part;
        }
    }

    private void addPart(){
        SnakePart virtual = snakeParts.get(snakeParts.size() - 1);
        int x = virtual.getX();
        int y = virtual.getY();
        field.getField()[x][y] = new SnakePart(x, y, virtual.getDirection());
    }

    private void tryAddVirtualPart(){
        SnakePart last = snakeParts.get(snakeParts.size() - 1);
        if (last instanceof VirtualSnakePart)
            return;
        SnakePart newPart = new VirtualSnakePart(last.getX() + last.getDirection().opposite().getVector().getX(),
                last.getY() + last.getDirection().opposite().getVector().getY(), last.getDirection());
        snakeParts.add(newPart);
        field.getField()[newPart.getX()][newPart.getY()] = newPart;
    }
}
