package ru.tyubarovma.mousemover.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import ru.tyubarovma.mousemover.AutoMouseMoverFX;

public class AppController {
    private final AutoMouseMoverFX application;

    public AppController(Application application) {
        this.application = (AutoMouseMoverFX) application;
    }

    public void onActionStopButton(ActionEvent actionEvent ) {
        Button button = (Button) actionEvent.getSource();
        if (button.getText().equals("Stop")) {
            this.application.stopMouseMover();
//            System.out.println("Stop pressed");
            button.setText("Start");
        }
        else {
            this.application.startMouseMover();
//            System.out.println("Start pressed");
            button.setText("Stop");
        }
        this.application.sizeToScene();
    }

    public void onActionInputSlider(InputMethodEvent inputMethodEvent) {
        System.out.println("Spinner: InputMethodEvent");
        this.application.restartOrIdleIfCancelled();
    }

    public void onActionMouseClickedSlider(MouseEvent mouseEvent) {
        this.application.restartOrIdleIfCancelled();
    }

}
