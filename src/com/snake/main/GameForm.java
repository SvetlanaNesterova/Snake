package com.snake.main;

import com.snake.main.model.Directions;
import com.snake.main.model.Game;
import com.snake.main.model.cell.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameForm extends JPanel{

    private final static int SPEED = 100;
    private final static int CELL_SIZE = 30;
    private final static int WIDTH_SHIFT = 18;
    private final static int HEIGHT_SHIFT = 47;
    private final static int BORDER = 0;

    private final static Color SNAKE_COLOR = new Color(45,219,22);
    private final static Color SNAKE_HEAD_COLOR = new Color(0x26AB84);
    private final static Color BACKGROUND_COLOR1 = new Color(0xD9D2FF);
    private final static Color BACKGROUND_COLOR2 = new Color(0xD3CBF0);
    private final static Color APPLE_COLOR = new Color(0xFF2141);
    private final static Color APPLE_ROTTEN_COLOR = new Color(0x3E1E0D);
    private final static Color WALL_COLOR = new Color(0x023A4F);

    private Game game;
    private int fieldWidth;
    private int fieldHeight;
    private JFrame window;
    private Timer timer;
    private Directions nextSnakeDirection;


    public GameForm(){
        game = new Game();
        fieldWidth = game.getField().getWidth();
        fieldHeight = game.getField().getHeight();
        setBackground(BACKGROUND_COLOR1);
        RepaintAction action = new RepaintAction();
        timer = new Timer(SPEED, action);
        window = new JFrame("Snake");
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

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i< game.getField().getWidth(); i++)
            for (int j = 0; j< game.getField().getHeight(); j++){
                switch (game.getField().cellAt(i, j).getName()){
                    case "SnakeHead":
                        drawCell(g2, i, j, SNAKE_HEAD_COLOR);
                        break;
                    case "SnakePart":
                        drawCell(g2, i, j, getSnakeColor((SnakePart) game.getField().cellAt(i, j)));
                        break;
                    case "Apple":
                        drawCell(g2, i, j, getAppleColor());
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
                (tailColors[0] - headColors[0]) / game.getSnake().getLength() * position + headColors[0],
                (tailColors[1] - headColors[1]) / game.getSnake().getLength() * position + headColors[1],
                (tailColors[2] - headColors[2]) / game.getSnake().getLength() * position + headColors[2]
                );
    }

    private Color getAppleColor() {
        final int ticksCount = Game.TICKS_TO_ROT;
        float[] rottenColors = new float[3];
        float[] normalColors = new float[3];
        APPLE_COLOR.getRGBColorComponents(normalColors);
        APPLE_ROTTEN_COLOR.getRGBColorComponents(rottenColors);
        return new Color(
                (rottenColors[0] - normalColors[0]) / ticksCount * game.getTicks() + normalColors[0],
                (rottenColors[1] - normalColors[1]) / ticksCount * game.getTicks() + normalColors[1],
                (rottenColors[2] - normalColors[2]) / ticksCount * game.getTicks() + normalColors[2]
        );
    }

    private class RepaintAction implements ActionListener{
        public void actionPerformed(ActionEvent evt) {
            game.getSnake().changeHeadDirection(nextSnakeDirection);
            game.makeStep();
            window.setTitle("Score: " + game.getScore());
            if (game.isOver()) {
                JOptionPane.showMessageDialog(null,
                        "YOUR SNAKE IS DEAD\nSHAMEFUL DISPLAY", "EPIC FAIL", JOptionPane.ERROR_MESSAGE);
                startNewGame();
            }
            repaint();
        }
    }

    private void startNewGame() {
        timer.stop();
        game = new Game();
        repaint();
    }

    private class Listener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            Directions direction = null;

            if (!timer.isRunning()) {
                timer.start();
                if (key == KeyEvent.VK_F2)
                    return;
            }

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
            else if (key == KeyEvent.VK_F1) {
                startNewGame();
                return;
            }
            else if (key == KeyEvent.VK_F2) {
                timer.stop();
                return;
            }
            else if (key == KeyEvent.VK_ESCAPE) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
            if (direction == null)
                direction = game.getSnake().getSnakeDirection();
            if (direction.isOpposite(game.getSnake().getSnakeDirection()))
                return;
            nextSnakeDirection = direction;
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}

