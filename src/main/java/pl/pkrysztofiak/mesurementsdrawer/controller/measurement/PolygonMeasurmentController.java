package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.ListIterator;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.EventsReceiver;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineEdgeView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.LineView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.MouseClickable;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.MouseClickedHandler;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class PolygonMeasurmentController extends MeasurementController implements EventsReceiver {

	private final Behaviour behaviour = new Behaviour();

	private final PolygonMeasurementView polygonMeasurementView;

	private final ObservableList<Point> points = FXCollections.observableArrayList();
	private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);

	public PolygonMeasurmentController(PolygonMeasurementView polygonMeasurementView, PolygonMeasurement polygonMeasurement) {
		this.polygonMeasurementView = polygonMeasurementView;
		initSubscriptons();
		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
	}

	private void initSubscriptons() {
		pointAddedObservable.subscribe(behaviour::onPointAdded);

//		Predicate<Integer> predicate = size -> size > 2;
//		pointsSizeObservable.filter(predicate)
//		.switchMap(size -> firstPointViewMouseClickedObservable)
//		.switchMap(mouseClicked -> Observable.combineLatest(firstPointViewObservale, lastPointViewObservable, behaviour::onFinished))
//		.subscribe();
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	@Override
	public void onMouseClicked(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());


		points.add(point);
	}

	private class Behaviour implements MouseClickedHandler<Point> {

		private void onPointAdded(Point point) {

			ListIterator<Point> listIterator = points.listIterator(points.indexOf(point));
			if (listIterator.hasPrevious()) {
				point.setPreviousPoint(listIterator.previous());
			}

			VertexController vertexController = new VertexController(point);

			vertexController.vertexViewObservable().subscribe(behaviour::onVertexViewInitialized);
			vertexController.vertexViewChangeObservable().subscribe(behaviour::onVertexViewChanged);

			if (point.hasPrevious()) {
				LineEdgeView lineEdgeView = new LineEdgeView(point.getPreviousPoint().get(), point);
				polygonMeasurementView.getEdgesChildren().add(lineEdgeView);
			}
		}

		private void onVertexViewInitialized(VertexView vertexView) {
			polygonMeasurementView.getVerticesChildren().add(vertexView);
		}

		private void onVertexViewChanged(Change<Optional<VertexView>> change) {
			Optional<VertexView> oldVertexViewOptional = change.getOldVal();
			Optional<VertexView> newVertexViewOptional = change.getNewVal();
			ObservableList<Node> verticesChildren = polygonMeasurementView.getVerticesChildren();
			if (oldVertexViewOptional.isPresent() && newVertexViewOptional.isPresent()) {
				VertexView oldVertexView = oldVertexViewOptional.get();
				VertexView newVertexView = newVertexViewOptional.get();
				verticesChildren.set(verticesChildren.indexOf(oldVertexView), newVertexView);
			} else if (!oldVertexViewOptional.isPresent() && newVertexViewOptional.isPresent()) {
				verticesChildren.add(newVertexViewOptional.get());
			} else if (oldVertexViewOptional.isPresent() && !newVertexViewOptional.isPresent()) {
				verticesChildren.remove(oldVertexViewOptional.get());
			}
		}

		private void onVertexViewChanged(VertexView vertexView) {
//			polygonMeasurementView.addVertexView(vertexView);
		}

		private void onMouseClicked(MouseClickable<Point> mouseClickable) {
		}


		private void onVertexClicked(Point point) {
			System.out.println("clicked");
			if ((points.size() > 2) && !point.hasPrevious()) {
				point.setPreviousPoint(points.get(points.size() - 1));
			}
		}

		private Optional<Void> onFinished(VertexView firstPointView, VertexView lastPointView) {
			LineView lineView = new LineView(firstPointView.getPoint(), lastPointView.getPoint());
//			polygonMeasurementView.addLineView(lineView);
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
//			polygonMeasurementView.addLineView(lineView);
		}

		@Override
		public void mouseClicked(Point point) {

		}
	}
}
