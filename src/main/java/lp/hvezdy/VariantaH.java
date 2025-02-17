package lp.hvezdy;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaH extends Scene {

    private final Random rnd = new Random();

    public VariantaH(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            SuperStar star = new SuperStar(pane.getWidth() / 2, pane.getHeight() / 2, 25.0 + rnd.nextInt(75), Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), 10 + rnd.nextInt(100));
            star.shine();
            star.move(pane, -pane.getWidth() / 2 + rnd.nextInt((int) pane.getWidth()), -pane.getHeight() / 2 + rnd.nextInt((int) pane.getHeight()));
            pane.getChildren().add(star);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        Souhvezdi.ANIMATIONS.add(timeline);
        timeline.play();
    }
}
