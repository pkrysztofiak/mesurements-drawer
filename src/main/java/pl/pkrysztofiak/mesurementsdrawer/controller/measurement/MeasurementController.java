package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;

public abstract class MeasurementController implements EventsReceiver {

	protected final Measurement measurement;
	protected final PublishSubject<Measurement> measurementViewCreatedPublishable = PublishSubject.create();

    public MeasurementController(Measurement measurement) {
    	this.measurement = measurement;
    }

	public Measurement getMeasurement() {
		return measurement;
	}

	public Observable<Measurement> measurementViewCreatedObservable() {
		return measurementViewCreatedPublishable;
	}

	public boolean hasMeasurement(Measurement measurement) {
		return this.measurement == measurement;
	}
}