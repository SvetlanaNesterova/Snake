package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.util.ArrayList;
import java.util.Random;

public class FieldGenerator {
    private static final int SNAKE_SPACE = 4;
    private static final int ROOM_SIZE = 5;
    private static Cell[][] cells;
    private static int width;
    private static int height;

    public static Field generateMaze() {
        width = 40;
        height = 20;
        createMaze();
        createSnake();
        return new Field(cells);
    }

    public static Field generateEmpty() {
        width = 20;
        height = 20;
        createEmptyCells();
        createSnake();
        return new Field(cells);
    }

    private static void createMaze(){
        cells = new Cell[width][height];
        int rooms_width_count = width/ROOM_SIZE;
        int rooms_height_count = height/ROOM_SIZE;
        for (int x = 0; x < rooms_width_count; x++)
            for (int y = 0; y < rooms_height_count; y++)
                createRoom(x*ROOM_SIZE, y*ROOM_SIZE);
    }

    private static void createRoom(int room_up_x, int room_left_y){
        int room_down_x = room_up_x + ROOM_SIZE - 1;
        int room_right_y = room_left_y + ROOM_SIZE - 1;
        ArrayList<Wall> walls = new ArrayList<>();
        for (int x = room_up_x; x <= room_down_x; x++)
            for (int y = room_left_y; y <= room_right_y; y++)
                if (x != room_up_x && x != room_down_x && y != room_left_y && y != room_right_y)
                    cells[x][y] = new Empty(x, y);
                else {
                    cells[x][y] = new Wall(x, y);
                    walls.add((Wall)cells[x][y]);
                }
        createDoors(walls);
    }

    private static void createDoors(ArrayList<Wall> walls){

    }

    private static void createEmptyCells() {
        cells = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Empty(i, j);
            }
        }
    }

    private static void createSnake() {
        Random random = new Random();
        int x = 0, y = 0;
        while (!(cells[x][y] instanceof Empty)) {
            x = random.nextInt(width - width / 2) + width / 4;
            y = random.nextInt(height - height / 2) + height / 4;
        }
        ArrayList<Cell> horizontalSpacesStarts = getSpacesStarts(Directions.Right);
        ArrayList<Cell> verticalSpacesStarts = getSpacesStarts(Directions.Up);

        putSnake(x, y, direction);
    }

    private static ArrayList<Cell> getSpacesStarts(Directions direction) {
        ArrayList<Cell> starts = new ArrayList<Cell>();
        int dx = direction.getVector().getX();
        int dy = direction.getVector().getY();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean isEmptySpace = false;
                int x = i;
                int y = j;
                for (int spaceCount = 0; spaceCount < SNAKE_SPACE; spaceCount++) {
                    x += dx;
                    y += dy;
                    if (cells[x][y] instanceof Empty) {

                    }
                }
            }
        }
    }

    private static void putSnake(int headX, int headY, Directions direction) {
        SnakeHead head = new SnakeHead(headX, headY);
        head.setDirection(direction);
        cells[headX][headY] = head;
        int dx = head.getDirection().getVector().getX();
        int dy = head.getDirection().getVector().getY();
        cells[headX + dx][headY + dy] = new SnakePart(headX + dx, headY + dy);
        cells[headX + 2 * dx][headY + 2 * dy] = new SnakePart(headX + 2 * dx, headY + 2 * dy);
    }
}