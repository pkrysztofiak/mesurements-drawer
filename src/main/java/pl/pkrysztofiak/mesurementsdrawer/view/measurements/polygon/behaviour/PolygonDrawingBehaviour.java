package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.behaviour;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public abstract class PolygonDrawingBehaviour {

    protected final ObservableList<Point> points = FXCollections.observableArrayList();
    protected final ObservableList<Node> children = FXCollections.observableArrayList();
    
    public ObservableList<Point> getPoints() {
        return points;
    }

    public ObservableList<Node> getChildren() {
        return children;
    }
}