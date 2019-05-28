package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class LineEdgeView extends EdgeView {

	private final Line line = new Line();

	public LineEdgeView(Point startPoint, Point endPoint) {
		super(startPoint, endPoint);
		line.setStroke(Color.YELLOW);
		getChildren().add(line);
		line.startXProperty().bindBidirectional(startPoint.xTranslateProperty());
		line.startYProperty().bindBidirectional(startPoint.layoutYProperty());
		line.endXProperty().bindBidirectional(endPoint.xTranslateProperty());
		line.endYProperty().bindBidirectional(endPoint.layoutYProperty());
	}

}