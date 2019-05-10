package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import java.util.Optional;

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
//        mouseReleasedObservable.subscribe(behaviour::onMouseReleased);
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
            Optional.ofNullable(activePanelController.get()).ifPresent(panelController -> {
                System.out.println("active panel controller present");
                PolygonMeasurement polygonMeasurement = new PolygonMeasurement();
                panelController.setEventsReceiver(polygonMeasurement);
                panelController.addMeasurement(polygonMeasurement);
            });
        }

        private void onMeasurementCreated(Measurement measurement) {
            Optional.ofNullable(activePanelController.get()).ifPresent(panelController -> panelController.addMeasurement(measurement));
        }
    }

}