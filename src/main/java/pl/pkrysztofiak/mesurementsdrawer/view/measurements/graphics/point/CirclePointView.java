package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class CirclePointView extends PointView {

	public CirclePointView(Point point) {
		super(point);
		getChildren().add(new Circle(8, Color.CYAN));
	}
}