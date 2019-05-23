package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;

import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.ConnectorView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class PolygonMeasurementView extends MeasurementView {

	private final Behaviour behaviour = new Behaviour();

	public void addVertexView(VertexView vertexView) {
		getChildren().add(vertexView);
	}

	public void addLineView(LineView lineView) {
		getChildren().add(0, lineView);
	}

	public void addLineView(ConnectorView lineView) {
		getChildren().add(lineView);
	}

	private class Behaviour {

		private void addPoint(Point point) {
			VertexView vertexView = new CirclePointView(point);
			getChildren().add(vertexView);
		}
	}
}