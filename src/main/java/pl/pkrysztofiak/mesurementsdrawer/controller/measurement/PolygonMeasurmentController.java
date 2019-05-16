package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
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
	private final Observable<Integer> pointsSizeObservable = JavaFxObservable.emitOnChanged(points).map(List::size);

	private final PublishSubject<CirclePointView> firstPointViewPubslishable = PublishSubject.create();
	private final PublishSubject<CirclePointView> lastPointViewPubslishable = PublishSubject.create();

	private final PolygonMeasurementView polygonMeasurementView;

	public PolygonMeasurmentController(PolygonMeasurementView polygonMeasurementView, PolygonMeasurement polygonMeasurement) {
		this.polygonMeasurementView = polygonMeasurementView;
		initSubscriptons();
		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
	}

	private void initSubscriptons() {
		pointAddedObservable.subscribe(behaviour::onPointAdded);
		Observable.combineLatest(firstPointViewPubslishable.switchMap(pointView -> pointView.mouseClickedObservable().map(mouseClicked -> pointView)), lastPointViewPubslishable, behaviour::onFinished).subscribe();
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	@Override
	public void onMouseClicked(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	private class Behaviour {

		private void onPointAdded(Point point) {
			initPoint(point);

			CirclePointView pointView = new CirclePointView(point);
			polygonMeasurementView.addPointView(pointView);

			point.previousPointObservable().filter(Optional.empty()::equals).subscribe(previousEmpty -> firstPointViewPubslishable.onNext(pointView));
			point.nextPointObservable().filter(Optional.empty()::equals).subscribe(nextEmpty -> lastPointViewPubslishable.onNext(pointView));

			point.nextPointObservable().filter(Optional::isPresent).map(Optional::get).subscribe(behaviour::onNexPointSet);
		}

		private Optional<Void> onFinished(CirclePointView firstCirclePointView, CirclePointView lastCirclePointView) {
			LineView lineView = new LineView(firstCirclePointView.getPoint(), lastCirclePointView.getPoint());
			polygonMeasurementView.addLineView(lineView);
			return Optional.empty();
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
