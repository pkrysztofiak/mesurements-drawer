package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.PolygonPointCircle;

public class PolygonUnfinishedDrawingBehaviour extends PolygonDrawingBehaviour {

    private final Behaviour behaviour = new Behaviour();

    private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);
    private final Observable<Point> pointRemovedObservable = JavaFxObservable.removalsOf(points);
    private final Observable<Integer> pointsSizeObservable = JavaFxObservable.emitOnChanged(points).map(List::size);

    public PolygonUnfinishedDrawingBehaviour() {
        initSubscriptions();
    }

    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
    }

    private Observable<Point> pointRemovedObservable() {
    	return pointRemovedObservable;
    }

    private class Behaviour {

        private void onPointAdded(Point point) {
        	PolygonPointCircle polygonPointCircle = new PolygonPointCircle(new Circle(8, Color.GREEN)) {

        		{
        			point.previousPointObservable().subscribe(optionalPoint -> {
        				if (optionalPoint.isPresent()) {
        					System.out.println("previous point present");
        					Point previousPoint = optionalPoint.get();
        					System.out.println("current x=" + point.getLayoutX() + ", y=" + point.getLayoutY());
        					System.out.println("previous x=" + previousPoint.getLayoutX() + ", y=" + previousPoint.getLayoutY());

        					Line line = new Line();
                			line.setStroke(Color.CYAN);

                			line.startXProperty().bindBidirectional(previousPoint.layoutXProperty());
                			line.startYProperty().bindBidirectional(previousPoint.layoutYProperty());

                			line.endXProperty().bindBidirectional(point.layoutXProperty());
                			line.endYProperty().bindBidirectional(point.layoutYProperty());

                			children.add(0, line);
        				}
        			});

        			JavaFxObservable.eventsOf(getNode(), MouseEvent.MOUSE_RELEASED)
        			.subscribe(mouseEvent -> {
        				System.out.println("przejąłem");
        				mouseEvent.consume();
        				points.get(0).setPreviousPoint(points.get(points.size() - 1));
        			});
        		}
        	};

        	Circle circle = polygonPointCircle.getNode();
            circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
            circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
            children.add(circle);
        }
    }
}