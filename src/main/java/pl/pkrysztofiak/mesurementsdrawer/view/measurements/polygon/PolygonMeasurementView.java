package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;

import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.ConnectorView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.PointView;

public class PolygonMeasurementView extends MeasurementView {

	private final Behaviour behaviour = new Behaviour();

	public void addPoint(Point point) {
		behaviour.addPoint(point);
	}

	public void addPointView(PointView pointView) {
		getChildren().add(pointView);
	}

	public void addLineView(LineView lineView) {
		getChildren().add(0, lineView);
	}

	public void addLineView(ConnectorView lineView) {
		getChildren().add(lineView);
	}

	private class Behaviour {

		private void addPoint(Point point) {
			PointView pointView = new CirclePointView(point);
			getChildren().add(pointView);
		}
	}
}