package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;

public abstract class Tool implements EventsReceiver {

    protected final ObjectProperty<PanelController> selectedPanelController = new SimpleObjectProperty<>();

    protected final PublishSubject<Measurement> measurementCreatedPubslishable = PublishSubject.create();


    public abstract ToolType getType();


    public void setSelectedPanelController(PanelController panelController) {
        selectedPanelController.set(panelController);
    }

    public Observable<Measurement> measurementCreatedObservable() {
    	return measurementCreatedPubslishable;
    }
}