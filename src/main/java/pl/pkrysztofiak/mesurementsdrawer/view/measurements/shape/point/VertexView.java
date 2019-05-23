package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.ShapeView;

//TODO przepisać w generyka
public abstract class VertexView extends ShapeView implements MouseClickable<MouseEvent> {

	protected final Point point;

	public VertexView(Point point) {
		super();
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
}