package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

public class VariantaE extends Scene {

    private final Random rnd = new Random();
    private final Pane pane;

    public VariantaE(Pane pane) {
        super(pane, pane.getWidth(), pane.getHeight());
        this.pane = pane;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), event -> {
            double starX = rnd.nextInt((int) pane.getWidth());
            double starY = rnd.nextInt((int) pane.getHeight());
            createStar(starX, starY, pane.getWidth(), pane.getHeight());
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void createStar(double starX, double starY, double width, double height) {
        Star2 star = new Star2(starX, starY, 1 + rnd.nextInt(2));
        pane.getChildren().add(star);
        if (starY < (height / width * starX)) {
            if (starY < (starX * (-height / width) + height)) {
                //up quarter
                star.start(pane, getFinalX(starX, starY, 0) - starX, -starY);
            } else {
                //right quarter
                star.start(pane, width - starX, getFinalY(starX, starY, width) - starY);
            }
        } else {
            if (starY < (starX * (-height / width) + height)) {
                //left quarter
                star.start(pane, -starX, getFinalY(starX, starY, 0) - starY);
            } else {
                //bottom quarter
                star.start(pane, getFinalX(starX, starY, height) - starX, height - starY);
            }
        }
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
