package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape;

import javafx.scene.Group;

public abstract class ShapeView extends Group {

//	private final ObjectProperty<MouseClickedHandler> mouseClickedHandlerProperty = new SimpleObjectProperty<>();
//	private final Observable<MouseClickedHandler> mouseClickedHandlerObservable = JavaFxObservable.valuesOf(mouseClickedHandlerProperty);

//	private final Observable<MouseEvent> mouseClickedObservable = JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_CLICKED);

	public ShapeView() {
//		mouseClickedHandlerObservable.switchMap(mouseClickedHandler -> mouseClickedObservable.doOnNext(mouseClickedHandler::onMouseClicked)).subscribe();
	}

//	protected void setMouseClickedHandler(MouseClickedHandler mouseClickedHandler) {
//		mouseClickedHandlerProperty.set(mouseClickedHandler);
//	}

//	public abstract void onMouseClicked(MouseEvent mouseEvent);
}
