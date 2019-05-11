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
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanel;

public class ImagePanelController {

    private static int idGenerator = 0;
    private final int id = ++idGenerator;

    private final Behaviour behaviour = new Behaviour();

    private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    private final Observable<Measurement> measurementAddedObservable = JavaFxObservable.additionsOf(measurements);
    private final Observable<Measurement> measurementRemovedObservable = JavaFxObservable.removalsOf(measurements);

    private final ObjectProperty<EventsReceiver> eventsReceiverPropety = new SimpleObjectProperty<>();
    private final Observable<EventsReceiver> eventsReceiverObservable = JavaFxObservable.valuesOf(eventsReceiverPropety);

    private final ImagePanel imagePanel;

    public ImagePanelController(ImagePanel imagePanel) {
        super();
        this.imagePanel = imagePanel;
        initSubscriptions();
    }

    private void initSubscriptions() {
        eventsReceiverObservable.switchMap(eventsReceiver -> imagePanel.mouseReleasedObservable().doOnNext(eventsReceiver::mouseReleased)).subscribe();

        measurementAddedObservable.flatMap(Measurement::finishedObservale).subscribe(behaviour::onMeasurementFinished);
    }

    public void setSelected(boolean value) {
        imagePanel.setSelected(value);
    }

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        imagePanel.getChildren().add(measurement);
    }

    public Observable<MouseEvent> mouseReleasedObservable() {
        return imagePanel.mouseReleasedObservable();
    }

    public Observable<MouseEvent> mouseAnyObservable() {
        return imagePanel.mouseAnyObservable();
    }

    public Observable<Measurement> measurementAddedObservable() {
    	return measurementAddedObservable;
    }

    public boolean equals(ImagePanelController imagePanelController) {
        return this == imagePanelController;
    }

    public boolean unequals(ImagePanelController imagePanelController) {
        return this != imagePanelController;
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

    	private void onMeasurementFinished(Measurement measurement) {

    	}
    }
}