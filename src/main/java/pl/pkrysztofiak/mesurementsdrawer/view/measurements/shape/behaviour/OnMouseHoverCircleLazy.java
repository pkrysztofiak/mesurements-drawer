package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.behaviour;

import javafx.scene.shape.Circle;

public class OnMouseHoverCircleLazy extends OnMouseHoverAbstract<Circle> {

	public OnMouseHoverCircleLazy(Circle circle) {
		super(circle);
	}

	@Override
	public void onMouseHover(boolean value) {
		if (value) {
			node.setStyle("-fx-fill: blue;");
		} else {
			node.setStyle("-fx-fill: yellow;");
		}
	}
}