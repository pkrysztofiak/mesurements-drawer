package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.PolygonPointCircle;

public class PolygonMeasurementView extends MeasurementView {

	private final Behaviour behaviour = new Behaviour();

//	private final ObjectProperty<PolygonMeasurement> polygonMeasurementProperty = new SimpleObjectProperty<>();
//	private final Observable<PolygonMeasurement> polygonMeasurmentObservable = JavaFxObservable.valuesOf(polygonMeasurementProperty);
//	private final Observable<Point> pointAddedObservable = polygonMeasurmentObservable.map(PolygonMeasurement::getPoints).switchMap(JavaFxObservable::additionsOf);

	public PolygonMeasurementView() {
//		polygonMeasurementProperty.set(polygonMeasurement);
//		initSubscriptions();
	}

//	private void initSubscriptions() {
//		pointAddedObservable.subscribe(behaviour::addPoint);
//	}

//	public PolygonMeasurement getPolygonMeasurement() {
//		return polygonMeasurementProperty.get();
//	}

	public void addPoint(Point point) {
		behaviour.addPoint(point);
	}

	private class Behaviour {

		private void addPoint(Point point) {
			PolygonPointCircle polygonPointCircle = new PolygonPointCircle(new Circle(8, Color.GREEN)) {

        		{
        			point.previousPointObservable().subscribe(optionalPoint -> {
        				if (optionalPoint.isPresent()) {
        					Point previousPoint = optionalPoint.get();

        					Line line = new Line();
                			line.setStroke(Color.CYAN);

                			line.startXProperty().bindBidirectional(previousPoint.layoutXProperty());
                			line.startYProperty().bindBidirectional(previousPoint.layoutYProperty());

                			line.endXProperty().bindBidirectional(point.layoutXProperty());
                			line.endYProperty().bindBidirectional(point.layoutYProperty());

//                			children.add(0, line);
                			getChildren().add(0, line);
        				}
        			});

        			JavaFxObservable.eventsOf(getNode(), MouseEvent.MOUSE_RELEASED)
        			.subscribe(mouseEvent -> {
        				System.out.println("CONSUMED");
        				mouseEvent.consume();
//        				points.get(0).setPreviousPoint(points.get(points.size() - 1));
        			});
        		}
        	};

        	Circle circle = polygonPointCircle.getNode();
            circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
            circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
//            children.add(circle);
            getChildren().add(circle);
		}
	}
}