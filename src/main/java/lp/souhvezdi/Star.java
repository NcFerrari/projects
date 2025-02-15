package lp.souhvezdi;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Star extends Group {

    private final TranslateTransition transition = new TranslateTransition();

    public Star(double x, double y, double width, double height) {
        x -= width / 2;
        y -= height / 2;
        Line line = new Line(x + width / 2, y, x + width / 2, y + height);
        Line line2 = new Line(x, y + height / 2, x + width, y + height / 2);
        Line line3 = new Line(x + width / 5, y + height / 5, x + 4 * width / 5, y + 4 * height / 5);
        Line line4 = new Line(x + width / 5, y + 4 * height / 5, x + 4 * width / 5, y + height / 5);
        line.setStroke(Color.WHITE);
        line2.setStroke(Color.WHITE);
        line3.setStroke(Color.WHITE);
        line4.setStroke(Color.WHITE);
        getChildren().addAll(line, line2, line3, line4);

        transition.setNode(this);
        transition.setDuration(Duration.seconds(2));
        transition.setCycleCount(1);
        setAutoSizeChildren(false);
    }

    public void goTo(double x, double y) {
        transition.setToX(x);
        transition.setToY(y);
        transition.play();
    }
}
