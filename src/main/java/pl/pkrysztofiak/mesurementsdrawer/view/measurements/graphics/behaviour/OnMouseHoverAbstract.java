package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics.behaviour;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.scene.Node;

public abstract class OnMouseHoverAbstract<N extends Node> implements OnMouseHover {

	protected final N node;

	public OnMouseHoverAbstract(N node) {
		this.node = node;

		JavaFxObservable.valuesOf(node.hoverProperty()).subscribe(this::onMouseHover);
	}

}