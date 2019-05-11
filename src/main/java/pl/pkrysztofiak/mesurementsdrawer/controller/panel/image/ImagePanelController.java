package pl.pkrysztofiak.mesurementsdrawer.controller.panel.image;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanelView;

public class ImagePanelController {

    private static int idGenerator = 0;
    private final int id = ++idGenerator;

    private final Behaviour behaviour = new Behaviour();

    private final ObservableList<MeasurementView> measurementViews = FXCollections.observableArrayList();
    private final Observable<MeasurementView> measurementAddedObservable = JavaFxObservable.additionsOf(measurementViews);
    private final Observable<MeasurementView> measurementRemovedObservable = JavaFxObservable.removalsOf(measurementViews);

    private final ObjectProperty<EventsReceiver> eventsReceiverPropety = new SimpleObjectProperty<>();
    private final Observable<EventsReceiver> eventsReceiverObservable = JavaFxObservable.valuesOf(eventsReceiverPropety);

    private final ImagePanelView imagePanelView;

    public ImagePanelController(ImagePanelView imagePanelView) {
        this.imagePanelView = imagePanelView;
        initSubscriptions();
    }

    private void initSubscriptions() {
        eventsReceiverObservable.switchMap(eventsReceiver -> imagePanelView.mouseReleasedObservable().doOnNext(eventsReceiver::mouseReleased)).subscribe();

        measurementAddedObservable.flatMap(MeasurementView::finishedObservale).subscribe(behaviour::onMeasurementFinished);
    }

    public void setSelected(boolean value) {
        imagePanelView.setSelected(value);
    }

    public void addMeasurement(MeasurementView measurementView) {
    	System.out.println("add measurement");
        measurementViews.add(measurementView);
        imagePanelView.getChildren().add(measurementView);
    }

    public Observable<MouseEvent> mouseReleasedObservable() {
        return imagePanelView.mouseReleasedObservable();
    }

    public Observable<MouseEvent> mouseAnyObservable() {
        return imagePanelView.mouseAnyObservable();
    }

    public Observable<MeasurementView> measurementAddedObservable() {
    	return measurementAddedObservable;
    }

    public boolean equals(ImagePanelController imagePanelController) {
        return this == imagePanelController;
    }

    public boolean unequals(ImagePanelController imagePanelController) {
        return this != imagePanelController;
    }

    public ObservableList<MeasurementView> getMeasurements() {
        return measurementViews;
    }

    public int getId() {
        return id;
    }

    public void setEventsReceiver(EventsReceiver eventsReceiver) {
        eventsReceiverPropety.set(eventsReceiver);
    }

    private class Behaviour {

    	private void onMeasurementFinished(MeasurementView measurementView) {

    	}
    }
}