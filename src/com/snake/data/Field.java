package com.snake.data;

import com.snake.data.cell.*;

import java.util.ArrayList;

public class Field {
    private static Cell[][] field;
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

    protected void setField(Cell[][] field){
        this.field = field;
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
