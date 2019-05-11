package pl.pkrysztofiak.mesurementsdrawer.view.panel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanelView;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class PanelView extends VBox {

	private final ObjectProperty<ToolbarView> toolbarViewProperty = new SimpleObjectProperty<>(new ToolbarView());
	private final ObjectProperty<ImagePanelView> imagePanelViewProperty = new SimpleObjectProperty<>(new ImagePanelView());

	public PanelView() {
		ToolbarView toolbarView = toolbarViewProperty.get();
		ImagePanelView imagePanelView = imagePanelViewProperty.get();
		getChildren().addAll(toolbarView, imagePanelView);
		setVgrow(imagePanelView, Priority.ALWAYS);
	}

	public ToolbarView getToolbarView() {
		return toolbarViewProperty.get();
	}

	public ImagePanelView getImagePanelView() {
		return imagePanelViewProperty.get();
	}
}