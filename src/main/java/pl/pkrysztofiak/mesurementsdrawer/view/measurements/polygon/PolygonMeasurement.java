package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;


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
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.MeasurementType;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.PolygonDrawingBehaviour;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.PolygonUnfinishedDrawingBehaviour;

public class PolygonMeasurement extends Measurement {

    private Behaviour behaviour = new Behaviour();

    private final ObservableList<Point> points = FXCollections.observableArrayList();
    private final Observable<Point> pointRemovedObservable = JavaFxObservable.removalsOf(points);
    private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);

    private final ObjectProperty<Optional<PolygonDrawingBehaviour>> drawingBehaviourProperty = new SimpleObjectProperty<>(Optional.empty());
    private final Observable<Change<Optional<PolygonDrawingBehaviour>>> drawingBehaviourChangeObservablbe = JavaFxObservable.changesOf(drawingBehaviourProperty);

    public PolygonMeasurement() {
        initSubscriptions();
        drawingBehaviourProperty.set(Optional.of(new PolygonUnfinishedDrawingBehaviour()));

        JavaFxObservable.emitOnChanged(getChildren())
        .subscribe(children -> System.out.println("PolygonMeasurement.children=" + children));
    }

    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
        drawingBehaviourChangeObservablbe.subscribe(behaviour::onDrawingBehaviourChanged);
    }

    @Override
    public MeasurementType getType() {
        return MeasurementType.POLYGON;
    }

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
    	System.out.println("PolygonMeasurement.onMouseReleased()");
        Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
        if (points.isEmpty()) {
            point.setSelected(true);
            points.add(point);
        } else {
            points.stream().filter(Point::isSelected).findFirst().ifPresent(selectedPoint -> {
                if (!selectedPoint.getNextPoint().isPresent()) {
                	selectedPoint.setNextPoint(point);
                    point.setSelected(true);
                    selectedPoint.setSelected(false);

                    return;
                }

                if (!selectedPoint.getPreviousPoint().isPresent()) {
                	selectedPoint.setPreviousPoint(point);
                    point.setSelected(true);

                    selectedPoint.setSelected(false);
                    return;
                }
            });
        }
    }

    @Override
    public String toString() {
        return "Polygon[points=" + points + "]";
    }

    private class Behaviour {

        private void onPointAdded(Point point) {
        	JavaFxObservable.changesOf(point.nextPointProperty())
        	.map(Change::getNewVal)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .takeUntil(pointRemovedObservable.filter(point::equals))
            .subscribe(points::add);
        }

        private void onDrawingBehaviourChanged(Change<Optional<PolygonDrawingBehaviour>> change) {
            change.getOldVal().ifPresent(drawingBehaviour -> {
            	System.out.println("Unbind");
                Bindings.unbindContentBidirectional(drawingBehaviour.getPoints(), points);
                Bindings.unbindContentBidirectional(getChildren(), drawingBehaviour.getChildren());
            });

            change.getNewVal().ifPresent(drawingBehaviour -> {
            	System.out.println("Bind");
                Bindings.bindContentBidirectional(drawingBehaviour.getPoints(), points);
                Bindings.bindContentBidirectional(getChildren(), drawingBehaviour.getChildren());
            });
        }
    }
}