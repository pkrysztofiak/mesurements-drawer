package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;

public abstract class Tool implements EventsReceiver {

    protected final Model model;
    
//    protected final PublishSubject<MouseEvent> mouseReleasedObservable = PublishSubject.create();
    
    protected final ObjectProperty<PanelController> activePanelController = new SimpleObjectProperty<>();
    
    public Tool(Model model) {
        this.model = model;
    }
    
    public abstract ToolType getType();
    
//    public PublishSubject<MouseEvent> mouseReleasedPubslishable() {
//        return mouseReleasedObservable;
//    }
    
    public void setActivePanelController(PanelController selectedPanelController) {
        this.activePanelController.set(selectedPanelController);
    }
}