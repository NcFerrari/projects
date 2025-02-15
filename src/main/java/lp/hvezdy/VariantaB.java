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

    public VariantaB(Pane pane, double width, double height, Souhvezdi souhvezdi) {
        super(pane, width, height);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            Star star = new Star(souhvezdi.getMaxWidth() / 2, souhvezdi.getMaxHeight() / 2, Souhvezdi.getStarSize(), Souhvezdi.getStarSize(), 1_000);
            star.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            star.removeFromPaneAfterFinished(pane);
            pane.getChildren().add(star);
            star.createTransition(-souhvezdi.getMaxWidth() / 2 + pane.getInsets().getLeft() + rnd.nextInt((int) (souhvezdi.getMaxWidth() - Souhvezdi.getStarSize())),
                    -souhvezdi.getMaxHeight() / 2 + pane.getInsets().getTop() + rnd.nextInt((int) (souhvezdi.getMaxHeight() - Souhvezdi.getStarSize())), true);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
