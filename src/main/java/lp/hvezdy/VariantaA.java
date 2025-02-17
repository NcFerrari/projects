package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaA extends Scene {

    private final Random rnd = new Random();

    /**
     * Mohla by být i druhá a třetí varianta kódu:
     * <p>
     * Task<Void> task = new Task<>() {
     * while (go) {
     * Platform.runLater(() -> {
     * Star star = new Star(width / 2, height / 2, 30, 30);
     * pane.getChildren().add(star);
     * star.goTo(-width / 2 + rnd.nextInt((int) width), -height / 2 + rnd.nextInt((int) height));
     * if (pane.getChildren().size() == 1000) {
     * go = false;
     * }
     * });
     * try {
     * Thread.sleep(50);
     * } catch (InterruptedException e) {
     * Thread.currentThread().interrupt();
     * }
     * }
     * return null;
     * }
     * };
     * new Thread(task).start();
     * <p>
     * final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
     * Runnable task = () -> {
     * if (!go) {
     * scheduler.shutdown();
     * return;
     * }
     * Platform.runLater(() -> {
     * Star star = new Star(width / 2, height / 2, 30, 30);
     * pane.getChildren().add(star);
     * star.goTo(-width / 2 + rnd.nextInt((int) width), -height / 2 + rnd.nextInt((int) height));
     * });
     * if (pane.getChildren().size() == 1000) {
     * go = false;
     * }
     * };
     * <p>
     * scheduler.scheduleAtFixedRate(task, 0, 50, TimeUnit.MILLISECONDS);
     * <p>
     * Jenže ty nejsou tak čisté řešení
     */
    public VariantaA(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            Star star = new Star(pane.getWidth() / 2, pane.getHeight() / 2, Souhvezdi.getStarSize(), Souhvezdi.getStarSize());
            star.setColor(Color.WHITE);
            pane.getChildren().add(star);
            star.createTransition(-pane.getWidth() / 2 + pane.getInsets().getLeft() + rnd.nextInt((int) (pane.getWidth() - Souhvezdi.getStarSize())),
                    -pane.getHeight() / 2 + pane.getInsets().getTop() + rnd.nextInt((int) (pane.getHeight() - Souhvezdi.getStarSize())), true);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        Souhvezdi.ANIMATIONS.add(timeline);
        timeline.play();
    }
}
