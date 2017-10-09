package com.snake;

import com.snake.cell.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Field {
    public HashMap<Point, Cell> field;

    public Field(String filename){
        try {
            field = parseTextField(filename);
        }catch (Exception e){
            System.out.println("Incorrect filename");
        }
    }

    private HashMap<Point, Cell> parseTextField(String filename) throws Exception{
        ArrayList<String> textField = readFile(filename);
        HashMap<Point, Cell> field = new HashMap<Point, Cell>();
        for (int i=0; i<textField.size(); i++){
            String currentString = textField.get(i);
            for (int j=0; j<currentString.length(); j++)
                field.put(new Point(i, j), getCellFromSymbol(currentString.charAt(j), i, j));
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
