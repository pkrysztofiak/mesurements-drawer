package pl.pkrysztofiak.mesurementsdrawer.controller.panel;

import pl.pkrysztofiak.mesurementsdrawer.controller.panel.image.ImagePanelController;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.toolbar.ToolbarController;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.PanelView;

public class PanelController {

	private final Behaviour behaviour = new Behaviour();

	private final PanelView panelView;

	private final ToolbarController toolbarController;
	private final ImagePanelController imagePanelController;

	public PanelController(PanelView panelView) {
		this.panelView = panelView;

		toolbarController = new ToolbarController(panelView.getToolbarView());
		imagePanelController = new ImagePanelController(panelView.getImagePanelView());

		initSubscriptions();
	}

	public void addMeasurement(Measurement measurement) {
		imagePanelController.addMeasurement(measurement);
	}

	private void initSubscriptions() {
		toolbarController.selectedToolObservable().subscribe(behaviour::onSelectedToolChanged);
		toolbarController.measurementCreatedObservable().subscribe(behaviour::onMeasurementCreated);
	}

	public ImagePanelController getImagePanelController() {
		return imagePanelController;
	}

	public void setSelected(boolean value) {
		imagePanelController.setSelected(value);
	}

	public boolean equals(PanelController panelController) {
		return this == panelController;
	}

	public boolean unequals(PanelController panelController) {
		return this != panelController;
	}

	private class Behaviour {

		private void onSelectedToolChanged(Tool tool) {
			imagePanelController.setEventsReceiver(tool);
		}

		private void onMeasurementCreated(Measurement measurement) {
			imagePanelController.addMeasurement(measurement);
			imagePanelController.setEventsReceiver(measurement);

			//TODO dopisać takeUntil na usunięcie (dodatkowy proprties)
			measurement.finishedObservale().subscribe(this::onMeasurementFinished);
		}

		private void onMeasurementFinished(Measurement measurement) {
			imagePanelController.setEventsReceiver(toolbarController.getSelectedTool());
		}
	}
}
