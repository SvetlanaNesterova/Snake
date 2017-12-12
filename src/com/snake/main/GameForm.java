package com.snake.main;

import com.snake.main.model.Directions;
import com.snake.main.model.Game;
import com.snake.main.model.cell.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class GameForm extends JPanel{

    private final static int SPEED = 75;
    private final static int CELL_SIZE = 30;
    private final static int HEAD_SIZE = 25;
    private final static int WIDTH_SHIFT = 18;
    private final static int HEIGHT_SHIFT = 47;
    private final static int TO_SAVE_APPLES_DELTA = 3;

    private final static Color BACKGROUND_COLOR1 = new Color(0xD9D2FF);

    private Game game;
    private JFrame window;
    private Timer timer;
    private Directions nextSnakeDirection;
    private Painter painter;
    private JLabel header;
    private int savedApplesCount = 0;
    private Saver<Game> saver;
    private int applesToSave = TO_SAVE_APPLES_DELTA;

    public GameForm(){
        try {
            game = Game.getNewInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nextSnakeDirection = game.getSnake().getSnakeDirection();
        saver = new Saver<Game>();

        painter = new Painter(this);
        setBackground(BACKGROUND_COLOR1);
        RepaintAction action = new RepaintAction();
        timer = new Timer(SPEED, action);
        configView();
    }

    private void configView() {
        window = new JFrame("Snake");
        int fieldWidth = game.getField().getWidth();
        int fieldHeight = game.getField().getHeight();
        window.setSize(CELL_SIZE * fieldWidth + WIDTH_SHIFT,
                HEAD_SIZE + CELL_SIZE * fieldHeight + HEIGHT_SHIFT);
        window.setLocation(50,50);

        ImageIcon icon = new ImageIcon("src\\com\\snake\\assets\\snake.png");
        window.setIconImage(icon.getImage());
        window.addKeyListener(new Listener());
        window.setContentPane(this);
        header = new JLabel();
        header.setLocation(55,55);
        add(header);
        changeHeader();

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.requestFocusInWindow();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < game.getField().getWidth(); i++) {
            for (int j = 0; j < game.getField().getHeight(); j++) {
                Cell cell = game.getField().cellAt(i, j);
                String cellType = cell.getClass().getSimpleName();
                try {
                    painter.getClass().getDeclaredMethod(
                            "paint" + cellType, Cell.class, Graphics2D.class).invoke(painter, cell, g2);
                } catch (NoSuchMethodException e) {
                    painter.paintDefault(cell, g2);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Game getGame() {
        return game;
    }

    private class RepaintAction implements ActionListener{
        public void actionPerformed(ActionEvent evt) {
            game.getSnake().tryChangeHeadDirection(nextSnakeDirection);
            try {
                game.makeStep();
                if (game.getEatenApples() == applesToSave) {
                    boolean wasSaved = saver.tryToSave(game);
                    if (wasSaved) {
                        savedApplesCount = game.getEatenApples();
                        applesToSave += TO_SAVE_APPLES_DELTA;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            window.setTitle("Score: " + game.getScore());
            changeHeader();
            if (game.isOver()) {
                Game saved = saver.getLastSaved();
                if (saver.isSaved() && saved != null) {
                    game = saved;
                    savedApplesCount = game.getEatenApples();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "YOUR SNAKE IS DEAD\nSHAMEFUL DISPLAY\n YOUR SCORE IS: " + game.getScore(), "EPIC FAIL", JOptionPane.ERROR_MESSAGE);
                    startNewGame();
                }
            }
            repaint();
        }
    }

    private void startNewGame() {
        timer.stop();
        try {
            game = Game.getNewInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        changeHeader();
        painter = new Painter(this);
        nextSnakeDirection = game.getSnake().getSnakeDirection();
        repaint();
    }

    private void changeHeader() {
        header.setText("Яблоки: " + game.getEatenApples() +
                "    Сохранено на: " + savedApplesCount);
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
                return;
            nextSnakeDirection = direction;
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}

