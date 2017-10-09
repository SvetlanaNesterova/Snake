package com.snake;

public abstract class Cell {
    protected int x;
    protected int y;

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    protected void setX(int x){
        this.x = x;
    }

    protected void setY(int y){
        this.y = y;
    }

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
}
