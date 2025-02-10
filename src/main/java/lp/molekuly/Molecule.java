package lp.molekuly;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class Molecule extends Circle {

    public Molecule(double x, double y, double radius) {
        super(x, y, radius);
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOW), new Stop(1, Color.RED));
        setFill(lg);
    }
}
