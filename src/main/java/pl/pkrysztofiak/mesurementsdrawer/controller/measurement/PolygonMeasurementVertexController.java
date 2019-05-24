package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class PolygonMeasurementVertexController {

	private final Point point;

	private final ObjectProperty<VertexView> vertexViewProperty = new SimpleObjectProperty<>();
	private final Observable<VertexView> vertexViewObservable = JavaFxObservable.valuesOf(vertexViewProperty);

	public PolygonMeasurementVertexController(Point point) {
		this.point = point;

		CirclePointView pointView = new CirclePointView(point);
		vertexViewProperty.set(pointView);
	}


	public Observable<VertexView> vertexViewObservable() {
		return vertexViewObservable;
	}

	public Observable<Point> mouseClickedObservable() {
		return vertexViewObservable.switchMap(VertexView::mouseClickedObservable).map(mouseClicked -> point);
	}
}