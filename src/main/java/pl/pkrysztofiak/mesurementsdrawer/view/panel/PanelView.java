package pl.pkrysztofiak.mesurementsdrawer.view.panel;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanelView;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class PanelView extends VBox {

	private final ObjectProperty<Optional<ToolbarView>> toolbarViewOptionalProperty = new SimpleObjectProperty<>(Optional.empty());
	private final ObjectProperty<Optional<ImagePanelView>> imagePanelViewOptionalProperty = new SimpleObjectProperty<>(Optional.empty());


}
