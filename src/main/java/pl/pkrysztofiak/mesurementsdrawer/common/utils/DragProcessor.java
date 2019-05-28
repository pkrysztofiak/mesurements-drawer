package pl.pkrysztofiak.mesurementsdrawer.common.utils;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;

public class DragProcessor {

	private final ObjectProperty<Double> startTranslateXProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Double> startTranslateYProperty = new SimpleObjectProperty<>();

	private final ObjectProperty<Double> translateXProperty = new SimpleObjectProperty<>();
	private final Observable<Double> translateXObservable = JavaFxObservable.valuesOf(translateXProperty);

	private final ObjectProperty<Double> layoutYProperty = new SimpleObjectProperty<>();
	private final Observable<Double> layoutYObservable = JavaFxObservable.valuesOf(layoutYProperty);

	private final PublishSubject<MouseEvent> mousePressedPublishable = PublishSubject.create();
	private final PublishSubject<MouseEvent> mouseDraggedPublishable = PublishSubject.create();
	private final PublishSubject<MouseEvent> mouseReleasedPublishable = PublishSubject.create();

//	private final Observable<Double> mousePressedXObservable = mousePressedPublishable.map(MouseEvent::getX);
	private final Observable<Double> mousePressedXObservable = mousePressedPublishable.map(MouseEvent::getSceneX);
//	private final Observable<Double> mousePressedYObservable = mousePressedPublishable.map(MouseEvent::getY);
	private final Observable<Double> mousePressedYObservable = mousePressedPublishable.map(MouseEvent::getSceneY);

//	private final Observable<Double> mouseDraggedXObservable = mouseDraggedPublishable.map(MouseEvent::getX);
	private final Observable<Double> mouseDraggedXObservable = mouseDraggedPublishable.map(MouseEvent::getSceneX);
//	private final Observable<Double> mouseDraggedYObservable = mouseDraggedPublishable.map(MouseEvent::getY);
	private final Observable<Double> mouseDraggedYObservable = mouseDraggedPublishable.map(MouseEvent::getSceneY);

	private final PublishSubject<Double> xResultPublishable = PublishSubject.create();
	private final PublishSubject<Double> yResultPublishable = PublishSubject.create();

	public DragProcessor() {

		mousePressedXObservable.switchMap(pressedX -> translateXObservable.take(1)).subscribe(startTranslateXProperty::set);
		mousePressedYObservable.switchMap(pressedX -> layoutYObservable.take(1)).subscribe(startTranslateYProperty::set);

		mousePressedXObservable.switchMap(pressedX ->
			mouseDraggedXObservable.map(draggedX -> processX(draggedX, pressedX)).takeUntil(mouseReleasedPublishable))
		.subscribe(xResultPublishable::onNext);

		mousePressedYObservable.switchMap(pressedY ->
			mouseDraggedYObservable.map(draggedY -> processY(draggedY, pressedY)).takeUntil(mouseReleasedPublishable))
		.subscribe(yResultPublishable::onNext);

		//DRAGG WHOLE MEASUREMENT
//		mousePressedXObservable.doOnNext(pressedX -> System.out.println("pressedX=" + pressedX)).switchMap(pressedX ->
//			mouseDraggedXObservable.doOnNext(draggedX -> System.out.println("draggedX=" + draggedX)).map(draggedX -> delta(draggedX, pressedX)).takeUntil(mouseReleasedPublishable))
//		.subscribe(xResultPublishable::onNext);

//		mousePressedYObservable.switchMap(pressedY ->
//			mouseDraggedYObservable.map(draggedY -> delta(draggedY, pressedY)).takeUntil(mouseReleasedPublishable))
//		.subscribe(yResultPublishable::onNext);
	}

	private double delta(double dragged, double pressed) {
		double delta = dragged - pressed;
		System.out.println("delta=" + delta);
		return delta;
	}

	private double processX(double draggedX, double pressedX) {
		return delta(draggedX, pressedX) + startTranslateXProperty.get();
	}

	private double processY(double draggedY, double pressedY) {
		return delta(draggedY, pressedY) + startTranslateYProperty.get();
	}

	public void setLayoutX(double value) {
		translateXProperty.set(value);
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

	public Observable<Double> xTranslateResultObservable() {
		return xResultPublishable;
	}

	public Observable<Double> yTranslateResultObservable() {
		return yResultPublishable;
	}

}
