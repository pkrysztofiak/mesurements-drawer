package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.ListIterator;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurementView2;

public class PolygonMeasurmentController extends MeasurementController implements EventsReceiver {

	private final Behaviour behaviour = new Behaviour();

	private final ObservableList<Point> points = FXCollections.observableArrayList();
	private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);

	private final PolygonMeasurementView2 polygonMeasurementView;

	public PolygonMeasurmentController(PolygonMeasurementView2 polygonMeasurementView) {
		this.polygonMeasurementView = polygonMeasurementView;
		initSubscriptons();
		Bindings.bindContentBidirectional(points, polygonMeasurementView.getPolygonMeasurement().getPoints());
	}

//	public PolygonMeasurmentController(PolygonMeasurementViewOld polygonMeasurementViewOld, PolygonMeasurement polygonMeasurement) {
//		this.polygonMeasurementViewOld = polygonMeasurementViewOld;
//		initSubscriptons();
//		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
//	}

	private void initSubscriptons() {
		pointAddedObservable.subscribe(behaviour::onPointAdded);
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	private class Behaviour {

		private void onPointAdded(Point point) {
			ListIterator<Point> listIterator = points.listIterator(points.indexOf(point));

			if (listIterator.hasPrevious()) {
				point.setPreviousPoint(points.get(listIterator.previousIndex()));
			}

			listIterator.next();
			if (listIterator.hasNext()) {
				point.setNextPoint(points.get(listIterator.nextIndex()));
			}
			polygonMeasurementView.addPoint(point);
		}
	}
}
