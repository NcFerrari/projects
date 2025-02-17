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

    public VariantaE(Pane pane, double width, double height) {
        super(pane, width, height);
        double stredX = pane.getWidth() / 2;
        double stredY = pane.getHeight() / 2;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            double starX = rnd.nextInt((int) pane.getWidth());
            double starY = rnd.nextInt((int) pane.getHeight());
            Star2 star = new Star2(starX, starY, 1 + rnd.nextInt(3));

            double k = (starY - stredY) / (starX - stredX);
            double q = starY - starX * k;
            double noveX;
            double noveY;
            if (starX - stredX >= starY - stredY) {
                noveX = starX >= stredX ? width : 0;
                noveY = k * noveX + q;
            } else {
                noveY = starY >= stredY ? height : 0;
                noveX = (noveY - q) / k;
            }
            star.start(noveX - starX, noveY - starY);
            pane.getChildren().add(star);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();

        pane.setOnMouseDragged(mouseEvent -> {
            double starX = mouseEvent.getX();
            double starY = mouseEvent.getY();
            Star2 star = new Star2(starX, starY, 1 + rnd.nextInt(3));


//            star.start(noveX - starX, noveY - starY);
            pane.getChildren().add(star);
        });
    }
}
