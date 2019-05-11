package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.PolygonDrawer;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon.PolygonMeasurement;

public class PolygonTool extends Tool {

    private final Behaviour behaviour = new Behaviour();
    private final PolygonDrawer polygonDrawer = new PolygonDrawer();


    public PolygonTool() {
        initSubscriptions();
    }

    private void initSubscriptions() {
        polygonDrawer.measurementCreatedObservable().subscribe(behaviour::onMeasurementCreated);
    }

    @Override
    public ToolType getType() {
        return ToolType.POLYGON;
    }

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
        behaviour.onMouseReleased(mouseEvent);
    }

    private class Behaviour {

        private void onMouseReleased(MouseEvent mouseEvent) {
            System.out.println("PolygonTool onMouseReleased");
            PolygonMeasurement polygonMeasurement = new PolygonMeasurement();
            measurementCreatedPubslishable.onNext(polygonMeasurement);
        }

        private void onMeasurementCreated(Measurement measurement) {
        }
    }

	@Override
	public String toString() {
		return "PolygonTool";
	}

}