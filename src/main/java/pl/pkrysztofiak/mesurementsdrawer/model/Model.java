package pl.pkrysztofiak.mesurementsdrawer.model;


import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;

public class Model {

	private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
	private final Observable<Measurement> measurementAddedObservable = JavaFxObservable.additionsOf(measurements);
//	private final ObservableList<Measurement> readOnlyMeasurements = FXCollections.unmodifiableObservableList(measurements);

	public void addMeasurement(Measurement measurement) {
		measurements.add(measurement);
	}

//	public ObservableList<Measurement> getUnmodifiableMeasurements() {
//		return readOnlyMeasurements;
//	}

	public Observable<Measurement> measurementAddedObservable() {
		return measurementAddedObservable;
	}

//	public ObservableList<Measurement> getMeasurements() {
//		return readOnlyMeasurements;
//	}
}