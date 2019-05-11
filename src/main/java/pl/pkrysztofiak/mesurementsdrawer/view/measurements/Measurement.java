package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.layout.Pane;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.MeasurementType;

public abstract class Measurement extends Pane implements EventsReceiver {

	protected PublishSubject<Measurement> finishedPublishable = PublishSubject.create();

    public abstract MeasurementType getType();

    public Measurement() {
        setPickOnBounds(false);
    }

    public Observable<Measurement> finishedObservale() {
    	return finishedPublishable.take(1);
    }
}
