package pl.pkrysztofiak.mesurementsdrawer.controller.panel.image;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.controller.measurement.MeasurementController;
import pl.pkrysztofiak.mesurementsdrawer.controller.measurement.polygon.PolygonMeasurmentController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanelView;

public class ImagePanelController {

    private final Behaviour behaviour = new Behaviour();

    private final ImagePanelView imagePanelView;
    private final Model model;

    private final ObjectProperty<EventsReceiver> eventsReceiverPropety = new SimpleObjectProperty<>();
    private final Observable<EventsReceiver> eventsReceiverObservable = JavaFxObservable.valuesOf(eventsReceiverPropety);

    private final PublishSubject<Measurement> measurementViewCreatedPublishable = PublishSubject.create();

    private final ObservableList<MeasurementController> measurementsControllers = FXCollections.observableArrayList();

    public ImagePanelController(ImagePanelView imagePanelView, Model model) {
        this.imagePanelView = imagePanelView;
        this.model = model;
        initSubscriptions();
    }

    private void initSubscriptions() {
        eventsReceiverObservable.switchMap(eventsReceiver -> imagePanelView.mouseClickedObservable().doOnNext(eventsReceiver::mouseClicked)).subscribe();
        model.measurementAddedObservable().subscribe(behaviour::onMeasurementAdded);
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

    public boolean equals(ImagePanelController imagePanelController) {
        return this == imagePanelController;
    }

    public void setEventsReceiver(EventsReceiver eventsReceiver) {
        eventsReceiverPropety.set(eventsReceiver);
    }

    public void setEventsReceiver(Measurement measurement) {
    	measurementsControllers.stream()
    	.filter(measurementController -> measurementController.getMeasurement().getId().equals(measurement.getId()))
    	.findFirst()
    	.ifPresent(this::setEventsReceiver);
    }

    public Observable<Measurement> measurementViewCreatedObservable() {
    	return measurementViewCreatedPublishable;
    }

    private class Behaviour {

    	private void onMeasurementAdded(Measurement measurement) {
    		MeasurementController measurementController = behaviour.createMeasurementController(measurement);
        	measurementsControllers.add(measurementController);
    	}

    	private MeasurementController createMeasurementController(Measurement measurement) {
    		if (!measurementExists(measurement)) {
    			switch (measurement.getType()) {
					case POLYGON :
						PolygonMeasurmentController polygonMeasurmentController = new PolygonMeasurmentController((PolygonMeasurement) measurement);
						imagePanelView.getChildren().add(polygonMeasurmentController.getPolygonMeasurementView());

						polygonMeasurmentController.measurementViewCreatedObservable().subscribe(measurementViewCreatedPublishable::onNext);

//						polygonMeasurmentController.measurementViewInitializedObservable().subscribe(imagePanelView.getChildren()::add);

						return polygonMeasurmentController;

//						setEventsReceiver(polygonMeasurmentController);
					case LINE :
						return null;
					default :
						return null;
				}
    		}
			return null;
    	}

    	private boolean measurementExists(Measurement measurement) {
    		return false;
//    		return measurementsViewsOld.stream().map(MeasurementViewOld::getMeasurement).map(Measurement::getId).anyMatch(measurement.getId()::equals);
    	}

    }
}