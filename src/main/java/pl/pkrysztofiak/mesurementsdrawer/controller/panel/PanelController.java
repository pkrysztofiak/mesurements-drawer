package pl.pkrysztofiak.mesurementsdrawer.controller.panel;

import pl.pkrysztofiak.mesurementsdrawer.controller.panel.image.ImagePanelController;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.toolbar.ToolbarController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.PanelView;

public class PanelController {

	private final Behaviour behaviour = new Behaviour();

	private final Model model;

	private final PanelView panelView;

	private final ToolbarController toolbarController;
	private final ImagePanelController imagePanelController;

	public PanelController(PanelView panelView, Model model) {
		this.panelView = panelView;
		this.model = model;

		toolbarController = new ToolbarController(panelView.getToolbarView(), model);
		imagePanelController = new ImagePanelController(panelView.getImagePanelView(), model);

		initSubscriptions();
	}

	public void addMeasurement(MeasurementView measurementView) {
		imagePanelController.addMeasurement(measurementView);
	}

	private void initSubscriptions() {
		toolbarController.selectedToolObservable().subscribe(behaviour::onSelectedToolChanged);
		toolbarController.newMeasurementCreatedObservable().subscribe(behaviour::onNewMeasurementCreated);
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

		private void onNewMeasurementCreated(MeasurementView measurementView) {
			imagePanelController.addMeasurement(measurementView);
			imagePanelController.setEventsReceiver(measurementView);

			//TODO dopisać takeUntil na usunięcie (dodatkowy proprties)
			measurementView.finishedObservale().subscribe(this::onMeasurementFinished);
		}

		private void onMeasurementFinished(MeasurementView measurementView) {
			imagePanelController.setEventsReceiver(toolbarController.getSelectedTool());
			model.addMeasurement(measurementView.getMeasurement());
		}
	}
}
