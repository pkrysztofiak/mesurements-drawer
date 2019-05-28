package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;

public class PolygonMeasurementView extends MeasurementView {

	private final Group edgesPane = new Group();
	private final Group verticesPane = new Group();
	private final Group group = new Group(edgesPane, verticesPane);

	public PolygonMeasurementView() {
		getChildren().add(group);
//		getChildren().addAll(edgesPane, verticesPane);
	}

	public void setTranslateXGroup(double value) {
		group.setTranslateX(value);
//		setTranslateX(value);
	}

	public void setTranslateYGroup(double value) {
		group.setTranslateY(value);
//		setTranslateY(value);
	}

	public ObservableList<Node> getVerticesChildren() {
		return verticesPane.getChildren();
	}

	public ObservableList<Node> getEdgesChildren() {
		return edgesPane.getChildren();
	}
}