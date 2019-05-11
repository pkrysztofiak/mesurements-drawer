package pl.pkrysztofiak.mesurementsdrawer.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;

public class Model {

	private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
	private final ObservableList<Measurement> unmodifiableMeasurements = FXCollections.unmodifiableObservableList(measurements);

	public void addMeasurement(Measurement measurement) {
		measurements.add(measurement);
	}

	public ObservableList<Measurement> getUnmodifiableMeasurements() {
		return unmodifiableMeasurements;
	}

	public ObservableList<Measurement> getMeasurements() {
		return measurements;
	}
}