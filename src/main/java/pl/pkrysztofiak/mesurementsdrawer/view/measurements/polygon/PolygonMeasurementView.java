package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;


import java.util.ListIterator;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.MeasurementType;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.PolygonDrawingBehaviour;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.PolygonUnfinishedDrawingBehaviour;

public class PolygonMeasurementView extends MeasurementView {

    private Behaviour behaviour = new Behaviour();

    private final ObservableList<Point> points = FXCollections.observableArrayList();
    private final Observable<Point> pointRemovedObservable = JavaFxObservable.removalsOf(points);
    private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);

    private final ObjectProperty<Optional<PolygonDrawingBehaviour>> drawingBehaviourProperty = new SimpleObjectProperty<>(Optional.empty());
    private final Observable<Change<Optional<PolygonDrawingBehaviour>>> drawingBehaviourChangeObservablbe = JavaFxObservable.changesOf(drawingBehaviourProperty);

    public PolygonMeasurementView() {
    	super(new PolygonMeasurement());
        initSubscriptions();
        drawingBehaviourProperty.set(Optional.of(new PolygonUnfinishedDrawingBehaviour()));
    }

    public PolygonMeasurementView(Measurement polygonMeasurement) {
    	super(polygonMeasurement);
    	initSubscriptions();
    	drawingBehaviourProperty.set(Optional.of(new PolygonUnfinishedDrawingBehaviour()));
    }

    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
        drawingBehaviourChangeObservablbe.subscribe(behaviour::onDrawingBehaviourChanged);

        measurementInitializedObservable.cast(PolygonMeasurement.class).subscribe(behaviour::onMeasurmentInitialized);
    }

    @Override
    public MeasurementType getType() {
        return MeasurementType.POLYGON;
    }

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
    	Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
    	if (points.isEmpty()) {
    		JavaFxObservable.valuesOf(point.previousPointProperty()).filter(Optional::isPresent).subscribe(previousPoint -> finishedPublishable.onNext(this));
    	}
		points.add(point);
    }

    @Override
    public String toString() {
        return "Polygon[points=" + points + "]";
    }

    private class Behaviour {

    	private void onMeasurmentInitialized(PolygonMeasurement polygonMeasurement) {
    		Bindings.bindContentBidirectional(points, polygonMeasurement.getPoints());
    	}

        private void onPointAdded(Point point) {
        	int indexOf = points.indexOf(point);
			ListIterator<Point> forwardsListIterator = points.listIterator(indexOf + 1);
			ListIterator<Point> backwardsListIterator = points.listIterator(indexOf);

        	System.out.println("indexOf=" + indexOf + ", points.size=" + points.size());

        	if (backwardsListIterator.hasPrevious()) {
        		System.out.println("has previous");
        		point.setPreviousPoint(points.get(backwardsListIterator.previousIndex()));
        	}

        	if (forwardsListIterator.hasNext()) {
        		System.out.println("has next");
        		point.setNextPoint(points.get(forwardsListIterator.nextIndex()));
        	}
        }

        private void onDrawingBehaviourChanged(Change<Optional<PolygonDrawingBehaviour>> change) {
            change.getOldVal().ifPresent(drawingBehaviour -> {
                Bindings.unbindContentBidirectional(drawingBehaviour.getPoints(), points);
                Bindings.unbindContentBidirectional(getChildren(), drawingBehaviour.getChildren());
            });

            change.getNewVal().ifPresent(drawingBehaviour -> {
                Bindings.bindContentBidirectional(drawingBehaviour.getPoints(), points);
                Bindings.bindContentBidirectional(getChildren(), drawingBehaviour.getChildren());
            });
        }
    }
}