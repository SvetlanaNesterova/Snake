package com.snake.data;

public abstract class Cell {
    private int x;
    private int y;
    private boolean isWalkable;

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

    protected void setX(int x){
        this.x = x;
    }

    protected void setY(int y){
        this.y = y;
    }

    protected void setIsWalkable(boolean isWalkable){
        this.isWalkable = isWalkable;
    }

}
