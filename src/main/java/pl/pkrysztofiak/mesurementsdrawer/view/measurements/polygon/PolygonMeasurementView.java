package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;

import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.line.ConnectorView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.line.LineView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.point.PointView;

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

	public void addPointView(PointView pointView) {
		getChildren().add(pointView);
	}

	public void addLineView(LineView lineView) {
		getChildren().add(0, lineView);
	}

//	public void addLine(Point startPoint, Point endPoint) {
//		behaviour.addLine(startPoint, endPoint);
//	}

	public void addLineView(ConnectorView lineView) {
		getChildren().add(lineView);
	}

	public void addLine(Point startPoint) {
		behaviour.addLine(startPoint);
	}

	private class Behaviour {

		private void addLine(Point startPoint) {
//			LineView lineView = new LineView(startPoint);
//			getChildren().add(lineView);
		}

//		private void addLine(Point startPoint, Point endPoint) {
//			LineView lineView = new LineView(startPoint, endPoint);
//			getChildren().add(0, lineView);
//		}

		private void addPoint(Point point) {
			PointView pointView = new CirclePointView(point);
			getChildren().add(pointView);


//			PolygonPointCircle polygonPointCircle = new PolygonPointCircle(new Circle(8, Color.GREEN)) {
//
//        		{
//        			point.previousPointObservable().subscribe(optionalPoint -> {
//        				if (optionalPoint.isPresent()) {
//        					Point previousPoint = optionalPoint.get();
//
//        					Line line = new Line();
//                			line.setStroke(Color.CYAN);
//
//                			line.startXProperty().bindBidirectional(previousPoint.layoutXProperty());
//                			line.startYProperty().bindBidirectional(previousPoint.layoutYProperty());
//
//                			line.endXProperty().bindBidirectional(point.layoutXProperty());
//                			line.endYProperty().bindBidirectional(point.layoutYProperty());
//
////                			children.add(0, line);
//                			getChildren().add(0, line);
//        				}
//        			});
//
//        			JavaFxObservable.eventsOf(getNode(), MouseEvent.MOUSE_RELEASED)
//        			.subscribe(mouseEvent -> {
//        				System.out.println("CONSUMED");
//        				mouseEvent.consume();
////        				points.get(0).setPreviousPoint(points.get(points.size() - 1));
//        			});
//        		}
//        	};
//
//        	Circle circle = polygonPointCircle.getNode();
//            circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
//            circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
////            children.add(circle);
//            getChildren().add(circle);
		}
	}
}