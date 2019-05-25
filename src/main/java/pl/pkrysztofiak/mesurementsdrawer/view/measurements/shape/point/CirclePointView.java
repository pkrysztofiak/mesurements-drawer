package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.geometry.Bounds;
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
	private final Observable<Bounds> layoutBoundsObservable = JavaFxObservable.valuesOf(circle.layoutBoundsProperty());

	private final Observable<MouseEvent> mouseClickedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_CLICKED);

	private final Observable<MouseEvent> mousePressedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_PRESSED);
	private final Observable<MouseEvent> mouseDraggedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_DRAGGED);

	private final Observable<Double> layoutXObservable = JavaFxObservable.valuesOf(circle.centerXProperty()).map(Number::doubleValue);
	private final Observable<Double> layoutYObservable = JavaFxObservable.valuesOf(circle.centerYProperty()).map(Number::doubleValue);

	public CirclePointView(Point point) {
		super(point);
		circle.centerXProperty().bindBidirectional(point.layoutXProperty());
		circle.centerYProperty().bindBidirectional(point.layoutYProperty());

//		layoutXProperty().bindBidirectional(point.layoutXProperty());
//		circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
		initSubscriptions();
		getChildren().add(circle);
	}


	private void initSubscriptions() {
		hoverObservable.subscribe(behaviour::onHover);
	}

	@Override
	public Observable<MouseEvent> mouseDraggedObservable() {
		return mouseDraggedObservable.doOnNext(MouseEvent::consume);
	}

	@Override
	public Observable<MouseEvent> mouseClickedObservable() {
		return mouseClickedObservable.doOnNext(mouseEvent -> {
			mouseEvent.consume();
			System.out.println("mouseClicked consumed");
		});
	}

	@Override
	public Observable<MouseEvent> mousePressedObservable() {
		return mousePressedObservable.doOnNext(mouseEvent -> {
			mouseEvent.consume();
			System.out.println("mousePressed consumed");
		});
	}

	@Override
	public Observable<Bounds> layoutBoundsObservable() {
		return layoutBoundsObservable;
	}

	@Override
	public Observable<Double> layoutXObservable() {
		return layoutXObservable;
	}

	@Override
	public Observable<Double> layoutYObservable() {
		return layoutYObservable;
	}

	private class Behaviour {

		private void onHover(boolean value) {
			circle.setFill(value ? hoverPaint : paint);
		}
	}

}