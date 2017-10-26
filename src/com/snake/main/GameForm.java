package com.snake.main;

import com.snake.main.model.Directions;
import com.snake.main.model.Field;
import com.snake.main.model.Snake;
import com.snake.main.model.Vector;
import com.snake.main.model.cell.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameForm extends JPanel{

    private final static int SPEED = 100;
    private final static int CELL_SIZE = 30;
    private final static int WIDTH_SHIFT = 18;
    private final static int HEIGHT_SHIFT = 47;
    private final static int BORDER = 1;

    private final static Color SNAKE_COLOR = new Color(45,219,22);
    private final static Color SNAKE_HEAD_COLOR = new Color(0x26AB84);
    private final static Color BACKGROUND_COLOR1 = new Color(0xD9D2FF);
    private final static Color BACKGROUND_COLOR2 = new Color(0xD3CBF0);
    private final static Color APPLE_COLOR = new Color(0xFF2141);
    private final static Color WALL_COLOR = new Color(0x023A4F);

    private Game game;
    private Field field;
    private int fieldWidth;
    private int fieldHeight;
    private Snake snake;


    public GameForm(){
        createNewLevel();

        setBackground(BACKGROUND_COLOR1);

        RepaintAction action = new RepaintAction();
        Timer timer = new Timer(SPEED, action);
        timer.start();
        JFrame window = new JFrame("Snake");
        ImageIcon icon = new ImageIcon("src\\com\\snake\\assets\\snake.png");
        window.setIconImage(icon.getImage());
        window.addKeyListener(new Listener());
        window.setContentPane(this);
        window.setSize(CELL_SIZE*fieldWidth+WIDTH_SHIFT, CELL_SIZE*fieldHeight+ HEIGHT_SHIFT);
        window.setLocation(100,100);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.requestFocusInWindow();
    }

    private void createNewLevel() {
        int gameSize = 20;
        Cell[][] cells = new Cell[gameSize][gameSize];
        /*TextParser tp = new TextParser();
        try {
            cells = tp.parseTextField("Level.txt");
        }catch (Exception e){
            System.out.println("Incorrect filename");
            return;
        }*/
        for (int i=0; i<gameSize; i++){
            for (int j=0; j<gameSize; j++) {
                cells[i][j] = new Empty(i, j);
                if (i == 0 || j == 0 || i == gameSize - 1 || j == gameSize - 1)
                    cells[i][j] = new Wall(i, j);
            }
        }
        Random random = new Random();
        int x = 0, y = 0;
        while (!(cells[x][y] instanceof Empty)) {
            x = random.nextInt(gameSize - gameSize / 2) + gameSize / 4;
            y = random.nextInt(gameSize - gameSize / 2) + gameSize / 4;
        }
        cells[x][y] = new SnakeHead(x, y);
        game = new Game(cells);
        field = game.getField();
        fieldWidth = field.getWidth();
        fieldHeight = field.getHeight();
        snake = game.getSnake();
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i< field.getWidth(); i++)
            for (int j = 0; j< field.getHeight(); j++){
                switch (field.cellAt(i, j).getName()){
                    case "SnakeHead":
                        drawCell(g2, i, j, SNAKE_HEAD_COLOR);
                        break;
                    case "SnakePart":
                        drawCell(g2, i, j, getSnakeColor((SnakePart) field.cellAt(i, j)));
                        break;
                    case "Apple":
                        drawCell(g2, i, j, APPLE_COLOR);
                        break;
                    case "Wall":
                        drawCell(g2, i, j, WALL_COLOR);
                        break;
                    default:
                        drawCell(g2, i, j, (i+j)%2 == 1 ? BACKGROUND_COLOR1 : BACKGROUND_COLOR2);
                }
            }
    }

    private void drawCell(Graphics2D g2, int i, int j, Color color) {
        g2.setColor(color);
        g2.fillRect(CELL_SIZE*i+BORDER, CELL_SIZE*j+BORDER, CELL_SIZE-BORDER, CELL_SIZE-BORDER);
    }

    private Color getSnakeColor(SnakePart snakePart) {
        int position = snakePart.getPosition();
        float[] headColors = new float[3];
        float[] tailColors = new float[3];
        SNAKE_COLOR.getRGBColorComponents(tailColors);
        SNAKE_HEAD_COLOR.getRGBColorComponents(headColors);
        return new Color(
                (tailColors[0] - headColors[0]) / snake.getLength() * position + headColors[0],
                (tailColors[1] - headColors[1]) / snake.getLength() * position + headColors[1],
                (tailColors[2] - headColors[2]) / snake.getLength() * position + headColors[2]
                );
    }

    private class RepaintAction implements ActionListener{
        public void actionPerformed(ActionEvent evt) {
            game.makeStep();
            if (game.isOver()) {
                JOptionPane.showMessageDialog(null,
                        "YOUR SNAKE IS DEAD\nSHAMEFUL DISPLAY", "EPIC FAIL", JOptionPane.ERROR_MESSAGE);
                createNewLevel();
            }
            repaint();
        }
    }

    private class Listener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            Directions direction = null;

            if (key == KeyEvent.VK_LEFT) {
                direction = Directions.Left;
            }
            else if (key == KeyEvent.VK_RIGHT) {
                 direction = Directions.Right;
            }
            else if (key == KeyEvent.VK_UP) {
                direction = Directions.Up;
            }
            else if (key == KeyEvent.VK_DOWN) {
                direction = Directions.Down;
            }
            if (direction == null)
                direction = snake.getSnakeDirection();
            if (direction.isOpposite(snake.getSnakeDirection()))
                return;
            snake.changeHeadDirection(direction);
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}

