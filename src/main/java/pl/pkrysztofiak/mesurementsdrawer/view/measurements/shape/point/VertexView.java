package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.ShapeView;

//TODO przepisać w generyka
public abstract class VertexView extends ShapeView {

	protected final Point point;

	public VertexView(Point point) {
		super();
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
}