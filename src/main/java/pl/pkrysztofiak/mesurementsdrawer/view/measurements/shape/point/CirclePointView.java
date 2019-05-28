package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class CirclePointView extends VertexView {

	private final Behaviour behaviour = new Behaviour();

	private final Paint hoverPaint = Color.CYAN;
	private final Paint paint = Color.BISQUE;

	private final Circle circle = new Circle(8., paint);

	private final Observable<Boolean> hoverObservable = JavaFxObservable.valuesOf(circle.hoverProperty());

	private final Observable<MouseEvent> mouseClickedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_CLICKED);

	private final Observable<MouseEvent> mousePressedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_PRESSED);
	private final Observable<MouseEvent> mouseDraggedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_DRAGGED);
	private final Observable<MouseEvent> mouseReleasedObservale = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_RELEASED);

	private final Observable<Double> layoutXObservable = JavaFxObservable.valuesOf(circle.centerXProperty()).map(Number::doubleValue);
	private final Observable<Double> layoutYObservable = JavaFxObservable.valuesOf(circle.centerYProperty()).map(Number::doubleValue);

	private final Observable<Parent> parentObservable = JavaFxObservable.valuesOf(parentProperty());
	private final PublishSubject<MouseEvent> parentMouseDraggedPublishable = PublishSubject.create();

	public CirclePointView(Point point) {
		super(point);
		circle.translateXProperty().bindBidirectional(point.xTranslateProperty());
		circle.translateYProperty().bindBidirectional(point.layoutYProperty());

		initSubscriptions();
		getChildren().add(circle);

		parentObservable.subscribe(parent -> {
			parent.addEventFilter(MouseEvent.MOUSE_DRAGGED, parentMouseDraggedPublishable::onNext);
		});
	}


	private void initSubscriptions() {
		hoverObservable.subscribe(behaviour::onHover);
	}

	@Override
	public Observable<MouseEvent> mouseDraggedObservable() {
		return mouseDraggedObservable.doOnNext(MouseEvent::consume);
//		return sceneMouseDraggedObservable;
//		return parentMouseDraggedPublishable;
	}

	@Override
	public Observable<MouseEvent> mouseClickedObservable() {
		return mouseClickedObservable;
	}

	@Override
	public Observable<MouseEvent> mousePressedObservable() {
		return mousePressedObservable;
	}

	@Override
	public Observable<Double> layoutXObservable() {
		return layoutXObservable;
	}

	@Override
	public Observable<Double> layoutYObservable() {
		return layoutYObservable;
	}

	@Override
	public Observable<MouseEvent> mouseReleasedObservable() {
		return mouseReleasedObservale;
	}

	@Override
	public Node getDraggableNode() {
		return circle;
	}

	private class Behaviour {

		private void onHover(boolean value) {
			circle.setFill(value ? hoverPaint : paint);
		}
	}




}