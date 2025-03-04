package lp.waterfall;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class App extends Application {

    private final Random random = new Random();
    private double xMove;
    private Pane pane;

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        pane.setStyle("-fx-background-color: BLACk");
        Rectangle2D src = Screen.getPrimary().getBounds();
        Scene scene = new Scene(pane, src.getWidth(), src.getHeight() + 23);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();

        createDrops();

        pane.setOnMouseMoved(evt -> {
            double center = pane.getWidth() / 2;
            xMove = evt.getX() - center;
        });
    }

    private void createDrops() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), actionEvent -> {
            Drop drop = new Drop(pane, random.nextInt((int) pane.getWidth()), 0, 1, 5.0 + random.nextInt(5));
            drop.start(xMove);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
