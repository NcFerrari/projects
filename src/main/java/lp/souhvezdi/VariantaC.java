package lp.souhvezdi;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class VariantaC extends Scene {

    private final Random rnd = new Random();
    private double x = 0;
    private double y = 0;

    public VariantaC(Pane pane, double width, double height, Souhvezdi souhvezdi) {
        super(pane, width, height);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), event -> {
            Star star = new Star(x, y, Souhvezdi.getStarSize(), Souhvezdi.getStarSize(), 1_000);
            star.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            star.removeFromPaneAfterFinished(pane);
            pane.getChildren().add(star);
            star.createTransition(-souhvezdi.getMaxWidth() / 2 + pane.getInsets().getLeft() + rnd.nextInt((int) (souhvezdi.getMaxWidth() - Souhvezdi.getStarSize())),
                    -souhvezdi.getMaxHeight() / 2 + pane.getInsets().getTop() + rnd.nextInt((int) (souhvezdi.getMaxHeight() - Souhvezdi.getStarSize())), true);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);

        setOnMouseDragged(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
        });
        setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
            timeline.play();
        });
        setOnMouseReleased(mouseEvent -> timeline.stop());
    }
}
