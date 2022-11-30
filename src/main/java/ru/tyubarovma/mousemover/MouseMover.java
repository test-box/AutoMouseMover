package ru.tyubarovma.mousemover;

import java.awt.*;

class MouseMover implements Runnable {
    private final int timeout;
    boolean bye = false;

    public MouseMover(int timeout) {
        this.timeout = timeout;
    }
    public void stop() {
        this.bye = true;
    }

    @Override
    public void run() {
        boolean toggle = true;
        PointerInfo pointerInfo;
        Point location;
        try {
            Robot robot = new Robot();
            while (!this.bye) {
                pointerInfo = MouseInfo.getPointerInfo();
                if (pointerInfo == null) {
                    Thread.sleep(this.timeout);
                    continue;
                }
                location = pointerInfo.getLocation();
                int x = (int) location.getX();
                int y = (int) location.getY();
                if (toggle) {
                    robot.mouseMove(x + 1, y + 1);
                    toggle = false;
                } else {
                    robot.mouseMove( x - 1, y - 1);
                    toggle = true;
                }
                Thread.sleep(this.timeout);
            }
        } catch (InterruptedException e) {
            System.out.println("Выходим...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
