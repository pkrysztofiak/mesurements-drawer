package pl.pkrysztofiak.mesurementsdrawer.view;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class FocusedApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        

        Circle circle1 = new Circle(16, Color.AQUAMARINE);
        circle1.setLayoutX(16);
        circle1.setLayoutY(16);
        
        Pane pane1 = new Pane(circle1);
        pane1.setPickOnBounds(false);
        JavaFxObservable.valuesOf(circle1.focusedProperty()).filter(Boolean.TRUE::equals).subscribe(next -> System.out.println("circle1 focused"));
        JavaFxObservable.eventsOf(circle1, MouseEvent.MOUSE_CLICKED).subscribe(event -> {
            System.out.println("circle1 clicked");
            circle1.requestFocus();
        });
        
        JavaFxObservable.eventsOf(pane1, MouseEvent.MOUSE_MOVED).subscribe(next -> System.out.println("pane1 MOUSE_MOVED"));
        
        Circle circle2 = new Circle(16, Color.BLUEVIOLET);
        circle2.setLayoutX(64);
        circle2.setLayoutY(64);
        
        Pane pane2 = new Pane(circle2);
        pane2.setPickOnBounds(false);
        JavaFxObservable.valuesOf(circle2.focusedProperty()).filter(Boolean.TRUE::equals).subscribe(next -> System.out.println("circle2 focused"));
        JavaFxObservable.eventsOf(circle2, MouseEvent.MOUSE_CLICKED).subscribe(event -> {
            System.out.println("circle2 clicked");
            circle2.requestFocus();
        });
        
        JavaFxObservable.eventsOf(pane2, MouseEvent.MOUSE_MOVED).subscribe(next -> System.out.println("pane2 MOUSE_MOVED"));
        
        StackPane stackPane = new StackPane(pane1, pane2);
        stackPane.setPrefSize(600, 400);
        
        stage.setScene(new Scene(stackPane));
        stage.show();
        
    }
}