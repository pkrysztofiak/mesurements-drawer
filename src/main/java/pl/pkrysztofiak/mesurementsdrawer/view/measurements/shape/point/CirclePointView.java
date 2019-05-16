package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.behaviour.MouseClickedHandler;

public class CirclePointView extends PointView {

	private final Behaviour behaviour = new Behaviour();

	private final Paint hoverPaint = Color.CYAN;
	private final Paint paint = Color.BISQUE;

	private final Circle circle = new Circle(8., paint);
	private final Observable<Boolean> hoverObservable = JavaFxObservable.valuesOf(circle.hoverProperty());

	private final Observable<MouseEvent> mouseClickedObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_CLICKED);

	private final ObjectProperty<MouseClickedHandler<Point>> mouseClickedHandlerProperty = new SimpleObjectProperty<>();
//	private final Observable<MouseClickedHandler> mouseClickedHandlerObservable = JavaFxObservable.valuesOf(mouseClickedHandlerProperty);

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
		mouseClickedObservable.subscribe(behaviour::onMouseClicked);
	}

	public Observable<MouseEvent> mouseClickedObservable() {
		return mouseClickedObservable;
	}

	public void setMouseClickedHandler(MouseClickedHandler<Point> mouseClickedHandler) {
		mouseClickedHandlerProperty.set(mouseClickedHandler);
	}

	private class Behaviour {

		private void onHover(boolean value) {
			circle.setFill(value ? hoverPaint : paint);
		}

		public void onMouseClicked(MouseEvent mouseEvent) {
			System.out.println("inner click!");
			mouseEvent.consume();
//			Optional.ofNullable(mouseClickedHandlerProperty.get()).ifPresent(mouseClickedHandler -> mouseClickedHandler.onMouseClicked(point, mouseEvent));
		}
	}
}