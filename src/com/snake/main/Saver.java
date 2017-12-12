package com.snake.main;

import java.io.*;

public class Saver<T> {
    private String  fileName = "temp.ser";
    private boolean isSaved = false;

    public boolean tryToSave(T objToSave) {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(
                    new FileOutputStream(new File(fileName)));
            stream.writeObject(objToSave);
            stream.close();
            isSaved = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSaved() {
        return isSaved;
    }

    public T getLastSaved() {
        if (!isSaved)
            return null;
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("temp.ser"));
            T saved = (T) in.readObject();


            return saved;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
