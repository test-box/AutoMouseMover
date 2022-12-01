package ru.tyubarovma.mousemover;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutoMouseMoverFX extends Application {
    private final int timeout = 15000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = buildScene();
        stage.setTitle("AutoMouseMoverFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {

    }

    private Scene buildScene() {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        Label ticksLabel = new Label("Ticks");
        gridPane.add(ticksLabel, 0, 0);
        TextField textField = new TextField();
        gridPane.add(textField, 1, 0);
        textField.setEditable(false);
        Label timeoutLabel = new Label("Timout");
        gridPane.add(timeoutLabel, 2, 0);
        Spinner<Integer> spinner = new Spinner<>();
        gridPane.add(spinner, 3, 0);
        Button button = new Button("Stop");
        gridPane.add(button, 4, 0);
        return scene;
    }
}