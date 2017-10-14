package com.snake.data;

import com.snake.data.cell.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TextParser {

    private HashMap<Character, Cell> fromSymToCell;
    private FunctionThreeArgs<Cell, Integer, Integer, Cell> convert;

    public TextParser(){
        fromSymToCell = new HashMap<>();
        fromSymToCell.put('#', new Wall(0, 0));
        fromSymToCell.put('~', new Empty(0, 0));
        fromSymToCell.put('$', new SnakePart(0, 0));
        fromSymToCell.put('S', new SnakeHead(0, 0));
        fromSymToCell.put('a', new Apple(0, 0));
        convert = (c, x, y) -> {
            c.setX(x);
            c.setY(y);
            return c;
        };
    }

    public interface FunctionThreeArgs<T1, T2, T3, R>{
        R apply(T1 t1, T2 t2, T3 t3);
    }

    public Cell[][] parseTextField(String filename) throws Exception{
        ArrayList<String> textField = readFile(filename);
        Cell[][] field = new Cell[textField.size()][];
        for (int i=0; i<textField.size(); i++){
            String currentString = textField.get(i);
            field[i] = new Cell[currentString.length()];
            for (int j=0; j<currentString.length(); j++)
                field[i][j] = getCellFromSymbol(currentString.charAt(j), i, j);
        }
        return field;
    }

    private Cell getCellFromSymbol(char symbol, int i, int j) {
        return convert.apply(fromSymToCell.get(symbol), i, j);
    }

    private static ArrayList<String> readFile(String filename) throws FileNotFoundException{
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

    private static void exists(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists())
            throw new FileNotFoundException(file.getName());
    }
}
