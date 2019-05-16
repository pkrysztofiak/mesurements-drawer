package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.behaviour;

import javafx.scene.input.MouseEvent;

public interface MouseClickedHandler<T> {

	void onMouseClicked(T t, MouseEvent mouseEvent);
}
