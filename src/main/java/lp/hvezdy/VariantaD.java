package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light.Point;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class VariantaD extends Scene {

    private static final String START_TEXT = "Start";
    private static final String STOP_TEXT = "Stop";
    private final Random rnd = new Random();
    private boolean start = true;
    private Timeline fireworksTimeline;

    public VariantaD(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
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
            Souhvezdi.ANIMATIONS.add(mouseTimeline);
            mouseTimeline.play();
            try {
                String soundPath = Objects.requireNonNull(getClass().getResource("/sounds/fly" + rnd.nextInt(2) + ".mp3")).toURI().toString();
                Media sound = new Media(soundPath);
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            } catch (URISyntaxException e) {
                e.getReason();
            }
        });
        setOnMouseReleased(mouseEvent -> {
            mouseTimeline.stop();
            createFireworks(mouseEvent.getX(), mouseEvent.getY(), pane);
        });

        startFireworks(pane, pane.getWidth(), pane.getHeight());

        Button button = new Button(START_TEXT);
        button.setPrefSize(75, 25);
        button.setCursor(Cursor.HAND);
        button.setOnAction(actionEvent -> {
            if (start) {
                Souhvezdi.ANIMATIONS.add(fireworksTimeline);
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
            Souhvezdi.ANIMATIONS.add(timeline);
            timeline.play();
            Timeline yTimeline = new Timeline(new KeyFrame(Duration.millis(2), yMoving -> localPoint.setY(localPoint.getY() - 1)));
            yTimeline.setCycleCount((int) (width / 2 + rnd.nextInt((int) (width / 4))));
            yTimeline.setOnFinished(actionEvent -> {
                timeline.stop();
                createFireworks(localPoint.getX(), localPoint.getY(), pane);
            });
            Souhvezdi.ANIMATIONS.add(yTimeline);
            yTimeline.play();
            try {
                String soundPath = Objects.requireNonNull(getClass().getResource("/sounds/fly" + rnd.nextInt(2) + ".mp3")).toURI().toString();
                Media sound = new Media(soundPath);
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            } catch (URISyntaxException ignored) {
                throw new UnsupportedOperationException();
            }
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
        Souhvezdi.ANIMATIONS.add(explosionTransition);
        explosionTransition.play();
        try {
            String soundPath = Objects.requireNonNull(getClass().getResource("/sounds/explosion" + rnd.nextInt(4) + ".mp3")).toURI().toString();
            Media sound = new Media(soundPath);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (URISyntaxException e) {
            e.getReason();
        }
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

    public void setOnSwitching() {
        fireworksTimeline.stop();
    }
}
