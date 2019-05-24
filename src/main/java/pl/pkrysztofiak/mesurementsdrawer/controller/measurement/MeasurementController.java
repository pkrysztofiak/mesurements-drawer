package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;

public class MeasurementController {

    protected final ObjectProperty<MeasurementView> measurementViewProperty = new SimpleObjectProperty<>();  
    private final Observable<MeasurementView> measurementViewObservable = JavaFxObservable.valuesOf(measurementViewProperty);
    
    public Observable<MeasurementView> measurementViewInitializedObservable() {
        return measurementViewObservable;
    }
}
