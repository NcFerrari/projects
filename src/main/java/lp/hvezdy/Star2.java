package lp.hvezdy;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Star2 extends Group {

    public Star2(double x, double y, int radius) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setFill(Color.WHITE);
        getChildren().add(circle);
    }

    public void start(Pane pane, double x, double y) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(this);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(1.5);
        scaleTransition.setByY(1.5);
        scaleTransition.setCycleCount(1);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setToX(x);
        translateTransition.setToY(y);
        translateTransition.setCycleCount(1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(scaleTransition, translateTransition);
        parallelTransition.setCycleCount(1);
        parallelTransition.setAutoReverse(false);
        parallelTransition.setOnFinished(actionEvent -> pane.getChildren().remove(this));
        parallelTransition.play();
    }
}
