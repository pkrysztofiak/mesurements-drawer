package pl.pkrysztofiak.mesurementsdrawer.model.measurements;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Point {

    private final ObjectProperty<Optional<Point>> previousPointProperty = new SimpleObjectProperty<>(Optional.empty());
    private final ObjectProperty<Optional<Point>> nextPointProperty = new SimpleObjectProperty<>(Optional.empty());
    
    private DoubleProperty layoutXProperty = new SimpleDoubleProperty();
    private DoubleProperty layoutYProperty = new SimpleDoubleProperty();
    
    private BooleanProperty selectedProperty = new SimpleBooleanProperty();
    
    public Point(double layoutX, double layoutY) {
        layoutXProperty.set(layoutX);
        layoutYProperty.set(layoutY);
    }
    
    public DoubleProperty layoutXProperty() {
        return layoutXProperty;
    }
    
    public DoubleProperty layoutYProperty() {
        return layoutYProperty;
    }
    
    public ObjectProperty<Optional<Point>> previousPointProperty() {
        return previousPointProperty;
    }
    
    public ObjectProperty<Optional<Point>> nextPointProperty() {
        return nextPointProperty;
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
}
