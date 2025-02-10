package lp.molekuly;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Molecule extends Circle {

    private final int maxX;
    private final int maxY;

    private double xSpeed;
    private double ySpeed;

    public Molecule(double x, double y, double radius, int maxX, int maxY) {
        super(x, y, radius);
        this.maxX = maxX + (int) radius;
        this.maxY = maxY + (int) radius;
        Random rnd = new Random();
        xSpeed = (rnd.nextDouble() - 0.5) * 12;
        ySpeed = (rnd.nextDouble() - 0.5) * 12;
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))),
                new Stop(1, Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
        setFill(lg);
    }

    public void go() {
        double newX = getCenterX() + xSpeed;
        double newY = getCenterY() + ySpeed;
        boolean collision = false;

        if (newX <= getRadius() / 2.0 || newX > maxX) {
            xSpeed = -xSpeed;
            collision = true;
        }

        if (newY <= getRadius() / 2.0 || newY > maxY) {
            ySpeed = -ySpeed;
            collision = true;
        }

        if (!collision) {
            setCenterX(newX);
            setCenterY(newY);
        }
    }
}
