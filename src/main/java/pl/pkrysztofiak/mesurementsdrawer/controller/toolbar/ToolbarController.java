package pl.pkrysztofiak.mesurementsdrawer.controller.toolbar;

import io.reactivex.Observable;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.ToolController;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class ToolbarController {

	private final ToolController toolController;

	public ToolbarController(ToolbarView toolbarView) {
		toolController = new ToolController();
		toolbarView.selectedToolObservable().subscribe(toolController::setSelectedTool);
	}

	public Observable<Tool> selectedToolObservable() {
		return toolController.selectedToolObservable();
	}

	public Observable<Measurement> measurementCreatedObservable() {
		return toolController.measurementCreatedObservable();
	}
}