package pl.pkrysztofiak.fx.drag;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

public class BoundsUtils {

	public static Point2D toPoint2D(Bounds bounds) {
		return new Point2D(bounds.getMinX(), bounds.getMinY());
	}
}
