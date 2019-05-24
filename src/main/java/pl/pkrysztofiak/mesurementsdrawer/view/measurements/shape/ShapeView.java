package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape;

import io.reactivex.Observable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public abstract class ShapeView extends Group {

	public abstract Observable<MouseEvent> mouseClickedObservable();
}
