package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class CirclePointView extends PointView {

	private final Circle circle = new Circle(8., Color.CYAN);

	public CirclePointView(Point point) {
		super(point);
		getChildren().add(circle);
		circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
		circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
	}
}