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
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class PolygonMeasurmentController extends MeasurementController implements EventsReceiver {

	private final Behaviour behaviour = new Behaviour();

	private final PolygonMeasurementView polygonMeasurementView;

	private final ObservableList<Point> points = FXCollections.observableArrayList();
	private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);
	private final Observable<Integer> pointsSizeObservable = JavaFxObservable.emitOnChanged(points).map(List::size);

	private final ObjectProperty<VertexView> firstPointViewProperty = new SimpleObjectProperty<>();
	private final Observable<VertexView> firstPointViewObservale = JavaFxObservable.valuesOf(firstPointViewProperty);
	private final Observable<MouseEvent> firstPointViewMouseClickedObservable = firstPointViewObservale.switchMap(VertexView::mouseClickedObservable);

	private final ObjectProperty<VertexView> lastPointViewProperty = new SimpleObjectProperty<>();
	private final Observable<VertexView> lastPointViewObservable = JavaFxObservable.valuesOf(lastPointViewProperty);

	private final ObservableList<PolygonMeasurementVertexController> verticesControllers = FXCollections.observableArrayList();

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

			PolygonMeasurementVertexController vertexController = new PolygonMeasurementVertexController(point);

			vertexController.vertexViewObservable().subscribe(polygonMeasurementView::addVertexView);
			verticesControllers.add(vertexController);

			vertexController.mouseClickedObservable().subscribe(this::onVertexClicked);

			point.nextPointObservable().filter(Optional::isPresent).map(Optional::get).subscribe(nextPoint -> behaviour.addEdge(point, nextPoint));
		}

		private void onVertexClicked(Point point) {
			System.out.println("clicked");
			if ((points.size() > 2) && !point.hasPrevious()) {
				point.setPreviousPoint(points.get(points.size() - 1));
			}
		}

		private Optional<Void> onFinished(VertexView firstPointView, VertexView lastPointView) {
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

		private void addEdge (Point startPoint, Point endPoint) {
			LineView lineView = new LineView(startPoint, endPoint);
			polygonMeasurementView.addLineView(lineView);
		}
	}
}
