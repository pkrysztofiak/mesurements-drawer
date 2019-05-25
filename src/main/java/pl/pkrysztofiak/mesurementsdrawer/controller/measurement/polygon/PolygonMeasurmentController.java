package pl.pkrysztofiak.mesurementsdrawer.controller.measurement.polygon;

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
import pl.pkrysztofiak.mesurementsdrawer.controller.measurement.EdgeController;
import pl.pkrysztofiak.mesurementsdrawer.controller.measurement.MeasurementController;
import pl.pkrysztofiak.mesurementsdrawer.controller.measurement.VertexController;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.line.EdgeView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class PolygonMeasurmentController extends MeasurementController {

	private final Behaviour behaviour = new Behaviour();

	private final PolygonMeasurementView polygonMeasurementView = new PolygonMeasurementView();

	private final ObservableList<Point> points = FXCollections.observableArrayList();
	private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);

	public PolygonMeasurmentController(PolygonMeasurement polygonMeasurement) {
		super(polygonMeasurement);
		initSubscriptons();
		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
	}

	private void initSubscriptons() {
		pointAddedObservable.subscribe(behaviour::onPointAdded);
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
//		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	@Override
	public void onMouseClicked(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
		points.add(point);
	}

	public PolygonMeasurementView getPolygonMeasurementView() {
		return polygonMeasurementView;
	}

	private class Behaviour {

		private void onPointAdded(Point point) {
			point.nextPointObservable().filter(Optional::isPresent).map(Optional::get).subscribe(behaviour::onNextPointCreated);

			ListIterator<Point> listIterator = points.listIterator(points.indexOf(point));
			if (listIterator.hasPrevious()) {
				point.setPreviousPoint(listIterator.previous());
			}

			VertexController vertexController = new VertexController(point);

			vertexController.vertexViewObservable().subscribe(behaviour::onVertexViewInitialized);
			vertexController.vertexViewChangeObservable().subscribe(behaviour::onVertexViewChanged);
			vertexController.mouseClickedObservable().map(mouseEvent -> vertexController).subscribe(behaviour::onMouseClicked);
		}

		private void onVertexViewInitialized(VertexView vertexView) {
			polygonMeasurementView.getVerticesChildren().add(vertexView);
		}

		private void onNextPointCreated(Point point) {
			EdgeController edgeController = new EdgeController(point.getPreviousPoint().get(), point);
			edgeController.edgeViewObservable().subscribe(behaviour::onEdgeViewInitialized);
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
            if ((points.size() > 2) && !mouseClickedPoint.hasPrevious()) {
                Point lastPoint = points.get(points.size() - 1);
                lastPoint.setNextPoint(mouseClickedPoint);
				EdgeController edgeController = new EdgeController(lastPoint, mouseClickedPoint);
                edgeController.edgeViewObservable().subscribe(behaviour::onEdgeViewInitialized);
                measurementViewCreatedPublishable.onNext(measurement);
		    }
		}
	}
}