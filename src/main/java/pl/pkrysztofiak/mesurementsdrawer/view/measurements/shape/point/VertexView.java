package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.ShapeView;

public abstract class VertexView extends ShapeView {

	private final Observable<MouseEvent> mouseAnyObservable = JavaFxObservable.eventsOf(this, MouseEvent.ANY);

	protected final Point point;

	public VertexView(Point point) {
		super();
		this.point = point;

		mouseAnyObservable.subscribe(MouseEvent::consume);
	}

	public Point getPoint() {
		return point;
	}

	public abstract Observable<MouseEvent> mouseClickedObservable();
	public abstract Observable<MouseEvent> mouseDraggedObservable();
	public abstract Observable<MouseEvent> mousePressedObservable();
	public abstract Observable<MouseEvent> mouseReleasedObservable();
	public abstract Observable<Double> layoutXObservable();
	public abstract Observable<Double> layoutYObservable();
	public abstract Node getDraggableNode();


}