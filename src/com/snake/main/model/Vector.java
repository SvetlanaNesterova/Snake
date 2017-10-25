package com.snake.main.model;

public final class Vector {
    private final int x;
    private final int y;

    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Directions toDirection(){
        if (this.x == 0) {
            if (this.y == 1)
                return Directions.Down;
            if (this.y == -1)
                return Directions.Up;
        }
        if (this.y == 0) {
            if (this.x == 1)
                return Directions.Right;
            if (this.x == -1)
                return Directions.Left;
        }
        return null;
    }

}
