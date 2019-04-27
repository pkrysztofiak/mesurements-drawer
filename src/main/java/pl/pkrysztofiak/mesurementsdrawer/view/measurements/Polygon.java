package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Polygon extends Pane {

    private final Behaviour behaviour = new Behaviour();
    
    private final ObservableList<Point2D> points = FXCollections.observableArrayList();
    private final Observable<Point2D> pointAddedObservable = JavaFxObservable.additionsOf(points);
    
    private final ObservableList<Circle> circles = FXCollections.observableArrayList();
    private final Observable<Circle> circleAddedObservable = JavaFxObservable.additionsOf(circles);
    
    private final ObservableList<Line> lines = FXCollections.observableArrayList();
    private final Observable<Line> lineAddedObservable = JavaFxObservable.additionsOf(lines);
    
    public Polygon() {
        initSubscriptions();
    }
    
    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
        
        lineAddedObservable.subscribe(line -> getChildren().add(0, line));
        
        circleAddedObservable.subscribe(getChildren()::add);
        
        circleAddedObservable.buffer(2, 1).subscribe(circles -> behaviour.onNextCircleAdded(circles.get(0), circles.get(1)));
        
        circleAddedObservable
        .switchMap(circle -> 
            JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_RELEASED)
            .map(event -> circle)
            .withLatestFrom(Observable.just(circles.get(circles.size() - 1)), (lastCircle, firstCircle) -> Arrays.asList(lastCircle, firstCircle)))
        .subscribe(circles -> behaviour.onNextCircleAdded(circles.get(0), circles.get(1)));
            
            
        
    }
    
    public ObservableList<Point2D> getPoints() {
        return points;
    }
    
    private class Behaviour {
        
        private void onPointAdded(Point2D point) {
            Circle circle = new Circle(8, Color.BLUEVIOLET);
            circle.setLayoutX(point.getX());
            circle.setLayoutY(point.getY());
            circles.add(circle);
            
            JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_RELEASED).subscribe(MouseEvent::consume);
        }
        
        private void onNextCircleAdded(Circle point, Circle nextPoint) {
            System.out.println("next point added");
            Line line = new Line();
            line.setStroke(Color.CHARTREUSE);
            lines.add(line);
            Bindings.bindBidirectional(line.startXProperty(), point.layoutXProperty());
            Bindings.bindBidirectional(line.startYProperty(), point.layoutYProperty());
            Bindings.bindBidirectional(line.endXProperty(), nextPoint.layoutXProperty());
            Bindings.bindBidirectional(line.endYProperty(), nextPoint.layoutYProperty());
        }
    }
}
