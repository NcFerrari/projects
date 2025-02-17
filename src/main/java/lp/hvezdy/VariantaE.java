package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.Random;

public class VariantaE extends Scene {

    private final Random rnd = new Random();
    private final Pane pane;

    public VariantaE(Pane pane, double width, double height) {
        super(pane, width, height);
        this.pane = pane;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            double starX = rnd.nextInt((int) pane.getWidth());
            double starY = rnd.nextInt((int) pane.getHeight());
            Star2 star = new Star2(starX, starY, 1 + rnd.nextInt(3));
            pane.getChildren().add(star);
            if (starY < (height / width * starX)) {
                if (starY < (starX * (-height / width) + height)) {
                    star.start(pane, getFinalX(starX, starY, 0) - starX, -starY);
                } else {
                    star.start(pane, width - starX, starY - getFinalY(starX, starY, 0));
                }
            } else {
                if (starY < (starX * (-height / width) + height)) {
                    star.start(pane, -starX, getFinalY(starX, starY, 0) - starY);
                } else {
                    star.start(pane, starX - getFinalX(starX, starY, 0), height - starY);
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private double getFinalX(double existingPointX, double existingPointY, double yOfDesiredPoint) {
        double[] kq = getKQ(existingPointX, existingPointY, pane.getWidth() / 2.0, pane.getHeight() / 2.0);
        double k = kq[0];
        double q = kq[1];
        return (yOfDesiredPoint - q) / k;
    }

    private double getFinalY(double existingPointX, double existingPointY, double xOfDesiredPoint) {
        double[] kq = getKQ(existingPointX, existingPointY, pane.getWidth() / 2.0, pane.getHeight() / 2.0);
        double k = kq[0];
        double q = kq[1];
        return k * xOfDesiredPoint + q;
    }

    private double[] getKQ(double firstX, double firstY, double secondX, double secondY) {
        double k = (secondY - firstY) / (secondX - firstX);
        double q = firstY - firstX * k;
        return new double[]{k, q};
    }
}
