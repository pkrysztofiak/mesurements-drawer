package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.behaviour;

import javafx.scene.shape.Circle;

public class OnMouseHoverCircleFirst extends OnMouseHoverAbstract<Circle> {

	public OnMouseHoverCircleFirst(Circle circle) {
		super(circle);
	}

	@Override
	public void onMouseHover(boolean value) {
		if (value) {
			node.setStyle("-fx-background-color:yellow;");
		} else {
			node.setStyle("-fx-background-color:yellow;");
		}
	}
}