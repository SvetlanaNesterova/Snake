package com.snake.main;

import com.snake.main.data.TextParser;
import com.snake.main.data.cell.Cell;

public class Main {
    public static void main(String[] args) {
        Cell[][] field;
        TextParser tp = new TextParser();
        try {
            field = tp.parseTextField("C:\\Users\\finkrer\\IdeaProjects\\Snake\\Level.txt");
        }catch (Exception e){
            System.out.println("Incorrect filename");
            return;
        }
        Game game = new Game(field);
    }
}
