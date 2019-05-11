package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;

public abstract class Tool implements EventsReceiver {

//    protected final ObjectProperty<ImagePanelController> selectedImagePanelController = new SimpleObjectProperty<>();

//    protected final ObjectProperty<PanelController> selectedPanelController = new SimpleObjectProperty<>();

    protected final PublishSubject<Measurement> measurementCreatedPubslishable = PublishSubject.create();

    public abstract ToolType getType();

//    public void setSelectedPanelController(PanelController panelController) {
//    	selectedPanelController.set(panelController);
//    }

//    public void setSelectedImagePanelController(ImagePanelController imagePanelController) {
//        selectedImagePanelController.set(imagePanelController);
//    }

    public Observable<Measurement> measurementCreatedObservable() {
    	return measurementCreatedPubslishable;
    }
}