package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import javafx.scene.layout.Pane;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.MeasurementType;

public abstract class Measurement extends Pane implements EventsReceiver {

    public abstract MeasurementType getType();

    public Measurement() {
        setPickOnBounds(false);
    }
}
