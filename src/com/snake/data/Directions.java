package com.snake.data;

import java.util.ArrayList;

public enum Directions {
    Up(new Vector(-1, 0)),
    Down(new Vector(1, 0)),
    Left(new Vector(0, -1)),
    Right(new Vector(0, 1));

    private Vector vector;
    public static final Directions[] VALUES = Directions.values();

    Directions(Vector vector){
        this.vector = vector;
    }

    public Vector getVector() {
        return vector;
    }

    public Directions opposite(){
        Directions direction = null;
        switch (this){
            case Up:
                direction = Directions.Down;
                break;
            case Down:
                direction = Directions.Up;
                break;
            case Left:
                direction = Directions.Right;
                break;
            case Right:
                direction = Directions.Left;
                break;
        }
        return direction;
    }
}
