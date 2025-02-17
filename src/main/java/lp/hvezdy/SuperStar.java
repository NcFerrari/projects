package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperStar extends Group {

    private final List<Beam> beams = new ArrayList<>();

    public SuperStar(double x, double y, double range, Color color) {
        this(x, y, range, color, 40);
    }

    public SuperStar(double x, double y, double range, Color color, int countOfBeams) {
        for (int i = 0; i < countOfBeams; i++) {
            Beam beam = new Beam(x, y, range, i * (360.0 / countOfBeams), color);
            beams.add(beam);
            getChildren().add(beam);
        }
    }

    public void shine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> beams.forEach(Beam::setDistance)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private static class Beam extends Polygon {

        private final Random rnd = new Random();
        private final double maxRange;
        private final double angle;

        Beam(double x, double y, double range, double angle, Color color) {
            super();
            maxRange = range;
            this.angle = angle;
            getPoints().add(x);
            getPoints().add(y);
            getPoints().add(x + range * Math.cos(Math.toRadians(angle)));
            getPoints().add(y + range * Math.sin(Math.toRadians(angle)));
            setStroke(color);
            setStrokeWidth(1.5);
        }

        public void setDistance() {
            double distance = maxRange / 2 + rnd.nextInt((int) (maxRange / 2));
            getPoints().set(2, getPoints().getFirst() + distance * Math.cos(Math.toRadians(angle)));
            getPoints().set(3, getPoints().get(1) + distance * Math.sin(Math.toRadians(angle)));
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
                getPoints().set(2, getPoints().getFirst() + distance * Math.cos(Math.toRadians(angle)));
                getPoints().set(3, getPoints().get(1) + distance * Math.sin(Math.toRadians(angle)));
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }
}
