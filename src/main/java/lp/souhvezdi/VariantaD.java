package lp.souhvezdi;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaD extends Scene {

    private static final String START_TEXT = "Start";
    private static final String STOP_TEXT = "Stop";
    private final Random rnd = new Random();
    private boolean start = true;
    private Timeline fireworksTimeline;

    public VariantaD(Pane pane, double width, double height) {
        super(pane, width, height);
        pane.setCursor(Cursor.CROSSHAIR);
        Point point = new Point();
        Timeline mouseTimeline = getTimeline(pane, point);

        setOnMouseDragged(mouseEvent -> {
            pane.setCursor(Cursor.NONE);
            point.setX(mouseEvent.getX());
            point.setY(mouseEvent.getY());
        });
        setOnMousePressed(mouseEvent -> {
            point.setX(mouseEvent.getX());
            point.setY(mouseEvent.getY());
            mouseTimeline.play();
        });
        setOnMouseReleased(mouseEvent -> {
            mouseTimeline.stop();
            createFireworks(mouseEvent.getX(), mouseEvent.getY(), pane);
        });

        startFireworks(pane, width, height);

        Button button = new Button(START_TEXT);
        button.setPrefSize(75, 25);
        button.setCursor(Cursor.HAND);
        button.setOnAction(actionEvent -> {
            if (start) {
                fireworksTimeline.play();
                button.setText(STOP_TEXT);
            } else {
                fireworksTimeline.stop();
                button.setText(START_TEXT);
            }
            start = !start;
        });
        pane.getChildren().add(button);
    }

    private void startFireworks(Pane pane, double width, double height) {
        fireworksTimeline = new Timeline(new KeyFrame(Duration.millis(400), event -> {
            Point localPoint = new Point(rnd.nextInt((int) width), height, 0, null);
            Timeline timeline = getTimeline(pane, localPoint);
            timeline.play();
            Timeline yTimeline = new Timeline(new KeyFrame(Duration.millis(2), yMoving -> localPoint.setY(localPoint.getY() - 1)));
            yTimeline.setCycleCount((int) (width / 2 + rnd.nextInt((int) (width / 4))));
            yTimeline.setOnFinished(actionEvent -> {
                timeline.stop();
                createFireworks(localPoint.getX(), localPoint.getY(), pane);
            });
            yTimeline.play();
        }));
        fireworksTimeline.setCycleCount(Animation.INDEFINITE);
    }

    private void createFireworks(double x, double y, Pane pane) {
        Animation[] animations = new Animation[45];
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
        ParallelTransition explosionTransition = new ParallelTransition();
        explosionTransition.getChildren().clear();
        explosionTransition.getChildren().addAll(animations);
        explosionTransition.setOnFinished(actionEvent -> pane.setCursor(Cursor.CROSSHAIR));
        explosionTransition.play();
    }

    private Timeline getTimeline(Pane pane, Point point) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            Star star = new Star(point.getX(), point.getY(), Souhvezdi.getStarSize(), Souhvezdi.getStarSize(), 600);
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
