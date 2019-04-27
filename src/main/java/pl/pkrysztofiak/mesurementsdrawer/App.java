package pl.pkrysztofiak.mesurementsdrawer;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.pkrysztofiak.mesurementsdrawer.controller.Controller;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.view.View;

public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        View view = new View();
        new Controller(model, view);
    }
}
