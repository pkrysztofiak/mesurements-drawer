package pl.pkrysztofiak.mesurementsdrawer.controller.toolbar;

import io.reactivex.Observable;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.ToolController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementViewOld;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class ToolbarController {

	private final ToolController toolController;

	public ToolbarController(ToolbarView toolbarView, Model model) {
		toolController = new ToolController(model);
		toolbarView.selectedToolObservable().subscribe(toolController::setSelectedTool);
	}

	public Observable<Tool> selectedToolObservable() {
		return toolController.selectedToolObservable();
	}

	public Observable<MeasurementViewOld> newMeasurementViewCreatedObservable() {
		return toolController.newMeasurementViewCreatedObservable();
	}

	public Observable<Measurement> newMeasurementCreatedObservable() {
		return toolController.newMeasurementCreatedObservable();
	}

	public Tool getSelectedTool() {
		return toolController.getSelectedTool();
	}
}