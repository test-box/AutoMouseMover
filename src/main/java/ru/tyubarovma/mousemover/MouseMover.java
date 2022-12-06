package ru.tyubarovma.mousemover;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.robot.Robot;

import java.util.Optional;

class MouseMover {
    private final Robot robot = new Robot();
    private int ticks;
    private Optional<TextField> counterOpt = Optional.empty();
    private boolean toggle = false;

    private boolean getToggle() {
        toggle = !toggle;
        return toggle;
    }

    public void moveMouse() {
        System.out.print(toggle + "  ");
        if (getToggle()) {
            System.err.println("Двигаем мышь туда");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    robot.mouseMove(robot.getMouseX() + 1, robot.getMouseY() + 1);
                }
            });
        } else {
            System.err.println("Двигаем мышь сюда");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    robot.mouseMove(robot.getMouseX() - 1, robot.getMouseY() - 1);
                }
            });
            ticks++;
            counterOpt.ifPresent(c -> c.setText(Integer.toString(ticks)));
        }
    }

    public Runnable task() {
        return this::moveMouse;
    }

    public void addCounter(TextField textField) {
        this.counterOpt = Optional.of(textField);
    }
}
