package lp.souhvezdi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaA extends Scene {

    private final Random rnd = new Random();
    private final Pane pane;
    private double maxWidth;
    private double maxHeight;
    private double starSize = 20;

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
    public VariantaA(Pane pane, double width, double height) {
        super(pane, width, height);
        this.pane = pane;
        maxWidth = width;
        maxHeight = height;
        pane.setBackground(Background.fill(Color.BLACK));
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            Star star = new Star(maxWidth / 2, maxHeight / 2, starSize, starSize);
            pane.getChildren().add(star);
            star.goTo(-maxWidth / 2 + pane.getInsets().getLeft() + rnd.nextInt((int) (maxWidth - starSize)),
                    -maxHeight / 2 + pane.getInsets().getTop() + rnd.nextInt((int) (maxHeight - starSize)));
        }));

        timeline.setCycleCount(1000);
        timeline.play();

        events();
    }

    private void events() {
        pane.widthProperty().addListener((observableValue, number, t1) -> maxWidth = t1.doubleValue());
        pane.heightProperty().addListener((observableValue, number, t1) -> maxHeight = t1.doubleValue());
    }
}
