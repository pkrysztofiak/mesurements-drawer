package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.onmouseclicked;

import java.util.Optional;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class OnMouseClickedFirst extends OnMouseClickedBase {

	public OnMouseClickedFirst(Point point) {
		super(point);
	}

	@Override
	public void onMouseClicked(MouseEvent mouseEvent) {
		mouseEvent.consume();
		Point endPoint = findEndPoint(point);
		endPoint.setNextPoint(point);
	}

	private Point findEndPoint(Point point) {
		Optional<Point> optionalNextPoint = point.getNextPoint();
		if (optionalNextPoint.isPresent()) {
			return findEndPoint(optionalNextPoint.get());
		}
		return point;
	}
}