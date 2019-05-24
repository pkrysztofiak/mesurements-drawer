package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line;

import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.ShapeView;

public abstract class EdgeView extends ShapeView {

	private final Point startPoint;
	private final Point endPoint;

	public EdgeView(Point startPoint, Point endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}
}