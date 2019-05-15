package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.line;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class LineView extends Group {

	private Behaviour behaviour = new Behaviour();

	private final Line line = new Line();
	{
		line.setStroke(Color.YELLOW);
		getChildren().add(line);
	}

	private final ObjectProperty<Point> startPointProperty = new SimpleObjectProperty<>();
	private final Observable<Point> startPointInitializedObservable = JavaFxObservable.valuesOf(startPointProperty).take(1);
	private final ObjectProperty<Point> endPointProperty = new SimpleObjectProperty<>();
	private final Observable<Point> endPointInitializedObservable = JavaFxObservable.valuesOf(endPointProperty).take(1);

	public LineView(Point startPoint, Point endPoint) {
		startPointProperty.set(startPoint);
		endPointProperty.set(endPoint);
		initSubscriptions();
	}

	private void initSubscriptions() {
		startPointInitializedObservable.subscribe(behaviour::onStartPointInitialized);
		endPointInitializedObservable.subscribe(behaviour::onEndPointInitialized);
	}

	private class Behaviour {

		private void onStartPointInitialized(Point startPoint) {
			line.startXProperty().bindBidirectional(startPoint.layoutXProperty());
			line.startYProperty().bindBidirectional(startPoint.layoutYProperty());
		}

		private void onEndPointInitialized(Point endPoint) {
			line.endXProperty().bindBidirectional(endPoint.layoutXProperty());
			line.endYProperty().bindBidirectional(endPoint.layoutYProperty());
		}
	}
}
