package pl.pkrysztofiak.mesurementsdrawer.view.measurements;

import javafx.scene.layout.Region;

public abstract class MeasurementView extends Region {

	public MeasurementView() {
		setPickOnBounds(false);
	}
}
