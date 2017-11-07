package com.snake.tests;

import com.snake.main.model.Directions;
import com.snake.main.model.Field;
import com.snake.main.model.TextParser;
import com.snake.main.model.cell.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldTest {
    private static Field field;
    private static int rightMostIndex;
    private static int downMostIndex;
    private static Cell centralCell;
    private static Cell leftDownCell;
    private static Cell rightUpCell;

    @BeforeAll
    static void initField() {
        TextParser tp = new TextParser();
        try {
            field = new Field(tp.parseTextField("FieldTest.txt"));
        } catch (Exception e) {
            System.out.println("Incorrect filename");
        }
        rightMostIndex = field.getWidth() - 1;
        downMostIndex = field.getHeight() - 1;
        centralCell = field.cellAt(2, 2);
        leftDownCell = field.cellAt(0, downMostIndex);
        rightUpCell = field.cellAt(rightMostIndex, 0);
    }

    @Test
    void getRightNeighborOfCentral() {
        Cell neighbor = field.getNeighbor(centralCell, Directions.Right);
        assertEquals(field.cellAt(3, 2), neighbor);
    }

    @Test
    void getLeftNeighborOfCentral() {
        Cell neighbor = field.getNeighbor(centralCell, Directions.Left);
        assertEquals(field.cellAt(1, 2), neighbor);
    }

    @Test
    void getUpNeighborOfCentral() {
        Cell neighbor = field.getNeighbor(centralCell, Directions.Up);
        assertEquals(field.cellAt(2, 1), neighbor);
    }

    @Test
    void getDownNeighborOfCentral() {
        Cell neighbor = field.getNeighbor(centralCell, Directions.Down);
        assertEquals(field.cellAt(2, 3), neighbor);
    }

    @Test
    void getDownNeighborOfLeftDown() {
        Cell neighbor = field.getNeighbor(leftDownCell, Directions.Down);
        assertEquals(field.cellAt(0, 0), neighbor);
    }

    @Test
    void getLeftNeighborOfLeftDown() {
        Cell neighbor = field.getNeighbor(leftDownCell, Directions.Left);
        assertEquals(field.cellAt(rightMostIndex, downMostIndex), neighbor);
    }

    @Test
    void getUpNeighborOfRightUp() {
        Cell neighbor = field.getNeighbor(rightUpCell, Directions.Up);
        assertEquals(field.cellAt(rightMostIndex, downMostIndex), neighbor);
    }

    @Test
    void getRightNeighborOfRightUp() {
        Cell neighbor = field.getNeighbor(rightUpCell, Directions.Right);
        assertEquals(field.cellAt(0, 0), neighbor);
    }

}