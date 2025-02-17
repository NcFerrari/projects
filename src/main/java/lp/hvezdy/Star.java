package lp.hvezdy;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Star extends Group {

    private final TranslateTransition translateTransition = new TranslateTransition();
    private final Line line;
    private final Line line2;
    private final Line line3;
    private final Line line4;
    private final int travelTimeInMilliSeconds;

    public Star(double x, double y, double width, double height) {
        this(x, y, width, height, 2_000);
    }

    public Star(double x, double y, double width, double height, int travelTimeInMilliSeconds) {
        this.travelTimeInMilliSeconds = travelTimeInMilliSeconds;
        x -= width / 2;
        y -= height / 2;
        line = new Line(x + width / 2, y, x + width / 2, y + height);
        line2 = new Line(x, y + height / 2, x + width, y + height / 2);
        line3 = new Line(x + width / 5, y + height / 5, x + 4 * width / 5, y + 4 * height / 5);
        line4 = new Line(x + width / 5, y + 4 * height / 5, x + 4 * width / 5, y + height / 5);
        getChildren().addAll(line, line2, line3, line4);
    }

    public void removeFromPaneAfterFinished(Pane pane) {
        translateTransition.setOnFinished(actionEvent -> pane.getChildren().remove(this));
    }

    public TranslateTransition createTransition(double endX, double endY, boolean play) {
        translateTransition.setNode(this);
        translateTransition.setDuration(Duration.millis(travelTimeInMilliSeconds));
        translateTransition.setToX(endX);
        translateTransition.setToY(endY);
        if (play) {
            translateTransition.play();
        }
        return translateTransition;
    }

    public void start(Pane pane, double x, double y) {
        final Duration duration = Duration.millis(1000);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(this);
        scaleTransition.setDuration(duration);
        scaleTransition.setByX(1);
        scaleTransition.setByY(scaleTransition.getByX());

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
        line.setStroke(color);
        line2.setStroke(color);
        line3.setStroke(color);
        line4.setStroke(color);
    }
}
