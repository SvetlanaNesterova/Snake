package com.snake.main.model;

import com.snake.main.model.cell.*;

public class Field {
    private Cell[][] field;
    private int height;
    private int width;
    public boolean hasApple = false;

    public Field(Cell[][] field){
        this.field = field;
        width = field.length;
        height = field[0].length;
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

    public Cell cellAt(int i, int j) {
        return field[i][j];
    }

    public void setCellAt(int i, int j, Cell value) {
        field[i][j] = value;
    }


}
