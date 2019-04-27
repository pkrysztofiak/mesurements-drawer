package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class PolygonUnfinishedDrawingBehaviour extends PolygonDrawingBehaviour {

    private final Behaviour behaviour = new Behaviour();

    private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);
    private final Observable<List<Point>> pointsAddedObservable = pointAddedObservable.buffer(2, 1);
    private final Observable<Point> pointRemovedObservable = JavaFxObservable.removalsOf(points);
    private final Observable<Integer> pointsSizeObservale = JavaFxObservable.emitOnChanged(points).map(List::size);

    public PolygonUnfinishedDrawingBehaviour() {
        initSubscriptions();
    }

    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
    }

    private class Behaviour {

        private void onPointAdded(Point point) {

        	Circle circle = new Circle(8, Color.BLUEVIOLET) {
        		private final PointBehaviour pointBehaviour = new PointBehaviour();

        		private final Observable<MouseEvent> pointClicked = JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_PRESSED);
        		private final Observable<Change<Optional<Point>>> nextPointChangedObservable = JavaFxObservable.changesOf(point.nextPointProperty());

        		{
        			pointClicked.subscribe(pointBehaviour::onMouseClicked);
        			nextPointChangedObservable.subscribe(behaviour::onNextPointChanged);
        		}

        		class PointBehaviour {

        			private void onMouseClicked(MouseEvent mouseEvent) {
        				mouseEvent.consume();
        				point.setPreviousPoint(points.get(points.size() - 1));
        			}
        		}
        	};

            circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
            circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
            children.add(circle);
        }

        private void onNextPointChanged(Change<Optional<Point>> change) {
        	change.getNewVal().ifPresent(point -> {

        		point.getPreviousPoint().ifPresent(previousPoint -> {
        			Line line = new Line();
        			line.setStroke(Color.CYAN);

        			line.startXProperty().bindBidirectional(previousPoint.layoutXProperty());
                    line.startYProperty().bindBidirectional(previousPoint.layoutYProperty());

                    line.endXProperty().bindBidirectional(point.layoutXProperty());
                    line.endYProperty().bindBidirectional(point.layoutYProperty());

                    children.add(0, line);
        		});
        	});
        }
    }
}