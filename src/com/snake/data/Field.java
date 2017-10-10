package com.snake.data;

import com.snake.data.cell.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.ArrayList;

public class Field {
    private static Cell[][] field;

    public Field(String filename){
        try {
            field = parseTextField(filename);
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
            for (int j=0; j<field[i].length; j++){
                if (field[i][j] instanceof SnakeHead)
                    return (SnakeHead)field[i][j];
            }
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
            newCell.setX(newX);
            newCell.setY(newY);
            field[x][y] = new Empty(x, y);
        }else{
            throw new Exception("The cell" + x + " " + y + " " + field[x][y].getClass() + " cannot be moved");
        }
    }

    private Cell[][] parseTextField(String filename) throws Exception{
        ArrayList<String> textField = readFile(filename);
        Cell[][] field = new Cell[textField.size()][];
        for (int i=0; i<textField.size(); i++){
            String currentString = textField.get(i);
            for (int j=0; j<currentString.length(); j++)
                field[i][j] = getCellFromSymbol(currentString.charAt(j), i, j);
        }
        return field;
    }

    private Cell getCellFromSymbol(char symbol, int i, int j) {
        switch (symbol) {
            case '#':
                return new Wall(i, j);
            case '~':
                return new Empty(i, j);
            case '$':
                return new SnakePart(i, j);
            case 'S':
                return new SnakeHead(i, j);
            case 'a':
                return new Apple(i, j);
            default:
                return null;
        }
    }

    private static ArrayList<String> readFile(String filename) throws Exception{
        exists(filename);
        File file = new File(filename);
        ArrayList<String> fileStrings = new ArrayList<>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try{
                String s;
                while ((s = in.readLine()) != null)
                    fileStrings.add(s);
            } finally {
                in.close();
            }
        } catch (IOException e){
            throw new RuntimeException();
        }
        return fileStrings;
    }

    private static void exists(String filename) throws FileNotFoundException{
        File file = new File(filename);
        if (!file.exists())
            throw new FileNotFoundException(file.getName());
    }

}
