package lp.hvezdy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaG extends Scene {

    private final Random rnd = new Random();

    public VariantaG(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            SuperStar star = new SuperStar(pane.getWidth() / 2, pane.getHeight() / 2, Souhvezdi.getStarSize(), Color.WHITE, 15);
            pane.getChildren().add(star);
            star.shine();
            star.move(pane, -pane.getWidth() / 2 + rnd.nextInt((int) pane.getWidth()), -pane.getHeight() / 2 + rnd.nextInt((int) pane.getHeight()), false);
        }));
        timeline.setCycleCount(400);
        timeline.play();
    }
}
