package pl.pkrysztofiak.mesurementsdrawer.controller.measurement;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.common.utils.DragProcessor;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.CirclePointView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point.VertexView;

public class VertexController {

	private final Point point;

	private final DragProcessor dragProcessor = new DragProcessor();

	private final ObjectProperty<VertexView> vertexViewProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Optional<VertexView>> vertexViewOptionalProperty = new SimpleObjectProperty<>(Optional.empty());

	private final Observable<VertexView> vertexViewObservable = JavaFxObservable.valuesOf(vertexViewProperty);
	private final Observable<Optional<VertexView>> vertexViewOptionalObservable = JavaFxObservable.nullableValuesOf(vertexViewProperty);
	private final Observable<Change<Optional<VertexView>>> vertexViewChangeObservable = JavaFxObservable.changesOf(vertexViewOptionalProperty);

	private final Observable<MouseEvent> mousePressedObservable = vertexViewObservable.switchMap(VertexView::mousePressedObservable);
	private final Observable<MouseEvent> mouseDraggedObservable = vertexViewObservable.switchMap(VertexView::mouseDraggedObservable);
	private final Observable<MouseEvent> mouseReleasedObservable = vertexViewObservable.switchMap(VertexView::mouseReleasedObservable);

	private final PublishSubject<Double> xTranslatePublishable = PublishSubject.create();
	private final PublishSubject<Double> yTranslatePublishable = PublishSubject.create();

	public VertexController(Point point) {
		this.point = point;
		initSubscriptins();
		vertexViewProperty.set(new CirclePointView(point));

		initDragProcessor();
	}

	private void initDragProcessor() {
		mousePressedObservable.subscribe(dragProcessor.mousePressedPublishable()::onNext);
		mouseDraggedObservable.subscribe(dragProcessor.mouseDraggedPublishable()::onNext);
		mouseReleasedObservable.subscribe(dragProcessor.mouseReleasedPublishable()::onNext);

		point.layoutXObservable().subscribe(dragProcessor::setLayoutX);
		point.layoutYObservable().subscribe(dragProcessor::setLayoutY);

		dragProcessor.xResultObservable().subscribe(point::setLayoutX);
		dragProcessor.yResultObservable().subscribe(point::setLayoutY);
	}

	private void initSubscriptins() {
		vertexViewOptionalObservable.subscribe(vertexViewOptionalProperty::set);
	}

	public Observable<VertexView> vertexViewObservable() {
		return vertexViewObservable;
	}

	public Observable<Change<Optional<VertexView>>> vertexViewChangeObservable() {
		return vertexViewChangeObservable;
	}

	public Observable<MouseEvent> mouseClickedObservable() {
		return vertexViewObservable.switchMap(VertexView::mouseClickedObservable);
	}

	public Observable<Double> xTranslateObservable() {
		return xTranslatePublishable;
	}

	public Observable<Double> yTranslateObservable() {
		return yTranslatePublishable;
	}

	public Point getPoint() {
		return point;
	}

	class Behaviour {

	}
}