package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;

public abstract class Tool implements EventsReceiver {

    protected final PublishSubject<MeasurementView> newMeasurementCreatedPubslishable = PublishSubject.create();

    public abstract ToolType getType();

    public Observable<MeasurementView> newMeasurementCreatedObservable() {
    	return newMeasurementCreatedPubslishable;
    }
}