package pl.pkrysztofiak.mesurementsdrawer.view.toolbar;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.ToolType;

public class ToolbarView extends HBox {

	private final ToggleGroup toolsToggleGroup = new ToggleGroup();
	private final Observable<Toggle> selectedToolToggleObservable = JavaFxObservable.valuesOf(toolsToggleGroup.selectedToggleProperty());
	private final Observable<Tool> selectedToolObservable = selectedToolToggleObservable.cast(ToolToggleButton.class).map(ToolToggleButton::getToolType).map(ToolType::getTool);

	private final ToolToggleButton polygonToolButton = new ToolToggleButton("Polygon", ToolType.POLYGON);
	private final ToolToggleButton lineToolButton = new ToolToggleButton("Line", ToolType.LINE);

	public ToolbarView() {
		getChildren().addAll(polygonToolButton, lineToolButton);
		toolsToggleGroup.getToggles().addAll(polygonToolButton, lineToolButton);
	}

	public Observable<Tool> selectedToolObservable() {
		return selectedToolObservable;
	}

	private class ToolToggleButton extends ToggleButton {

		private final ToolType toolType;

		public ToolToggleButton(ToolType toolType) {
			super();
			this.toolType = toolType;
		}

		public ToolToggleButton(String text, Node graphic, ToolType toolType) {
			super(text, graphic);
			this.toolType = toolType;
		}

		public ToolToggleButton(String text, ToolType toolType) {
			super(text);
			this.toolType = toolType;
		}

		public ToolType getToolType() {
			return toolType;
		}
	}
}