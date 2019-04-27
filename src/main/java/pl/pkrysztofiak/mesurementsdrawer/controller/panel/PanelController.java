package pl.pkrysztofiak.mesurementsdrawer.controller.panel;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.Panel;

public class PanelController {

    private static int idGenerator = 0;
    private final int id = ++idGenerator;
    
    private final Behaviour behaviour = new Behaviour();
    
    private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    private final Observable<Measurement> measurementAddedObservable = JavaFxObservable.additionsOf(measurements);
    private final Observable<Measurement> measurementRemovedObservable = JavaFxObservable.removalsOf(measurements);
    
    private final ObjectProperty<EventsReceiver> eventsReceiverPropety = new SimpleObjectProperty<>();
    private final Observable<EventsReceiver> eventsReceiverObservable = JavaFxObservable.valuesOf(eventsReceiverPropety);
    
    private final Panel panel;

    public PanelController(Panel panel) {
        super();
        this.panel = panel;
        initSubscriptions();
    }
    
    private void initSubscriptions() {
        eventsReceiverObservable.switchMap(eventsReceiver -> panel.mouseReleasedObservable().doOnNext(eventsReceiver::mouseReleased)).subscribe();
    }
    
    public void setSelected(boolean value) {
        panel.setSelected(value);
    }
    
    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        panel.getChildren().add(measurement);
    }
    
    public Observable<MouseEvent> mouseReleasedObservable() {
        return panel.mouseReleasedObservable();
    }
    
    public Observable<MouseEvent> mouseAnyObservable() {
        return panel.mouseAnyObservable();
    }
    
    public boolean equals(PanelController panelController) {
        return this == panelController;
    }
    
    public boolean unequals(PanelController panelController) {
        return this != panelController;
    }
    
    public ObservableList<Measurement> getMeasurements() {
        return measurements;
    }
    
    public int getId() {
        return id;
    }
    
    public void setEventsReceiver(EventsReceiver eventsReceiver) {
        eventsReceiverPropety.set(eventsReceiver);
    }
    
    private class Behaviour {
    }
}