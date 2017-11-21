package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.util.ArrayList;
import java.util.Random;

public class FieldGenerator {
    private static final int SNAKE_SPACE = 4;
    private static final int ROOM_SIZE = 6;
    private static Cell[][] cells;
    private static int width;
    private static int height;

    public static Field generateMaze() {
        width = 36;
        height = 18;
        createEmptyCells();
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
        int rooms_width_count = width/ROOM_SIZE;
        int rooms_height_count = height/ROOM_SIZE;
        for (int x = 0; x < rooms_width_count; x++)
            for (int y = 0; y < rooms_height_count; y++)
                createRoom1(x*ROOM_SIZE, y*ROOM_SIZE);
    }

    private static void createRoom(int room_up_x, int room_left_y){
        int room_down_x = room_up_x + ROOM_SIZE - 2;
        int room_right_y = room_left_y + ROOM_SIZE - 2;
        ArrayList<Wall> walls = new ArrayList<>();
        for (int x = room_up_x; x <= room_down_x; x++)
            for (int y = room_left_y; y <= room_right_y; y++)
                if (x == room_up_x || x == room_down_x || y == room_left_y || y == room_right_y) {
                    cells[x][y] = new Wall(x, y);
                    walls.add((Wall) cells[x][y]);
                }
        createDoors(walls);
    }

    private static void createRoom1(int room_left_x, int room_up_y){
        Random random = new Random();
        int dx = random.nextInt(2);
        int dy = random.nextInt(2);
        room_left_x += dx;
        room_up_y += dy;
        dx = random.nextInt(2) - 1;
        dy = random.nextInt(2) - 1;
        int room_right_x = room_left_x + ROOM_SIZE - 2 + dx;
        int room_down_y = room_up_y + ROOM_SIZE - 2 + dy;
        ArrayList<Wall> walls = new ArrayList<>();
        for (int x = room_left_x; x <= room_right_x; x++) {
            cells[x][room_up_y] = new Wall(x, room_up_y);
            walls.add((Wall) cells[x][room_up_y]);
        }
        for (int y = room_up_y + 1; y <= room_down_y; y++) {
                cells[room_right_x][y] = new Wall(room_right_x, y);
                    walls.add((Wall) cells[room_right_x][y]);
                }
        for (int x = room_right_x - 1; x >= room_left_x; x--) {
            cells[x][room_down_y] = new Wall(x, room_down_y);
            walls.add((Wall) cells[x][room_down_y]);
        }
        for (int y = room_down_y - 1; y >= room_up_y + 1; y--) {
            cells[room_left_x][y] = new Wall(room_left_x, y);
            walls.add((Wall) cells[room_left_x][y]);
        }
        createDoors1(walls);
    }

    private static void createDoors(ArrayList<Wall> walls){
        Random rand = new Random();
        int n = rand.nextInt(6)+4;
        int walls_count = walls.size();
        for (int i=0; i<n; i++){
            int j = rand.nextInt(walls_count);
            Wall wall = walls.get(j);
            cells[wall.getX()][wall.getY()] = new Empty(wall.getX(), wall.getY());
            walls.remove(j);
            walls_count--;
        }
    }

    private static void createDoors1(ArrayList<Wall> walls){
        int walls_count = walls.size();
        Random rand = new Random();
        int start = rand.nextInt(walls_count);
        int firstEmptySpace =  rand.nextInt(3) + 2;
        int firstWallSpace = rand.nextInt(3) + 2;
        int secondEmptySpace = rand.nextInt(3) + 2;
        int secondWallSpace = walls_count - firstEmptySpace - firstWallSpace - secondEmptySpace;
        for (int i = 0; i < firstEmptySpace; i++){
            Wall wall = walls.get((start + i) % walls_count);
            cells[wall.getX()][wall.getY()] = new Empty(wall.getX(), wall.getY());
        }

        for (int i = 0; i < secondEmptySpace; i++){
            Wall wall = walls.get((start + i + firstEmptySpace + firstWallSpace) % walls_count);
            cells[wall.getX()][wall.getY()] = new Empty(wall.getX(), wall.getY());
        }
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
        ArrayList<SnakeHead> possibleHeads = getPossibleSnakeHeads();
        Random random = new Random();
        int index = random.nextInt(possibleHeads.size());
        SnakeHead head = possibleHeads.get(index);
        putSnakeFrom(head);
    }

    private static ArrayList<SnakeHead> getPossibleSnakeHeads() {
        ArrayList<Cell> horizontalSpacesStarts = getSpacesStarts(Directions.Right);
        ArrayList<Cell> verticalSpacesStarts = getSpacesStarts(Directions.Down);
        ArrayList<SnakeHead> result = new ArrayList<SnakeHead>();
        horizontalSpacesStarts.forEach(cell -> {
            result.add(new SnakeHead(cell.getX() + 1, cell.getY(), Directions.Left));
            result.add(new SnakeHead(cell.getX() + 2, cell.getY(), Directions.Right));
        });
        verticalSpacesStarts.forEach(cell -> {
            result.add(new SnakeHead(cell.getX(), cell.getY() + 1, Directions.Up));
            result.add(new SnakeHead(cell.getX(), cell.getY() + 2, Directions.Down));
        });
        return result;
    }

    private static ArrayList<Cell> getSpacesStarts(Directions direction) {
        ArrayList<Cell> starts = new ArrayList<Cell>();
        int dx = direction.getVector().getX();
        int dy = direction.getVector().getY();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean isEmptySpace = true;
                int x = i;
                int y = j;
                for (int spaceCount = 0; spaceCount < SNAKE_SPACE; spaceCount++) {
                    if (!(0 <= x && x < width) || !(0 <= y && y < height) || !(cells[x][y] instanceof Empty)) {
                        isEmptySpace = false;
                    }
                    x += dx;
                    y += dy;
                }
                if (isEmptySpace) {
                    starts.add(cells[i][j]);
                }
            }
        }
        return starts;
    }

    private static void putSnakeFrom(SnakeHead head) {
        int headX = head.getX();
        int headY = head.getY();
        cells[headX][headY] = head;
        int dx = head.getDirection().opposite().getVector().getX();
        int dy = head.getDirection().opposite().getVector().getY();
        cells[headX + dx][headY + dy] = new SnakePart(headX + dx, headY + dy);
        cells[headX + 2 * dx][headY + 2 * dy] = new SnakePart(headX + 2 * dx, headY + 2 * dy);
    }
}