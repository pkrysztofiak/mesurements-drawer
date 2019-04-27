package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurement;

public class PolygonDrawer {

    private Behaviour behaviour = new Behaviour();
    
    private final ObjectProperty<PolygonMeasurement> newMeasurementProperty = new SimpleObjectProperty<>();
    private final Observable<PolygonMeasurement> newMeasurementObservable = JavaFxObservable.valuesOf(newMeasurementProperty);
    
    private final ObjectProperty<PanelController> selectedPanelController = new SimpleObjectProperty<>();
    private final PublishSubject<MouseEvent> mouseReleasedPublishable = PublishSubject.create();
    
    public PolygonDrawer() {
        initSubscriptions();
    }
    
    private void initSubscriptions() {
        mouseReleasedPublishable.subscribe(behaviour::onMouseReleased);
        newMeasurementObservable.subscribe(behaviour::onNewMesurementCreated);
    }
    
    public Observable<PolygonMeasurement> measurementCreatedObservable() {
        return newMeasurementObservable;
    }
    
    public void setSelectedPanelController(PanelController selectedPanelController) {
        this.selectedPanelController.set(selectedPanelController);
    }
    
    public PublishSubject<MouseEvent> mouseReleasedPublishable() {
        return mouseReleasedPublishable;
    }
    
    private class Behaviour {

        private void onMouseReleased(MouseEvent mouseEvent) {
            PolygonMeasurement measurement = Optional.ofNullable(newMeasurementProperty.get()).orElse(new PolygonMeasurement());
            newMeasurementProperty.set(measurement);
        }
        
        private void onNewMesurementCreated(PolygonMeasurement measurement) {
            Optional.ofNullable(selectedPanelController.get()).ifPresent(panelController -> panelController.addMeasurement(measurement));
            
        }
    }
}