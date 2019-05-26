package pl.pkrysztofiak.mesurementsdrawer.common.utils;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;

public class DragProcessor {

	private final ObjectProperty<Double> startLayoutXProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Double> startLayoutYProperty = new SimpleObjectProperty<>();

	private final ObjectProperty<Double> layoutXProperty = new SimpleObjectProperty<>();
	private final Observable<Double> layoutXObservable = JavaFxObservable.valuesOf(layoutXProperty);

	private final ObjectProperty<Double> layoutYProperty = new SimpleObjectProperty<>();
	private final Observable<Double> layoutYObservable = JavaFxObservable.valuesOf(layoutYProperty);

	private final PublishSubject<MouseEvent> mousePressedPublishable = PublishSubject.create();
	private final PublishSubject<MouseEvent> mouseDraggedPublishable = PublishSubject.create();
	private final PublishSubject<MouseEvent> mouseReleasedPublishable = PublishSubject.create();

	private final Observable<Double> mousePressedXObservable = mousePressedPublishable.map(MouseEvent::getX);
	private final Observable<Double> mousePressedYObservable = mousePressedPublishable.map(MouseEvent::getY);

	private final Observable<Double> mouseDraggedXObservable = mouseDraggedPublishable.map(MouseEvent::getX);
	private final Observable<Double> mouseDraggedYObservable = mouseDraggedPublishable.map(MouseEvent::getY);

	private final PublishSubject<Double> xResultPublishable = PublishSubject.create();
	private final PublishSubject<Double> yResultPublishable = PublishSubject.create();

	public DragProcessor() {

		mousePressedXObservable.switchMap(pressedX -> layoutXObservable.take(1)).subscribe(startLayoutXProperty::set);
		mousePressedYObservable.switchMap(pressedX -> layoutYObservable.take(1)).subscribe(startLayoutYProperty::set);

		mousePressedXObservable.switchMap(pressedX ->
			mouseDraggedXObservable.map(draggedX -> processX(draggedX, pressedX)).takeUntil(mouseReleasedPublishable))
		.subscribe(xResultPublishable::onNext);

		mousePressedYObservable.switchMap(pressedY ->
			mouseDraggedYObservable.map(draggedY -> processY(draggedY, pressedY)).takeUntil(mouseReleasedPublishable))
		.subscribe(yResultPublishable::onNext);
	}

	private double delta(double dragged, double pressed) {
		return dragged - pressed;
	}

	private double processX(double draggedX, double pressedX) {
		return delta(draggedX, pressedX) + startLayoutXProperty.get();
	}

	private double processY(double draggedY, double pressedY) {
		return delta(draggedY, pressedY) + startLayoutYProperty.get();
	}

	public void setLayoutX(double value) {
		layoutXProperty.set(value);
	}

	public void setLayoutY(double value) {
		layoutYProperty.set(value);
	}

	public PublishSubject<MouseEvent> mousePressedPublishable() {
		return mousePressedPublishable;
	}

	public PublishSubject<MouseEvent> mouseDraggedPublishable() {
		return mouseDraggedPublishable;
	}

	public PublishSubject<MouseEvent> mouseReleasedPublishable() {
		return mouseReleasedPublishable;
	}

	public Observable<Double> xResultObservable() {
		return xResultPublishable;
	}

	public Observable<Double> yResultObservable() {
		return yResultPublishable;
	}

}
