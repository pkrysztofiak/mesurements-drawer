package pl.pkrysztofiak.mesurementsdrawer.controller.panel.image;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.controller.measurement.polygon.PolygonMeasurmentController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanelView;

public class ImagePanelController {

    private static int idGenerator = 0;
    private final int id = ++idGenerator;

    private final Behaviour behaviour = new Behaviour();

    private final ImagePanelView imagePanelView;
    private final Model model;

    private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    private final Observable<Measurement> measurementAddedObservable = JavaFxObservable.additionsOf(measurements);

    private final ObservableList<MeasurementView> measurementsViews = FXCollections.observableArrayList();
    private final Observable<MeasurementView> measurementViewAddedObservable = JavaFxObservable.additionsOf(measurementsViews);

    private final ObjectProperty<EventsReceiver> eventsReceiverPropety = new SimpleObjectProperty<>();
    private final Observable<EventsReceiver> eventsReceiverObservable = JavaFxObservable.valuesOf(eventsReceiverPropety);

    public ImagePanelController(ImagePanelView imagePanelView, Model model) {
        this.imagePanelView = imagePanelView;
        this.model = model;
        initSubscriptions();

        Bindings.bindContentBidirectional(measurements, model.getMeasurements());
    }

    private void initSubscriptions() {
        eventsReceiverObservable.switchMap(eventsReceiver -> imagePanelView.mouseClickedObservable().doOnNext(eventsReceiver::mouseClicked)).subscribe();
        measurementAddedObservable.subscribe(behaviour::addMeasurement);
    }

    public void addMeasurement(Measurement measurement) {
    	behaviour.addMeasurement(measurement);
    }

    public void setSelected(boolean value) {
        imagePanelView.setSelected(value);
    }

    public Observable<MouseEvent> mouseReleasedObservable() {
        return imagePanelView.mouseReleasedObservable();
    }

    public Observable<MouseEvent> mouseAnyObservable() {
        return imagePanelView.mouseAnyObservable();
    }

//    public Observable<MeasurementView> measurementAddedObservable() {
//    	return measurementViewAddedObservable;
//    }

    public boolean equals(ImagePanelController imagePanelController) {
        return this == imagePanelController;
    }

    public boolean unequals(ImagePanelController imagePanelController) {
        return this != imagePanelController;
    }

//    public ObservableList<MeasurementViewOld> getMeasurements() {
//        return measurementsViewsOld;
//    }

    public int getId() {
        return id;
    }

    public void setEventsReceiver(EventsReceiver eventsReceiver) {
        eventsReceiverPropety.set(eventsReceiver);
    }

    public Observable<Measurement> measurementFinishedObservable() {
    	return null;
    	//TODO do poprawinia
//    	return measurementViewAddedObservableOld.flatMap(MeasurementViewOld::finishedObservable).map(MeasurementViewOld::getMeasurement);
    }

    private class Behaviour {

    	private void addMeasurement(Measurement measurement) {
    		if (!measurementExists(measurement)) {
    			switch (measurement.getType()) {
					case POLYGON :
						PolygonMeasurmentController polygonMeasurmentController = new PolygonMeasurmentController((PolygonMeasurement) measurement);
						polygonMeasurmentController.measurementViewInitializedObservable().subscribe(imagePanelView.getChildren()::add);
						
						setEventsReceiver(polygonMeasurmentController);
						break;
					case LINE :

						break;
					default :
						break;
				}
    		}
    	}

    	private boolean measurementExists(Measurement measurement) {
    		return false;
//    		return measurementsViewsOld.stream().map(MeasurementViewOld::getMeasurement).map(Measurement::getId).anyMatch(measurement.getId()::equals);
    	}

    }
}