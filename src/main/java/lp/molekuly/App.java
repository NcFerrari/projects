package lp.molekuly;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class App extends Application {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int RADIUS = 50;
    private static final int COUNT_OF_MOLECULES = 100;
    private static final int POSSIBLE_TRIES = 5;

    private final Set<Molecule> molecules = new HashSet<>();
    private final Random rnd = new Random();

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
        newSet();
        startThread();
    }

    private void startThread() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (!stop) {
                    Platform.runLater(() -> molecules.forEach(Molecule::go));
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
        molecules.clear();
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
            for (Molecule molecule : molecules) {
                if (RADIUS > getDistance(newX, newY, molecule.getCenterX(), molecule.getCenterY())) {
                    break;
                }
                notCollisionMolecule++;
            }
        } while (notCollisionMolecule < molecules.size());
        addMolecule(newX, newY);
    }

    private void addMolecule(double newX, double newY) {
        Molecule molecule = new Molecule(newX, newY, RADIUS / 2.0, maxX, maxY);
        molecules.add(molecule);
        pane.getChildren().add(molecule);
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        double a = Math.abs(x2 - x1);
        double b = Math.abs(y2 - y1);
        return Math.sqrt(a * a + b * b);
    }
}
