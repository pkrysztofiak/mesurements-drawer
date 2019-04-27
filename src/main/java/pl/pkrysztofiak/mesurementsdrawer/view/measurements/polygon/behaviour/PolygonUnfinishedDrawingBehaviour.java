package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.CircleBehaviour;

public class PolygonUnfinishedDrawingBehaviour extends PolygonDrawingBehaviour {
    
    private final Behaviour behaviour = new Behaviour();
    
    private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);
    private final Observable<List<Point>> pointsAddedObservable = pointAddedObservable.buffer(2, 1);
    private final Observable<Point> pointRemovedObservable = JavaFxObservable.removalsOf(points);
    private final Observable<Integer> pointsSizeObservale = JavaFxObservable.emitOnChanged(points).map(List::size);
    
    public PolygonUnfinishedDrawingBehaviour() {
        initSubscriptions();
    }
    
    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
        pointsAddedObservable.subscribe(points -> behaviour.onPointsAdded(points.get(0), points.get(1)));
    }
    
    private class Behaviour {
        
        private void onPointAdded(Point point) {
            
            Circle circle = new Circle(8, Color.BLUEVIOLET) {
                private final CircleBehaviour circleBehaviour = new CircleBehaviour(this);
                
                private final Observable<Optional<Point>> previousPointObservable = JavaFxObservable.valuesOf(point.previousPointProperty())
                        .filter(Optional.empty()::equals).takeUntil(pointRemovedObservable.filter(point::equals));
                
                {
                    JavaFxObservable.valuesOf(hoverProperty()).subscribe(circleBehaviour.blueOnHover());
                    
                    pointsSizeObservale.filter(size -> size >= 3).switchMap(size -> previousPointObservable).subscribe(point -> onFirstPoint());
                }
                
                private void onFirstPoint() {
                    
                }
            };
            
            
            
            
            
            circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
            circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
            children.add(circle);
        }
        
        
        
        private void onPointsAdded(Point previousPoint, Point point) {
            Line line = new Line();
            line.setStroke(Color.CYAN);
            line.startXProperty().bindBidirectional(previousPoint.layoutXProperty());
            line.startYProperty().bindBidirectional(previousPoint.layoutYProperty());
            
            line.endXProperty().bindBidirectional(point.layoutXProperty());
            line.endYProperty().bindBidirectional(point.layoutYProperty());
            
            children.add(0, line);
        }
    }
}