package lp.hvezdy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaE extends Scene {

    private final Random rnd = new Random();
    private final Pane pane;
    private final Slider sliderR;
    private final Slider sliderG;
    private final Slider sliderB;
    private Color color = Color.WHITE;
    private boolean randomize;

    public VariantaE(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
        this.pane = pane;
        sliderR = new Slider(0, 255, 255);
        sliderG = new Slider(0, 255, 255);
        sliderB = new Slider(0, 255, 255);
        sliderG.setLayoutY(14);
        sliderB.setLayoutY(28);
        sliderR.valueProperty().addListener((observableValue, number, t1) -> changeColor());
        sliderG.valueProperty().addListener((observableValue, number, t1) -> changeColor());
        sliderB.valueProperty().addListener((observableValue, number, t1) -> changeColor());
        pane.getChildren().addAll(sliderR, sliderG, sliderB);
        ToggleButton button = new ToggleButton("Random");
        button.setLayoutX(140);
        button.setOnAction(actionEvent -> {
            sliderR.setDisable(button.isSelected());
            sliderG.setDisable(button.isSelected());
            sliderB.setDisable(button.isSelected());
            randomize = !randomize;
        });
        pane.getChildren().add(button);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), event -> {
            double starX = rnd.nextInt((int) pane.getWidth());
            double starY = rnd.nextInt((int) pane.getHeight());
            createStar(starX, starY, pane.getWidth(), pane.getHeight());
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void createStar(double starX, double starY, double width, double height) {
        int size = 1 + rnd.nextInt(2);
        Star star = new Star(starX, starY, size, size);
        if (randomize) {
            star.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        } else {
            star.setColor(color);
        }
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

    private void changeColor() {
        color = Color.rgb((int) sliderR.getValue(), (int) sliderG.getValue(), (int) sliderB.getValue());
    }
}
