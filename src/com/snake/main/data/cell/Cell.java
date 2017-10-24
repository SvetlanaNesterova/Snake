package com.snake.main.data.cell;

public abstract class Cell {
    private int x;
    private int y;
    protected boolean isWalkable;

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

    public boolean isWalkable(){
        return isWalkable;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
