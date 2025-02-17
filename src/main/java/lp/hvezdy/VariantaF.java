package lp.hvezdy;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class VariantaF extends Scene {

    public VariantaF(Pane pane) {
        super(pane, pane.getPrefWidth(), pane.getPrefHeight());
        SuperStar star = new SuperStar(pane.getWidth() / 2, pane.getHeight() / 2, 500, Color.YELLOW);
        pane.getChildren().add(star);
        star.shine();
    }
}
