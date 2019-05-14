package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.MeasurementType;

public abstract class MeasurementView extends Pane implements EventsReceiver {

	protected PublishSubject<MeasurementView> finishedPublishable = PublishSubject.create();

	protected ObjectProperty<Measurement> measurementProperty = new SimpleObjectProperty<>();
	protected Observable<Measurement> measurementInitializedObservable = JavaFxObservable.valuesOf(measurementProperty).take(1);

    public abstract MeasurementType getType();

    public MeasurementView(Measurement measurement) {
    	measurementProperty.set(measurement);
        setPickOnBounds(false);
    }

    public Observable<MeasurementView> finishedObservable() {
    	return finishedPublishable.take(1);
    }

    public Measurement getMeasurement() {
    	return measurementProperty.get();
    }
}
