package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementViewOld;

public abstract class Tool implements EventsReceiver {

    protected final PublishSubject<MeasurementViewOld> newMeasurementViewCreatedPubslishable = PublishSubject.create();
    protected final PublishSubject<Measurement> newMeasurementCreatedPublishable = PublishSubject.create();

    public abstract ToolType getType();

    public Observable<MeasurementViewOld> newMeasurementViewCreatedObservable() {
    	return newMeasurementViewCreatedPubslishable;
    }

    public Observable<Measurement> newMeasurementCreatedPublishable() {
    	return newMeasurementCreatedPublishable;
    }
}