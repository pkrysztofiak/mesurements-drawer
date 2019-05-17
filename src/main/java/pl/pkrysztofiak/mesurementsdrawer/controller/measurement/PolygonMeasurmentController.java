package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.PointView;

public class PolygonMeasurmentController extends MeasurementController implements EventsReceiver {

	private final Behaviour behaviour = new Behaviour();

	private final ObservableList<Point> points = FXCollections.observableArrayList();
	private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);
	private final Observable<Integer> pointsSizeObservable = JavaFxObservable.emitOnChanged(points).map(List::size);

	private final ObjectProperty<PointView> firstPointViewProperty = new SimpleObjectProperty<>();
	private final Observable<PointView> firstPointViewObservale = JavaFxObservable.valuesOf(firstPointViewProperty);
	private final Observable<MouseEvent> firstPointViewMouseClickedObservable = firstPointViewObservale.switchMap(PointView::mouseClickedObservable);

	private final ObjectProperty<PointView> lastPointViewProperty = new SimpleObjectProperty<>();
	private final Observable<PointView> lastPointViewObservable = JavaFxObservable.valuesOf(lastPointViewProperty);

//	private final PublishSubject<PointView> firstPointViewPubslishable = PublishSubject.create();
//	private final PublishSubject<PointView> lastPointViewPubslishable = PublishSubject.create();

//	private final Observable<PointView> firstPointViewClickedObservable = firstPointViewPubslishable.switchMap(firstPointView -> firstPointView.mouseClickedObservable().map(mouseEvent -> firstPointView));

	private final PolygonMeasurementView polygonMeasurementView;

	public PolygonMeasurmentController(PolygonMeasurementView polygonMeasurementView, PolygonMeasurement polygonMeasurement) {
		this.polygonMeasurementView = polygonMeasurementView;
		initSubscriptons();
		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
	}

	private void initSubscriptons() {
		pointAddedObservable.subscribe(behaviour::onPointAdded);

		Predicate<Integer> predicate = size -> size > 2;
		pointsSizeObservable.filter(predicate)
		.switchMap(size -> firstPointViewMouseClickedObservable)
		.switchMap(mouseClicked -> Observable.combineLatest(firstPointViewObservale, lastPointViewObservable, behaviour::onFinished))
		.subscribe();
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

			point.previousPointObservable().filter(Optional.empty()::equals).subscribe(previousPointEmpty -> firstPointViewProperty.set(pointView));
			point.nextPointObservable().filter(Optional.empty()::equals).subscribe(previousPointEmpty -> lastPointViewProperty.set(pointView));

//			Observable<PointView> firstPointViewObservable = point.previousPointObservable().filter(Optional.empty()::equals).map(previousPointEmpty -> pointView);
//			Observable<PointView> lastPointViewObservable = point.nextPointObservable().filter(Optional.empty()::equals).map(nextPointEmpty -> pointView);
//
//			firstPointViewObservable.subscribe(firstPointViewPubslishable::onNext);
//			lastPointViewObservable.subscribe(lastPointViewPubslishable::onNext);

			point.nextPointObservable().filter(Optional::isPresent).map(Optional::get).subscribe(behaviour::onNexPointSet);
		}

		private Optional<Void> onFinished(PointView firstPointView, PointView lastPointView) {
			System.out.println("on finished");
			LineView lineView = new LineView(firstPointView.getPoint(), lastPointView.getPoint());
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
