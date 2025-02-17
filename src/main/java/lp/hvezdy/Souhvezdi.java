package lp.hvezdy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Souhvezdi extends Application {

    private static final double STAR_SIZE = 20;
    private static final double MAX_WIDTH = 1300;
    private static final double MAX_HEIGHT = 1000;
    private Stage stage;

    public static double getStarSize() {
        return STAR_SIZE;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        List<String> parameters = getParameters().getRaw();
        if (!parameters.isEmpty()) {
            setVariant(Variants.valueOf(parameters.getFirst()));
        }
        stage.setOnCloseRequest(windowEvent -> System.exit(0));

        stage.show();
    }

    private void setVariant(Variants variant) {
        Scene scene;
        Pane pane = new Pane();
        pane.setPrefSize(MAX_WIDTH, MAX_HEIGHT);
        pane.setBackground(Background.fill(Color.BLACK));
        scene = switch (variant) {
            case Variants.A -> new VariantaA(pane);
            case Variants.B -> new VariantaB(pane);
            case Variants.C -> new VariantaC(pane);
            case Variants.D -> new VariantaD(pane);
            case Variants.E -> new VariantaE(pane);
            case Variants.F -> new VariantaF(pane);
            default -> {
                pane.setBackground(Background.fill(Color.GRAY));
                yield new Scene(pane, MAX_WIDTH, MAX_HEIGHT);
            }
        };
        scene.setOnKeyPressed(keyEvent -> {
            String letter = keyEvent.getText().toUpperCase();
            for (Variants v : Variants.values()) {
                if (letter.equals(v.name())) {
                    setVariant(Variants.valueOf(letter));
                    break;
                }
            }
            if (scene instanceof VariantaD varianta) {
                varianta.setOnSwitching();
            }
        });
        stage.setScene(scene);
    }
}
