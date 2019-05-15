package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.behaviour.OnMouseHoverAbstract;

//TO REMOVE
public abstract class PointNode<N extends Node> {

	protected N node;

	protected OnMouseHoverAbstract<N> onMouseHover;
	protected final ObjectProperty<OnMouseHoverAbstract<N>> onMouseHoverProperty = new SimpleObjectProperty<>();

	public PointNode(N node) {
		this.node = node;
	}

	public N getNode() {
		return node;
	}

	protected void setOnMouseHover(OnMouseHoverAbstract<N> onMouseHover) {
		this.onMouseHover = onMouseHover;
	}
}