package com.snake.main.data;

import com.snake.main.data.cell.Cell;
import com.snake.main.data.cell.CellFactory;

import java.io.*;
import java.util.ArrayList;

public class TextParser {

    public Cell[][] parseTextField(String filename) throws Exception{
        ArrayList<String> fileStrings = readFile(filename);
        Cell[][] field = new Cell[fileStrings.get(0).length()][];
        for (int i=0; i<fileStrings.get(0).length(); i++){
            field[i] = new Cell[fileStrings.size()];
            for (int j=0; j<fileStrings.size(); j++) {
                char symbol = fileStrings.get(j).charAt(i);
                field[i][j] = CellFactory.fromMapSymbol(symbol, i, j);
            }
        }
        return field;
    }

    private static ArrayList<String> readFile(String filename) throws FileNotFoundException{
        File file = new File(filename);
        if (!file.exists())
            throw new FileNotFoundException(file.getName());
        ArrayList<String> fileStrings = new ArrayList<>();
        try{
            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                String s;
                while ((s = in.readLine()) != null)
                    fileStrings.add(s);
            }
        } catch (IOException e){
            throw new RuntimeException();
        }
        return fileStrings;
    }
}
