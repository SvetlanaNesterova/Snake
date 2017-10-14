package com.snake.data;

import com.snake.data.cell.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.ArrayList;

public class Field {
    private static Cell[][] field;

    public Field(String filename){
        TextParser tp = new TextParser();
        try {
            field = tp.parseTextField(filename);
        }catch (Exception e){
            System.out.println("Incorrect filename");
        }
    }

    public Cell[][] getField(){
        return field;
    }

    protected void setField(Cell[][] field){
        this.field = field;
    }

    public SnakeHead findSnakeHead(){
        for (int i=0; i<=field.length; i++){
            for (int j=0; j<field[i].length; j++)
                if (field[i][j] instanceof SnakeHead)
                    return (SnakeHead)field[i][j];
        }
        return null;
    }

    public ArrayList<SnakePart> findSnake(){
        throw new NotImplementedException();
    }

    public static void moveCell(int x, int y, Directions direction) throws Exception {
        Cell newCell;
        switch (direction){
            case Up:
               newCell = field[x][y-1];
               break;
            case Down:
                newCell = field[x][y+1];
                break;
            case Right:
                newCell = field[x+1][y];
                break;
            case Left:
                newCell = field[x-1][y];
                break;
            default:
                newCell = new Empty(0, 0);
        }
        if (newCell.getIsWalkable()){
            int newX = newCell.getX();
            int newY = newCell.getY();
            newCell = field[x][y];
            field[x][y] = new Empty(x, y);
        }else{
            throw new Exception("The cell" + x + " " + y + " " + field[x][y].getClass() + " cannot be moved");
        }
    }

}
