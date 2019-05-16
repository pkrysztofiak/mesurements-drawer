package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Point;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.PolygonMeasurement;

public class PolygonTool extends Tool {

    private final Behaviour behaviour = new Behaviour();

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
//        behaviour.onMouseReleased(mouseEvent);
    }

    @Override
	public void onMouseClicked(MouseEvent mouseEvent) {
    	behaviour.onMouseClicked(mouseEvent);
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
//        	newMeasurementCreatedPublishable.onNext(new PolygonMeasurement(new Point(mouseEvent.getX(), mouseEvent.getY())));
        }

        private void onMouseClicked(MouseEvent mouseEvent) {
        	newMeasurementCreatedPublishable.onNext(new PolygonMeasurement(new Point(mouseEvent.getX(), mouseEvent.getY())));
        }
    }
}