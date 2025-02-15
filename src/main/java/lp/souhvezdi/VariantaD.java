package lp.souhvezdi;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaD extends Scene {

    private final Random rnd = new Random();
    private final Animation[] animations = new Animation[45];
    private double x;
    private double y;

    public VariantaD(Pane pane, double width, double height) {
        super(pane, width, height);
        pane.setCursor(Cursor.CROSSHAIR);
        Timeline timeline = getTimeline(pane);

        setOnMouseDragged(mouseEvent -> {
            pane.setCursor(Cursor.NONE);
            x = mouseEvent.getX();
            y = mouseEvent.getY();
        });
        setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
            timeline.play();
        });
        setOnMouseReleased(mouseEvent -> {
            timeline.stop();
            createFireWorks(mouseEvent.getX(), mouseEvent.getY(), pane);
            ParallelTransition explosionTransition = new ParallelTransition();
            explosionTransition.getChildren().clear();
            explosionTransition.getChildren().addAll(animations);
            explosionTransition.setOnFinished(actionEvent -> pane.setCursor(Cursor.CROSSHAIR));
            explosionTransition.play();
        });

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(1000), event -> {

        }));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
    }

    private void createFireWorks(double x, double y, Pane pane) {
        for (int i = 0; i < animations.length; i++) {
            int radius = 300 + rnd.nextInt(75);
            double angle = i * (double) 360 / animations.length;
            double newX = radius * Math.cos(Math.toRadians(angle));
            double newY = radius * Math.sin(Math.toRadians(angle));
            Star star = new Star(x, y, Souhvezdi.getStarSize(), Souhvezdi.getStarSize(), 800 + rnd.nextInt(100));
            star.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            pane.getChildren().add(star);
            star.removeFromPaneAfterFinished(pane);
            animations[i] = star.createTransition(newX, newY, false);
        }
    }

    private Timeline getTimeline(Pane pane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            Star star = new Star(x, y, Souhvezdi.getStarSize(), Souhvezdi.getStarSize(), 600);
            star.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            star.removeFromPaneAfterFinished(pane);
            pane.getChildren().add(star);
            double starRadius = 40;
            star.createTransition(-starRadius + rnd.nextInt((int) (starRadius * 2)), -starRadius + rnd.nextInt((int) (starRadius * 2)), true);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }
}
