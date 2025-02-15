package lp.souhvezdi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Souhvezdi extends Application {

    private static final double STAR_SIZE = 20;
    private double maxWidth = 1300;
    private double maxHeight = 1000;
    private Stage stage;
    private Pane pane;

    public static double getStarSize() {
        return STAR_SIZE;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
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
        pane = new Pane();
        events();
        pane.setBackground(Background.fill(Color.BLACK));
        switch (variant) {
            case Variants.A:
                scene = new VariantaA(pane, maxWidth, maxHeight, this);
                break;
            case Variants.B:
                scene = new VariantaB(pane, maxWidth, maxHeight, this);
                break;
            default:
                pane.setBackground(Background.fill(Color.GRAY));
                scene = new Scene(pane, maxWidth, maxHeight);
        }
        scene.setOnKeyPressed(keyEvent -> {
            String letter = keyEvent.getText().toUpperCase();
            for (Variants v : Variants.values()) {
                if (letter.equals(v.name())) {
                    setVariant(Variants.valueOf(letter));
                    break;
                }
            }
        });
        stage.setScene(scene);
    }

    private void events() {
        pane.widthProperty().addListener((observableValue, number, t1) -> setMaxWidth(t1.doubleValue()));
        pane.heightProperty().addListener((observableValue, number, t1) -> setMaxHeight(t1.doubleValue()));
    }
}
