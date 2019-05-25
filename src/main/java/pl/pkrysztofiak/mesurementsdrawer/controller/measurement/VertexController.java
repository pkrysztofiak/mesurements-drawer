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

	private final Observable<MouseEvent> mousePressedObservable = vertexViewObservable.switchMap(VertexView::mousePressedObservable);
	private final Observable<MouseEvent> mouseDraggedObservable = vertexViewObservable.switchMap(VertexView::mouseDraggedObservable);
//	private final Observable<Bounds> layoutBoundsObservable = vertexViewObservable.switchMap(VertexView::layoutBoundsObservable);

	private final Observable<Double> mousePressedXObservable = mousePressedObservable.map(MouseEvent::getX);
	private final Observable<Double> mousePressedYObservable = mousePressedObservable.map(MouseEvent::getY);

	private final Observable<Double> mouseDraggedXObservable = mouseDraggedObservable.map(MouseEvent::getX);
	private final Observable<Double> mouseDraggedYObservable = mouseDraggedObservable.map(MouseEvent::getY);

	private final Observable<Double> layoutXObservable = vertexViewObservable.switchMap(VertexView::layoutXObservable);
	private final Observable<Double> layoutYObservable = vertexViewObservable.switchMap(VertexView::layoutYObservable);

	public VertexController(Point point) {
		this.point = point;
		initSubscriptins();

		vertexViewProperty.set(new CirclePointView(point));

		mousePressedXObservable.switchMap(pressedX -> mouseDraggedXObservable.withLatestFrom(layoutXObservable.take(1), (draggedX, layoutX) -> {
			System.out.println("draggedX=" + draggedX + ", pressedX=" + pressedX + ", layoutX=" + layoutX);
			double delta = draggedX - pressedX;
			System.out.println("deltaX=" + delta);
			double result = delta + layoutX;
			return result;
		})).subscribe(point::setLayoutX);

		mousePressedYObservable.switchMap(pressedY -> mouseDraggedYObservable.withLatestFrom(layoutYObservable.take(1), (draggedY, layoutY) -> {
			System.out.println("draggedY=" + draggedY + ", pressedY=" + pressedY + ", layoutY=" + layoutY);
			double delta = draggedY - pressedY;
			System.out.println("deltaY=" + delta);
			double result = delta + layoutY;
			return result;
		})).subscribe(point::setLayoutY);
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

	private double translate(double draggedY, double pressedY, double layoutY) {
		System.out.println("draggedY=" + draggedY + ", pressedY=" + pressedY + ", layoutY=" + layoutY);
		double delta = draggedY - pressedY;
		System.out.println("deltaY=" + delta);
		double result = delta + layoutY;
		return result;
	}

	class Behaviour {

	}
}