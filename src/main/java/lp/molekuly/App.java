package lp.molekuly;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lp.Manager;

import java.util.Random;

public class App extends Application {

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 1000;
    private static final int RADIUS = 100;
    private static final int COUNT_OF_MOLECULES = 20;
    private static final int POSSIBLE_TRIES = 5;

    private final Random rnd = new Random();
    private final Manager manager = Manager.getInstance();

    private int maxX;
    private int maxY;
    private Pane pane;
    private boolean stop;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Molekuly");
        pane = new Pane();
        pane.setBackground(Background.fill(Color.BLACK));
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setOnKeyPressed(keyEvent -> {
            stop = !stop;
            if (!stop) {
                startThread();
            }
        });
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.show();

        setMax();
        addRemovingCircle();
        newSet();
        startThread();
    }

    private void addRemovingCircle() {
        Circle circle = new Circle();
        circle.setStroke(Color.RED);
        circle.setStrokeWidth(10);
        circle.setRadius(RADIUS / 2.0 + 20);
        circle.setCenterX(circle.getRadius() + 5);
        circle.setCenterY(circle.getRadius() + 5);
        pane.getChildren().add(circle);
    }

    private void startThread() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (!stop) {
                    manager.getMolecules().forEach(molecule -> Platform.runLater(() -> {
                        if (molecule.getCenterX() <= RADIUS && molecule.getCenterY() <= RADIUS) {
                            molecule.stop();
                            molecule.fallDown(pane);
                            manager.getMolecules().remove(molecule);
                            next();
                        } else {
                            molecule.go();
                        }
                    }));
                    Thread.sleep(20);
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private void setMax() {
        maxX = WIDTH - RADIUS;
        maxY = HEIGHT - RADIUS;
    }

    private void newSet() {
        manager.getMolecules().clear();
        for (int i = 0; i < COUNT_OF_MOLECULES; i++) {
            next();
        }
    }

    private void next() {
        double newX;
        double newY;
        int notCollisionMolecule;
        int step = 0;
        do {
            if (step++ == POSSIBLE_TRIES) {
                return;
            }
            newX = RADIUS / 2.0 + rnd.nextInt(maxX);
            newY = RADIUS / 2.0 + rnd.nextInt(maxY);
            notCollisionMolecule = 0;
            for (Molecule molecule : manager.getMolecules()) {
                if (RADIUS > molecule.getDistance(newX, newY)) {
                    break;
                }
                notCollisionMolecule++;
            }
        } while (notCollisionMolecule < manager.getMolecules().size());
        addMolecule(newX, newY);
    }

    private void addMolecule(double newX, double newY) {
        Molecule molecule = new Molecule(newX, newY, RADIUS / 2.0, maxX, maxY);
        manager.getMolecules().add(molecule);
        pane.getChildren().add(molecule);
    }
}
