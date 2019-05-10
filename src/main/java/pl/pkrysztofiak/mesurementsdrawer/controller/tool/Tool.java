package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;

public abstract class Tool implements EventsReceiver {

    protected final ObjectProperty<PanelController> selectedPanelController = new SimpleObjectProperty<>();

    public Tool() {
    }

    public abstract ToolType getType();


    public void setSelectedPanelController(PanelController panelController) {
        selectedPanelController.set(panelController);
    }
}