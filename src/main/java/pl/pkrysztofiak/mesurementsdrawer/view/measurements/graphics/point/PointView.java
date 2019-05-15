package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.point;

import javafx.scene.Group;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public abstract class PointView extends Group {

	protected final Point point;

	public PointView(Point point) {
		this.point = point;
	}
}