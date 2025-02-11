package lp.molekuly;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lp.Manager;

import java.util.Random;

public class Molecule extends Circle {

    private final Manager manager = Manager.getInstance();
    private final int maxX;
    private final int maxY;

    private double xSpeed;
    private double ySpeed;
    private final Timeline timeline;

    public Molecule(double x, double y, double radius, int maxX, int maxY) {
        super(x, y, radius);
        this.maxX = maxX + (int) radius;
        this.maxY = maxY + (int) radius;
        Random rnd = new Random();
        xSpeed = (rnd.nextDouble() - 0.5) * 12;
        ySpeed = (rnd.nextDouble() - 0.5) * 12;

        timeline = new Timeline();

        Color color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Color color2 = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Color color3 = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        for (double i = 0; i < 1; i += 0.002) {
            Color interpolatedFirstColor = color.interpolate(color2, i);
            Color interpolatedSecondColor = color2.interpolate(color, i);
            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, interpolatedFirstColor),
                    new Stop(0.25, interpolatedFirstColor),
                    new Stop(0.5, color3),
                    new Stop(0.75, interpolatedSecondColor),
                    new Stop(1, interpolatedSecondColor));

            KeyValue keyValue = new KeyValue(fillProperty(), linearGradient);

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i), keyValue);

            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public void fallDown(Pane pane) {
        ScaleTransition transition = new ScaleTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setNode(this);
        transition.setToX(0);
        transition.setToY(0);
        transition.play();
        transition.setOnFinished(actionEvent -> pane.getChildren().remove(this));
    }

    public void go() {
        double newX = getCenterX() + xSpeed;
        double newY = getCenterY() + ySpeed;
        boolean collision = false;

        if (newX <= getRadius() / 2.0 || newX > maxX) {
            xSpeed = -xSpeed;
            collision = true;
        }

        if (newY <= getRadius() / 2.0 || newY > maxY) {
            ySpeed = -ySpeed;
            collision = true;
        }

        for (Molecule molecule : manager.getMolecules()) {
            if (molecule != this && molecule.getDistance(newX, newY) < getRadius() + molecule.getRadius()) {
                xSpeed = xSpeed + molecule.xSpeed - (molecule.xSpeed = xSpeed);
                ySpeed = ySpeed + molecule.ySpeed - (molecule.ySpeed = ySpeed);
                collision = true;
            }
        }

        if (!collision) {
            setCenterX(newX);
            setCenterY(newY);
        }
    }

    public double getDistance(double x1, double y1) {
        double a = Math.abs(getCenterX() - x1);
        double b = Math.abs(getCenterY() - y1);
        return Math.sqrt(a * a + b * b);
    }
}
