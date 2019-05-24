package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class VertexController {

	private final Point point;

	private final ObjectProperty<VertexView> vertexViewProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Optional<VertexView>> vertexViewOptionalProperty = new SimpleObjectProperty<>(Optional.empty());

	private final Observable<VertexView> vertexViewObservable = JavaFxObservable.valuesOf(vertexViewProperty);
	private final Observable<Optional<VertexView>> vertexViewOptionalObservable = JavaFxObservable.nullableValuesOf(vertexViewProperty);
	private final Observable<Change<Optional<VertexView>>> vertexViewChangeObservable = JavaFxObservable.changesOf(vertexViewOptionalProperty);


	public VertexController(Point point) {
		this.point = point;
		initSubscriptins();

		vertexViewProperty.set(new CirclePointView(point));
	}

	private void initSubscriptins() {
		vertexViewOptionalObservable.subscribe(vertexViewOptionalProperty::set);
	}

	public Observable<VertexView> vertexViewObservable() {
		return vertexViewObservable;
	}

	public Observable<Change<Optional<VertexView>>> vertexViewChangeObservable() {
		return vertexViewChangeObservable;
	}

	public Observable<MouseEvent> mouseClickedObservable() {
		return vertexViewObservable.switchMap(VertexView::mouseClickedObservable);
	}

	public Point getPoint() {
		return point;
	}
}