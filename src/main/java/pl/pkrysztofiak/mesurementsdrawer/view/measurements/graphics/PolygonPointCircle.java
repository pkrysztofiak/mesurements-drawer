package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics;

import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.behaviour.OnMouseHover;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.behaviour.OnMouseHoverCircleLazy;

public class PolygonPointCircle extends PointNode<Circle> implements OnMouseHover {

	public PolygonPointCircle(Circle circle) {
		super(circle);
		onMouseHoverProperty.set(new OnMouseHoverCircleLazy(circle));
	}

	@Override
	public void onMouseHover(boolean value) {
		onMouseHoverProperty.get().onMouseHover(value);
	}
}