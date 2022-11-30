package ru.tyubarovma.mousemover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutoMouseMover {
    private final int timeout = 15000;

    public static void main(String[] args) {
        new AutoMouseMover().go();
    }

    public void go() {
        MouseMover mouseMover = new MouseMover(this.timeout);
        Thread thread = new Thread(mouseMover);
        thread.start();
        String consoleInput = "";
        while(!consoleInput.equals("bye")) {
            consoleInput = this.getUserInput("Для выхода набери bye");
        }
        mouseMover.stop();
        thread.interrupt();
    }

    public static String getUserInput(String prompt) {
        String inputLine = null;
        System.out.println(prompt + " ");
        try {
            BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(System.in));
            inputLine = inputStreamReader.readLine();
            if (inputLine.length() == 0) return null;
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }
        return inputLine.toLowerCase();
    }
}