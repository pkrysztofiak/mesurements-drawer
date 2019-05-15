package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public abstract class ShapeView extends Group {

	private final Observable<MouseEvent> mouseClickedObservable = JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_CLICKED);

	public ShapeView() {
		mouseClickedObservable.subscribe(this::onMouseClicked);
	}

	protected abstract void onMouseClicked(MouseEvent mouseEvent);
}
