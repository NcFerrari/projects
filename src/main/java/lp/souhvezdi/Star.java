package lp.souhvezdi;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Star extends Group {

    private final TranslateTransition transition = new TranslateTransition();
    private final Line line;
    private final Line line2;
    private final Line line3;
    private final Line line4;

    public Star(double x, double y, double width, double height) {
        this(x, y, width, height, 2);
    }

    public Star(double x, double y, double width, double height, int travelTimeInSeconds) {
        x -= width / 2;
        y -= height / 2;
        line = new Line(x + width / 2, y, x + width / 2, y + height);
        line2 = new Line(x, y + height / 2, x + width, y + height / 2);
        line3 = new Line(x + width / 5, y + height / 5, x + 4 * width / 5, y + 4 * height / 5);
        line4 = new Line(x + width / 5, y + 4 * height / 5, x + 4 * width / 5, y + height / 5);
        setColor(Color.WHITE);
        getChildren().addAll(line, line2, line3, line4);

        transition.setNode(this);
        transition.setDuration(Duration.seconds(travelTimeInSeconds));
        transition.setCycleCount(1);
        setAutoSizeChildren(false);
    }

    public void removeFromPaneAfterFinished(Pane pane) {
        transition.setOnFinished(actionEvent -> pane.getChildren().remove(this));
    }

    public void goTo(double x, double y) {
        transition.setToX(x);
        transition.setToY(y);
        transition.play();
    }

    public void setColor(Color color) {
        line.setStroke(color);
        line2.setStroke(color);
        line3.setStroke(color);
        line4.setStroke(color);
    }
}
