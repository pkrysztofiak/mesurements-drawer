package pl.pkrysztofiak.fx.drag;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseEventUtils {

	public static Point2D toPoint2D(MouseEvent mouseEvent) {
		return new Point2D(mouseEvent.getX(), mouseEvent.getY());
	}
}
