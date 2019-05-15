package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import javafx.scene.layout.Pane;

public abstract class MeasurementView extends Pane {

	public MeasurementView() {
		setPickOnBounds(false);
	}
}
