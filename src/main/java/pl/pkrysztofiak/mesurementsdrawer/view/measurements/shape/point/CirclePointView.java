package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class CirclePointView extends PointView {

	private final Behaviour behaviour = new Behaviour();

	private final Paint hoverPaint = Color.CYAN;
	private final Paint paint = Color.BISQUE;

	private final Circle circle = new Circle(8., paint);
	private final Observable<Boolean> hoverObservable = JavaFxObservable.valuesOf(circle.hoverProperty());
	private final Observable<MouseEvent> mouseReleasedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_RELEASED).doOnNext(MouseEvent::consume);

	{
		getChildren().add(circle);
	}

	public CirclePointView(Point point) {
		super(point);
		circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
		circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
		initSubscriptions();
	}

	private void initSubscriptions() {
		hoverObservable.subscribe(behaviour::onHover);
	}

	private class Behaviour {

		private void onHover(boolean value) {
			circle.setFill(value ? hoverPaint : paint);
		}
	}

	@Override
	public Observable<MouseEvent> mouseReleasedObservable() {
		return mouseReleasedObservable;
	}
}