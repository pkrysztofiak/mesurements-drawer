package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;

public class CirclePoint extends Circle {

    private final Point point;
    
    public CirclePoint(Point point) {
        super();
        this.point = point;
    }

    public CirclePoint(double centerX, double centerY, double radius, Paint fill, Point point) {
        super(centerX, centerY, radius, fill);
        this.point = point;
    }

    public CirclePoint(double centerX, double centerY, double radius, Point point) {
        super(centerX, centerY, radius);
        this.point = point;
    }

    public CirclePoint(double radius, Paint fill, Point point) {
        super(radius, fill);
        this.point = point;
    }

    public CirclePoint(double radius, Point point) {
        super(radius);
        this.point = point;
    }
}