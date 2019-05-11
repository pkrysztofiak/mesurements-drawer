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
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.PolygonPointCircle;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.onmouseclicked.OnMouseClicked;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.onmouseclicked.OnMouseClickedFirst;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour.onmouseclicked.OnMouseClickedLazy;

public class PolygonUnfinishedDrawingBehaviour extends PolygonDrawingBehaviour {

    private final Behaviour behaviour = new Behaviour();

    private final Observable<Point> pointAddedObservable = JavaFxObservable.additionsOf(points);
    private final Observable<Point> pointRemovedObservable = JavaFxObservable.removalsOf(points);
    private final Observable<Integer> pointsSizeObservable = JavaFxObservable.emitOnChanged(points).map(List::size);

    public PolygonUnfinishedDrawingBehaviour() {
        initSubscriptions();
    }

    private void initSubscriptions() {
        pointAddedObservable.subscribe(behaviour::onPointAdded);
    }

    private Observable<Point> pointRemovedObservable() {
    	return pointRemovedObservable;
    }

    private class Behaviour {

        private void onPointAdded(Point point) {
        	PolygonPointCircle polygonPointCircle = new PolygonPointCircle(new Circle(8, Color.BLUEVIOLET)) {

        		private final PointBehaviour pointBehaviour = new PointBehaviour();

        		private OnMouseClicked onMouseClicked = new OnMouseClickedLazy(point);

        		private final Observable<MouseEvent> pointClicked = JavaFxObservable.eventsOf(getNode(), MouseEvent.MOUSE_PRESSED);
        		private final Observable<Change<Optional<Point>>> nextPointChangedObservable = JavaFxObservable.changesOf(point.nextPointProperty());
        		private final Observable<Point> pointRemovedObservable = pointRemovedObservable().filter(point::equals).take(1);

        		{
        			pointsSizeObservable.subscribe(pointBehaviour::onPointsSizeChanged);
        			pointClicked.subscribe(pointBehaviour::onMouseClicked);
        			nextPointChangedObservable.subscribe(behaviour::onNextPointChanged);
        		}

        		private void setOnMouseClicked(OnMouseClicked onMouseClicked) {
        			this.onMouseClicked = onMouseClicked;
        		}

        		class PointBehaviour implements OnMouseClicked {

        			@Override
					public void onMouseClicked(MouseEvent mouseEvent) {
        				onMouseClicked.onMouseClicked(mouseEvent);
        			}

        			private void onPointsSizeChanged(int size) {
        				if (size > 2) {
        					setOnMouseClicked(new OnMouseClickedFirst(point));
        				} else {
        					setOnMouseClicked(new OnMouseClickedLazy(point));
        				}
        			}
        		}
        	};

        	Circle circle = polygonPointCircle.getNode();
            circle.layoutXProperty().bindBidirectional(point.layoutXProperty());
            circle.layoutYProperty().bindBidirectional(point.layoutYProperty());
            children.add(circle);
        }

        private void onNextPointChanged(Change<Optional<Point>> change) {
        	change.getNewVal().flatMap(Point::getPreviousPoint).ifPresent(previousPoint -> {
        		Line line = new Line();
    			line.setStroke(Color.CYAN);

    			line.startXProperty().bindBidirectional(previousPoint.layoutXProperty());
                line.startYProperty().bindBidirectional(previousPoint.layoutYProperty());

                line.endXProperty().bindBidirectional(previousPoint.getNextPoint().get().layoutXProperty());
                line.endYProperty().bindBidirectional(previousPoint.getNextPoint().get().layoutYProperty());

                children.add(0, line);
        	});
        }
    }
}