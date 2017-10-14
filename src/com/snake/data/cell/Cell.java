package com.snake.data.cell;

public abstract class Cell {
    private int x;
    private int y;
    protected static boolean isWalkable;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        isWalkable = false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean getIsWalkable(){
        return isWalkable;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
