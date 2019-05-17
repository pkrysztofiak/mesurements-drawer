package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.ShapeView;

//TODO przepisać w generyka
public abstract class PointView extends ShapeView {

	protected final Point point;

	public PointView(Point point) {
		super();
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

	public abstract Observable<MouseEvent> mouseClickedObservable();
}