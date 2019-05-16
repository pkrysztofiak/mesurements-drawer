package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.ListIterator;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;

public class PolygonMeasurmentController extends MeasurementController implements EventsReceiver {

	private final Behaviour behaviour = new Behaviour();

	private final ObservableList<Point> points = FXCollections.observableArrayList();
	private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);

	private final PolygonMeasurementView polygonMeasurementView;

	public PolygonMeasurmentController(PolygonMeasurementView polygonMeasurementView, PolygonMeasurement polygonMeasurement) {
		this.polygonMeasurementView = polygonMeasurementView;
		initSubscriptons();
		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
	}

	private void initSubscriptons() {
		pointAddedObservable.subscribe(behaviour::onPointAdded);
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	private class Behaviour {

		private void onPointsChagned(ObservableList<Point> points) {
		}

		private void onPointAdded(Point point) {
			initPoint(point);

			CirclePointView pointView = new CirclePointView(point);
			polygonMeasurementView.addPointView(pointView);

			Observable<CirclePointView> firstPointViewObservable = point.previousPointObservable().filter(Optional.empty()::equals).map(emptyPrevious -> pointView).filter(poitView -> points.size() > 2);
			Observable<CirclePointView> lastPointViewObservable = point.nextPointObservable().filter(Optional.empty()::equals).map(emptyNext -> pointView).filter(poitView -> points.size() > 2);



//			point.previousPointObservable().filter(Optional.empty()::equals).filter(empty -> )

			point.nextPointObservable().filter(Optional::isPresent).map(Optional::get).subscribe(behaviour::onNexPointSet);
		}

		private void initPoint(Point point) {
			ListIterator<Point> listIterator = points.listIterator(points.indexOf(point));

			if (listIterator.hasPrevious()) {
				point.setPreviousPoint(points.get(listIterator.previousIndex()));
			}

			listIterator.next();
			if (listIterator.hasNext()) {
				point.setNextPoint(points.get(listIterator.nextIndex()));
			}
		}

		private void onNexPointSet(Point nextPoint) {
			LineView lineView = new LineView(nextPoint.getPreviousPoint().get(), nextPoint);
			polygonMeasurementView.addLineView(lineView);
		}
	}
}
