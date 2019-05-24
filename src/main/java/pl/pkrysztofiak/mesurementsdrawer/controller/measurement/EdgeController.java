package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.EdgeView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineEdgeView;

public class EdgeController {

	private final Point startPoint;
	private final Point endPoint;

	private final ObjectProperty<EdgeView> edgeViewProperty = new SimpleObjectProperty<>();
	private final Observable<EdgeView> edgeViewObservable = JavaFxObservable.valuesOf(edgeViewProperty);

	public EdgeController(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;

		LineEdgeView lineEdgeView = new LineEdgeView(startPoint, endPoint);
		edgeViewProperty.set(lineEdgeView);
	}

	public Observable<EdgeView> edgeViewObservable() {
		return edgeViewObservable;
	}
}