package lp.waterfall;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Drop {

    private final Line line;
    private final Pane pane;

    public Drop(Pane pane, double x, double y, double width, double height) {
        this.pane = pane;
        line = new Line(x + width, y, x, y + height);
        line.setStroke(Color.LIGHTBLUE);
        pane.getChildren().add(line);
    }

    public void start(double xMove) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(line);
        translateTransition.setToY(pane.getHeight());
        translateTransition.setToX(xMove);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setOnFinished(actionEvent -> pane.getChildren().remove(line));
        translateTransition.play();
    }
}
