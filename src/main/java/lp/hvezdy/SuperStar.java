package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperStar extends Group {

    private final List<Beam> beams = new ArrayList<>();
    private Timeline shineTimeline;

    public SuperStar(double x, double y, double range, Color color) {
        this(x, y, range, color, 50);
    }

    public SuperStar(double x, double y, double range, Color color, int countOfBeams) {
        for (int i = 0; i < countOfBeams; i++) {
            Beam beam = new Beam(x, y, range, i * (360.0 / countOfBeams), color);
            beams.add(beam);
            getChildren().add(beam);
        }
    }

    public void shine() {
        shineTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> beams.forEach(Beam::start)));
        shineTimeline.setCycleCount(Animation.INDEFINITE);
        Souhvezdi.ANIMATIONS.add(shineTimeline);
        shineTimeline.play();
    }

    public void move(Pane pane, double moveByX, double moveByY) {
        move(pane, moveByX, moveByY, true);
    }

    public void move(Pane pane, double moveByX, double moveByY, boolean remove) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(2_000));
        translateTransition.setNode(this);
        translateTransition.setByX(moveByX);
        translateTransition.setByY(moveByY);
        translateTransition.setOnFinished(actionEvent -> {
            if (remove) {
                shineTimeline.stop();
                pane.getChildren().remove(this);
            }
        });
        Souhvezdi.ANIMATIONS.add(translateTransition);
        translateTransition.play();
    }

    private static class Beam extends Line {

        private final Random rnd = new Random();
        private final double maxRange;
        private final double angle;

        Beam(double x, double y, double range, double angle, Color color) {
            super(x, y, 0, 0);
            maxRange = range;
            this.angle = angle;
            setEndX(x + 0 * Math.cos(Math.toRadians(angle)));
            setEndY(y + 0 * Math.sin(Math.toRadians(angle)));
            setStroke(color);
            setStrokeWidth(1.5);
        }

        public void start() {
            double distance = maxRange / 10 + rnd.nextInt((int) (9 * maxRange / 10));
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250),
                    new KeyValue(endXProperty(), getStartX() + distance * Math.cos(Math.toRadians(angle))),
                    new KeyValue(endYProperty(), getStartY() + distance * Math.sin(Math.toRadians(angle)))
            ));
            Souhvezdi.ANIMATIONS.add(timeline);
            timeline.play();
        }
    }
}
