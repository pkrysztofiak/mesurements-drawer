package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.layout.Pane;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.MeasurementType;

public abstract class MeasurementView extends Pane implements EventsReceiver {

	protected PublishSubject<MeasurementView> finishedPublishable = PublishSubject.create();

    public abstract MeasurementType getType();

    public MeasurementView() {
        setPickOnBounds(false);
    }

    public Observable<MeasurementView> finishedObservale() {
    	return finishedPublishable.take(1);
    }
}
