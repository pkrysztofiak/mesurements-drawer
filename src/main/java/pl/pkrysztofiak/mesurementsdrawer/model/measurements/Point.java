package pl.pkrysztofiak.mesurementsdrawer.model.measurements;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Point {

    private final ObjectProperty<Optional<Point>> previousPointProperty = new SimpleObjectProperty<>(Optional.empty());
    private final Observable<Optional<Point>> previousePointObservable = JavaFxObservable.valuesOf(previousPointProperty);
    private final Observable<Change<Optional<Point>>> previousPointChangedObservable = JavaFxObservable.changesOf(previousPointProperty);

    private final ObjectProperty<Optional<Point>> nextPointProperty = new SimpleObjectProperty<>(Optional.empty());
    private final Observable<Optional<Point>> nextPointObservable = JavaFxObservable.valuesOf(nextPointProperty);
    private final Observable<Change<Optional<Point>>> nextPointChangedObservable = JavaFxObservable.changesOf(nextPointProperty);

    private final DoubleProperty xTranslateProperty = new SimpleDoubleProperty();
    private final Observable<Double> xTranslateObservable = JavaFxObservable.valuesOf(xTranslateProperty).map(Number::doubleValue);

    private final DoubleProperty yTranslateProperty = new SimpleDoubleProperty();
    private final Observable<Double> yTranslateObservable = JavaFxObservable.valuesOf(yTranslateProperty).map(Number::doubleValue);

    private BooleanProperty selectedProperty = new SimpleBooleanProperty();

    public Point(double layoutX, double layoutY) {
        xTranslateProperty.set(layoutX);
        yTranslateProperty.set(layoutY);

        initSubscriptions();
    }

    private void initSubscriptions() {
    	previousPointChangedObservable.subscribe(this::onPreviousPointChanged);
    	nextPointChangedObservable.subscribe(this::onNextPointChanged);
    }

    public DoubleProperty xTranslateProperty() {
        return xTranslateProperty;
    }

    public Observable<Double> xTranslateObservable() {
    	return xTranslateObservable;
    }

    public Double getLayoutX() {
    	return xTranslateProperty.doubleValue();
    }

    public Observable<Double> yTranslateObservable() {
    	return yTranslateObservable;
    }

    public void setTranslateX(double value) {
    	xTranslateProperty.set(value);
    }


    public DoubleProperty layoutYProperty() {
        return yTranslateProperty;
    }

    public Double getLayoutY() {
    	return yTranslateProperty.doubleValue();
    }

    public void setTranslateY(double value) {
    	yTranslateProperty.set(value);
    }

    public ObjectProperty<Optional<Point>> previousPointProperty() {
        return previousPointProperty;
    }

    public Observable<Optional<Point>> previousPointObservable() {
    	return previousePointObservable;
    }

    public ObjectProperty<Optional<Point>> nextPointProperty() {
        return nextPointProperty;
    }

    public Observable<Optional<Point>> nextPointObservable() {
    	return nextPointObservable;
    }

    public void setNextPoint(Point nextPoint) {
        nextPointProperty.set(Optional.ofNullable(nextPoint));
    }

    public void setPreviousPoint(Point previousPoint) {
        previousPointProperty.set(Optional.ofNullable(previousPoint));
    }

    public Optional<Point> getPreviousPoint() {
        return previousPointProperty.get();
    }

    public Optional<Point> getNextPoint() {
        return nextPointProperty.get();
    }

    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

    public boolean isSelected() {
        return selectedProperty.get();
    }

    public void setSelected(boolean value) {
        selectedProperty.set(value);
    }

    public boolean equals(Point point) {
        return point == this;
    }

    private void onPreviousPointChanged(Change<Optional<Point>> change) {
    	change.getOldVal().ifPresent(point -> {
    		point.setNextPoint(null);
    	});

    	change.getNewVal().ifPresent(point -> {
    		point.setNextPoint(this);
    	});
    }

    private void onNextPointChanged(Change<Optional<Point>> change) {
    	change.getOldVal().ifPresent(point -> {
    		point.setPreviousPoint(null);
    	});

    	change.getNewVal().ifPresent(point -> {
    		point.setPreviousPoint(this);
    	});
    }

    public boolean hasPrevious() {
    	return previousPointProperty.get().map(previousPoint -> true).orElse(false);
    }

    public boolean hasNext() {
    	return nextPointProperty.get().map(nextPoint -> true).orElse(false);
    }

	@Override
	public String toString() {
		return "Point[x=" + xTranslateProperty.get() + ", y=" + yTranslateProperty.get() + ", previous=" + getPreviousPoint().map(point -> "Point[x" + point.getLayoutX() + ", y=" + point.getLayoutY() + "]").orElse("none") + ", next=" + getNextPoint().map(point -> "Point[x" + point.getLayoutX() + ", y=" + point.getLayoutY() + "]").orElse("none") + "]";
	}
}
