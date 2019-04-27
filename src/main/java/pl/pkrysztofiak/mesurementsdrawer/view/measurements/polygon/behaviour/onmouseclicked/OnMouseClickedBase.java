package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.onmouseclicked;

import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public abstract class OnMouseClickedBase implements OnMouseClicked {

	protected final Point point;

	public OnMouseClickedBase(Point point) {
		this.point = point;
	}

}
