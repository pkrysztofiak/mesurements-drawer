package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

//TODO przepisać w generyka
public abstract class ConnectorView extends Group {

	protected final ObjectProperty<Point> startPointProperty = new SimpleObjectProperty<>();
	protected final Observable<Point> startPointInitedObservable = JavaFxObservable.valuesOf(startPointProperty).take(1);

	protected final ObjectProperty<Point> endPointProperty = new SimpleObjectProperty<>();
	protected final Observable<Point> endPointInitedObservable = JavaFxObservable.valuesOf(endPointProperty).take(1);

	public ConnectorView(Point startPoint, Point endPoint) {
		startPointProperty.set(startPoint);
		endPointProperty.set(endPoint);
//		initSubscriptions();
	}

	protected void initSubscriptions() {
		startPointInitedObservable.subscribe(this::onStartPointViewInited);
		endPointInitedObservable.subscribe(this::onEndPointViewInited);
	}

	public Observable<Point> endPointInitedObservable() {
		return endPointInitedObservable;
	}

	protected abstract void onStartPointViewInited(Point startPointView);
	protected abstract void onEndPointViewInited(Point endPointView);

}