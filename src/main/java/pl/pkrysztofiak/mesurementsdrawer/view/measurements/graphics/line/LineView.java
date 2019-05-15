package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class LineView extends ConnectorView {

	private Behaviour behaviour = new Behaviour();

	private Line line = new Line();

	{
		initSubscriptions();
	}

	public LineView(Point startPoint, Point endPoint) {
		super(startPoint, endPoint);
		line.setStroke(Color.YELLOW);
		getChildren().add(line);
	}

	@Override
	protected void onStartPointViewInited(Point startPoint) {
		System.out.println("startPoint=" + startPoint);

		line.startXProperty().bindBidirectional(startPoint.layoutXProperty());
		line.startYProperty().bindBidirectional(startPoint.layoutYProperty());
	}

	@Override
	protected void onEndPointViewInited(Point endPoint) {
		line.endXProperty().bindBidirectional(endPoint.layoutXProperty());
		line.endYProperty().bindBidirectional(endPoint.layoutYProperty());
	}

	private class Behaviour {
	}
}