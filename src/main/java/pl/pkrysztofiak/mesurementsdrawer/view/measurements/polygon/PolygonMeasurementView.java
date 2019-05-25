package pl.pkrysztofiak.mesurementsdrawer.view.measurements.polygon;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;

public class PolygonMeasurementView extends MeasurementView {

//	private final Pane verticesPane = new Pane();
	private final Group verticesPane = new Group();
//	private final Pane edgesPane = new Pane();
	private final Group edgesPane = new Group();

	private final Behaviour behaviour = new Behaviour();

	public PolygonMeasurementView() {
//		verticesPane.setPickOnBounds(false);
//		edgesPane.setPickOnBounds(false);
//		getChildren().addAll(edgesPane, verticesPane);
	}

	public ObservableList<Node> getVerticesChildren() {
		return getChildren();
//		return verticesPane.getChildren();
	}

	public ObservableList<Node> getEdgesChildren() {
		return getChildren();
//		return edgesPane.getChildren();
	}

	private class Behaviour {

	}
}