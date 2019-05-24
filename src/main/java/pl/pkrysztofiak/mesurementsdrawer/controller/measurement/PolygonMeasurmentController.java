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
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.EdgeView;
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

	private class Behaviour {

		private void onPointAdded(Point point) {
			ListIterator<Point> listIterator = points.listIterator(points.indexOf(point));
			if (listIterator.hasPrevious()) {
				point.setPreviousPoint(listIterator.previous());
			}

			VertexController vertexController = new VertexController(point);

			vertexController.vertexViewObservable().subscribe(behaviour::onVertexViewInitialized);
			vertexController.vertexViewChangeObservable().subscribe(behaviour::onVertexViewChanged);
			vertexController.mouseClickedObservable().map(mouseEvent -> vertexController).subscribe(behaviour::onMouseClicked);
			

			if (point.hasPrevious()) {
				EdgeController edgeController = new EdgeController(point.getPreviousPoint().get(), point);
				edgeController.edgeViewObservable().subscribe(behaviour::onEdgeViewInitialized);
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

		private void onEdgeViewInitialized(EdgeView edgeView) {
			polygonMeasurementView.getEdgesChildren().add(edgeView);
		}
		
		private void onMouseClicked(VertexController vertexController) {
		    Point mouseClickedPoint = vertexController.getPoint();
            if (points.size() > 2 && !mouseClickedPoint.hasPrevious()) {
                EdgeController edgeController = new EdgeController(points.get(points.size() - 1), mouseClickedPoint);
                edgeController.edgeViewObservable().subscribe(behaviour::onEdgeViewInitialized);
		    }
		}
	}
}