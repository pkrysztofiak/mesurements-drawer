package pl.pkrysztofiak.mesurementsdrawer.common;

import javafx.scene.input.MouseEvent;

public interface EventsReceiver {

    void onMouseReleased(MouseEvent mouseEvent);
    void onMouseClicked(MouseEvent mouseEvent);

    default void mouseReleased(MouseEvent mouseEvent) {
        onMouseReleased(mouseEvent);
    }

    default void mouseClicked(MouseEvent mouseEvent) {
    	onMouseClicked(mouseEvent);
    }

}