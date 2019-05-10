package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics;

import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.behaviour.OnMouseHoverCircleLazy;

public class PolygonPointCircle extends PointNode<Circle> {

	public PolygonPointCircle(Circle circle) {
		super(circle);
		//tutaj dorzucić chyba interfejs który ma się wywoływać
		setOnMouseHover(new OnMouseHoverCircleLazy(circle));
//		onMouseHoverProperty.set(new OnMouseHoverCircleLazy(circle));
	}

//	@Override
//	public void onMouseHover(boolean value) {
//		System.out.println("kurwa");
//		onMouseHover.onMouseHover(value);
////		onMouseHoverProperty.get().onMouseHover(value);
//	}
}