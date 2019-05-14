package pl.pkrysztofiak.mesurementsdrawer.model.measurements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PolygonMeasurement extends Measurement {

	private final ObservableList<Point> points = FXCollections.observableArrayList();

	public PolygonMeasurement() {

	}

	public PolygonMeasurement(Point... points) {
		this.points.setAll(points);
	}

	@Override
	public MeasurementType getType() {
		return MeasurementType.POLYGON;
	}

	public ObservableList<Point> getPoints() {
		return points;
	}
}