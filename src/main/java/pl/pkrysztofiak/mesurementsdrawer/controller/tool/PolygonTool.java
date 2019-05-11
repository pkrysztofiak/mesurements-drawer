package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurement;

public class PolygonTool extends Tool {

    private final Behaviour behaviour = new Behaviour();

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
        behaviour.onMouseReleased(mouseEvent);
    }

    @Override
    public ToolType getType() {
    	return ToolType.POLYGON;
    }

    @Override
    public String toString() {
    	return "PolygonTool";
    }

    private class Behaviour {

        private void onMouseReleased(MouseEvent mouseEvent) {
            PolygonMeasurement polygonMeasurement = new PolygonMeasurement();
            measurementCreatedPubslishable.onNext(polygonMeasurement);
        }
    }
}