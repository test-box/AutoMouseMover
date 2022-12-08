package ru.tyubarovma.mousemover;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import ru.tyubarovma.mousemover.controller.AppController;

import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

//TODO -Add AlwaysOnTop Button
public class AutoMouseMoverFX extends Application {
    private AppController appController;
    private Stage stage;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final MouseMover mouseMover = new MouseMover();
    private Spinner<Integer> spinner;
    private Optional<ScheduledFuture> mouseMoverTaskOptional = Optional.empty();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.appController = new AppController(this);
        this.stage = stage;
        Scene scene = buildScene();
        // Note: invoke startMouseMover() after buildScene()
        startMouseMover();
        stage.setTitle("AutoMouseMoverFX");
        stage.setScene(scene);
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/icons/mousemover.png"));
        stage.getIcons().add(icon);
        stage.show();
    }

    @Override
    public void stop() {
        stopMouseMover();
        scheduler.shutdown();
    }

    private Scene buildScene() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(4);

        Scene scene = new Scene(gridPane);

        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setPadding(new Insets(2,2,2,2));
        Image lockImage = new Image(getClass().getResourceAsStream("/icons/lock.png"));
        Image unlockImage = new Image(getClass().getResourceAsStream("/icons/unlock.png"));
        ImageView toggleImage = new ImageView();
        toggleImage.setFitHeight(26);
        toggleImage.setFitWidth(24);
        toggleImage.imageProperty().bind(Bindings
                .when(toggleButton.selectedProperty())
                .then(lockImage)
                .otherwise(unlockImage));
        toggleButton.setGraphic(toggleImage);
        toggleButton.setOnAction(appController::onActionToggleButton);
        gridPane.add(toggleButton, 0, 0);


        Label ticksLabel = new Label("Ticks:");
        gridPane.add(ticksLabel, 1, 0);

        TextField textField = new TextField("0");
        gridPane.add(textField, 2, 0);
        textField.setEditable(false);
        textField.setAlignment(Pos.CENTER);
        textField.setPrefWidth(60);
        mouseMover.addCounter(textField);

        Label timeoutLabel = new Label("Timout:");
        gridPane.add(timeoutLabel, 3, 0);

        Spinner<Integer> spinner = new Spinner<>(1, 60, 2);
        spinner.setEditable(false);
        spinner.setPrefWidth(60);
        gridPane.add(spinner, 4, 0);
        this.spinner = spinner;
        spinner.setOnInputMethodTextChanged(appController::onActionInputSlider);
        spinner.setOnMouseClicked(appController::onActionMouseClickedSlider);

        Button button = new Button("Stop");
        gridPane.add(button, 5, 0);
        button.setOnAction(appController::onActionStopButton);

        return scene;
    }

    public void sizeToScene() {
        this.stage.sizeToScene();
    }

    public void startMouseMover() {
        mouseMoverTaskOptional = Optional.of(scheduler.scheduleAtFixedRate(
                mouseMover.task(), 0, this.spinner.getValue(), TimeUnit.SECONDS
                ));
//        System.out.println("futureOptional = " + mouseMoverTaskOptional + " - НОВАЯ ЗАДАЧА");
    }

    public void stopMouseMover() {
        mouseMoverTaskOptional.filter(t -> !t.isCancelled())
                .ifPresent(t -> t.cancel(true));
//        System.out.println("futureOptional = " + mouseMoverTaskOptional + " - ОТМЕНИЛИ СТАРУЮ ЗАДАЧУ");
    }

    private void restartMouseMover() {
        stopMouseMover();
        startMouseMover();
    }

    public void restartOrIdleIfCancelled() {
        if (mouseMoverTaskOptional.filter(t -> !t.isCancelled()).isPresent()) {
            restartMouseMover();
        }
    }

    public void setAllwaysOnTop(boolean onTop) {
        this.stage.setAlwaysOnTop(onTop);
    }
}