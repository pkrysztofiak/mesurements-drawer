package pl.pkrysztofiak.mesurementsdrawer.controller.toolbar;

import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.ToolController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class ToolbarController {

	private final ToolController toolController;

//	private final ObjectProperty<PanelController> selectedPanelControllerProperty = new SimpleObjectProperty<>();
//	private final Observable<PanelController> selectedPanelControllerObservable = JavaFxObservable.valuesOf(selectedPanelControllerProperty);

	public ToolbarController(ToolbarView view, Model model) {
		toolController = new ToolController(model);

		view.selectedToolObservable().subscribe(toolController::setSelectedTool);

//		view.selectedToolObservable().subscribe(onNext)
	}

	public void setSelectedPanelController(PanelController panelController) {
		toolController.setSelectedPanelController(panelController);
//		selectedPanelControllerProperty.set(panelController);
	}
}
