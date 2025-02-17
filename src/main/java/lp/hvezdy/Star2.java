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

    private final Circle circle;

    public Star2(double x, double y, int radius) {
        circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        getChildren().add(circle);
    }

    public void start(Pane pane, double x, double y) {
        final Duration duration = Duration.millis(1000);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(this);
        scaleTransition.setDuration(duration);
        scaleTransition.setByX(1);
        scaleTransition.setByY(scaleTransition.getByX());

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this);
        translateTransition.setDuration(duration);
        translateTransition.setToX(x);
        translateTransition.setToY(y);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(scaleTransition, translateTransition);
        parallelTransition.setOnFinished(actionEvent -> pane.getChildren().remove(this));
        parallelTransition.play();
    }

    public void setColor(Color color) {
        circle.setFill(color);
    }
}
