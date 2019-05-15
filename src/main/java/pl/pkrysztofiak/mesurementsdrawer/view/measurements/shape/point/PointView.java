package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

//TODO przepisać w generyka
public abstract class PointView extends Group {

	protected final Point point;

	public PointView(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

	public abstract Observable<MouseEvent> mouseReleasedObservable();

}