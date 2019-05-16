package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.behaviour;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class PointMouseClickedHandlerLazy implements MouseClickedHandler<Point> {

	@Override
	public void onMouseClicked(Point point, MouseEvent mouseEvent) {
		mouseEvent.consume();
	}
}