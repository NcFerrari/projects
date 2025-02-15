package lp.souhvezdi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class Souhvezdi extends Application {

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        Scene scene = null;
        List<String> parameters = getParameters().getRaw();
        if (!parameters.isEmpty()) {
            switch (Variants.valueOf(parameters.getFirst())) {
                case Variants.A:
                    scene = new VariantaA(pane, 1300, 1000);
                    break;
                default:
                    scene = new Scene(pane, 1300, 1000);
            }
        }
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.show();
    }
}
