package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaB extends Scene {

    private final Random rnd = new Random();

    public VariantaB(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            Star star = new Star(pane.getWidth() / 2, pane.getHeight() / 2, Souhvezdi.getStarSize(), Souhvezdi.getStarSize(), 1_000);
            star.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            star.removeFromPaneAfterFinished(pane);
            pane.getChildren().add(star);
            star.createTransition(-pane.getWidth() / 2 + pane.getInsets().getLeft() + rnd.nextInt((int) (pane.getWidth() - Souhvezdi.getStarSize())),
                    -pane.getHeight() / 2 + pane.getInsets().getTop() + rnd.nextInt((int) (pane.getHeight() - Souhvezdi.getStarSize())), true);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
