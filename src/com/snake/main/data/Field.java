package com.snake.main.data;

import com.snake.main.data.cell.*;

public class Field {
    private Cell[][] field;
    private int height;
    private int width;

    public Field(Cell[][] field){
        this.field = field;
        width = field.length;
        height = field[0].length;
    }

    public Cell[][] getField(){
        return field;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getNeighbor(Cell cell, Directions direction){
        int x = (cell.getX() + direction.getVector().getX() + width) % width;
        int y = (cell.getY() + direction.getVector().getY() + height) % height;
        return field[x][y];
    }


}
