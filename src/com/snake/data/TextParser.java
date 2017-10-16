package com.snake.data;

import com.snake.data.cell.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class TextParser {

    private HashMap<Character, FunctionTwoArgs<Integer, Integer, Cell>> fromSymToCell;

    public TextParser(){
        fromSymToCell = new HashMap<>();
        fromSymToCell.put('#', Wall::new);
        fromSymToCell.put('~', Empty::new);
        fromSymToCell.put('$', SnakePart::new);
        fromSymToCell.put('S', SnakeHead::new);
        fromSymToCell.put('a', Apple::new);
    }

    public interface FunctionTwoArgs<T1, T2, R>{
        R apply(T1 t1, T2 t2);
    }

    public Cell[][] parseTextField(String filename) throws Exception{
        ArrayList<String> fileStrings = readFile(filename);
        Cell[][] field = new Cell[fileStrings.get(0).length()][];
        for (int i=0; i<fileStrings.get(0).length(); i++){
            field[i] = new Cell[fileStrings.size()];
            for (int j=0; j<fileStrings.size(); j++)
                field[i][j] = fromSymToCell.get(fileStrings.get(j).charAt(i)).apply(i, j);
        }
        return field;
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
