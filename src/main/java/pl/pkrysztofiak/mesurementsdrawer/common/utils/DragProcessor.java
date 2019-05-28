package pl.pkrysztofiak.mesurementsdrawer.common.utils;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DragProcessor {

//	private final PublishSubject<MouseEvent> mousePressedPublishable = PublishSubject.create();
//	private final PublishSubject<MouseEvent> mouseDraggedPublishable = PublishSubject.create();
//	private final PublishSubject<MouseEvent> mouseReleasedPublishable = PublishSubject.create();
//
	private final ObjectProperty<Double> startTranslateXProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Double> startTranslateYProperty = new SimpleObjectProperty<>();
//
//	private final ObjectProperty<Double> translateXProperty = new SimpleObjectProperty<>();
//	private final Observable<Double> xTranslateObservable = JavaFxObservable.valuesOf(translateXProperty);
//
//	private final ObjectProperty<Double> layoutYProperty = new SimpleObjectProperty<>();
//	private final Observable<Double> yTranslateObservable = JavaFxObservable.valuesOf(layoutYProperty);
//
//
//	private final Observable<Double> mousePressedXObservable = mousePressedPublishable.map(MouseEvent::getSceneX);
//	private final Observable<Double> mousePressedYObservable = mousePressedPublishable.map(MouseEvent::getSceneY);
//
//	private final Observable<Double> mouseDraggedXObservable = mouseDraggedPublishable.map(MouseEvent::getSceneX);
//	private final Observable<Double> mouseDraggedYObservable = mouseDraggedPublishable.map(MouseEvent::getSceneY);
//
	private final PublishSubject<Double> translateXResultPublishable = PublishSubject.create();
	private final PublishSubject<Double> translateYResultPublishable = PublishSubject.create();

	private final ObjectProperty<Node> nodeProperty = new SimpleObjectProperty<>();
	private final Observable<Node> nodeObservable = JavaFxObservable.valuesOf(nodeProperty);

	private final Observable<MouseEvent> mousePressedObservable = nodeObservable.switchMap(node -> JavaFxObservable.eventsOf(node, MouseEvent.MOUSE_PRESSED));
	private final Observable<MouseEvent> mouseDraggedObservable = nodeObservable.switchMap(node -> JavaFxObservable.eventsOf(node, MouseEvent.MOUSE_DRAGGED));
	private final Observable<MouseEvent> mouseReleasedObservable = nodeObservable.switchMap(node -> JavaFxObservable.eventsOf(node, MouseEvent.MOUSE_RELEASED));
	private final Observable<Double> translateXObservable = nodeObservable.switchMap(node -> JavaFxObservable.valuesOf(node.translateXProperty()).map(Number::doubleValue));
	private final Observable<Double> translateYObservable = nodeObservable.switchMap(node -> JavaFxObservable.valuesOf(node.translateYProperty()).map(Number::doubleValue));

	private final Observable<Double> mousePressedXObservable = mousePressedObservable.map(MouseEvent::getSceneX).map(Number::doubleValue);
	private final Observable<Double> mousePressedYObservable = mousePressedObservable.map(MouseEvent::getSceneY).map(Number::doubleValue);

	private final Observable<Double> mouseDraggedXObservable = mouseDraggedObservable.map(MouseEvent::getSceneX).map(Number::doubleValue);
	private final Observable<Double> mouseDraggedYObservable = mouseDraggedObservable.map(MouseEvent::getSceneY).map(Number::doubleValue);

//	private final Observable<Double> translateXObservale = nodeObservable.map(Node::translateXProperty).switchMap(JavaFxObservable::valuesOf);

	public DragProcessor() {

		mousePressedXObservable.switchMap(pressedX -> translateXObservable.take(1)).subscribe(startTranslateXProperty::setValue);
		mousePressedYObservable.switchMap(pressedX -> translateYObservable.take(1)).subscribe(startTranslateYProperty::setValue);

		mousePressedXObservable.switchMap(pressedX ->
			mouseDraggedXObservable.map(draggedX -> processX(draggedX, pressedX)).takeUntil(mouseReleasedObservable))
		.subscribe(translateXResultPublishable::onNext);

		mousePressedYObservable.switchMap(pressedY ->
			mouseDraggedYObservable.map(draggedY -> processY(draggedY, pressedY)).takeUntil(mouseReleasedObservable))
		.subscribe(translateYResultPublishable::onNext);

		//DRAGG WHOLE MEASUREMENT
//		mousePressedXObservable.doOnNext(pressedX -> System.out.println("pressedX=" + pressedX)).switchMap(pressedX ->
//			mouseDraggedXObservable.doOnNext(draggedX -> System.out.println("draggedX=" + draggedX)).map(draggedX -> delta(draggedX, pressedX)).takeUntil(mouseReleasedPublishable))
//		.subscribe(xResultPublishable::onNext);

//		mousePressedYObservable.switchMap(pressedY ->
//			mouseDraggedYObservable.map(draggedY -> delta(draggedY, pressedY)).takeUntil(mouseReleasedPublishable))
//		.subscribe(yResultPublishable::onNext);
	}

	public void setNode(Node node) {
		nodeProperty.set(node);
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

//	public void setLayoutX(double value) {
//		translateXProperty.set(value);
//	}
//
//	public void setLayoutY(double value) {
//		layoutYProperty.set(value);
//	}
//
//	public PublishSubject<MouseEvent> mousePressedPublishable() {
//		return mousePressedPublishable;
//	}
//
//	public PublishSubject<MouseEvent> mouseDraggedPublishable() {
//		return mouseDraggedPublishable;
//	}
//
//	public PublishSubject<MouseEvent> mouseReleasedPublishable() {
//		return mouseReleasedPublishable;
//	}

	public Observable<Double> translateXResultObservable() {
		return translateXResultPublishable;
	}

	public Observable<Double> translateYResultObservable() {
		return translateYResultPublishable;
	}

}
