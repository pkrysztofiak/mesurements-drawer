package pl.pkrysztofiak.mesurementsdrawer.common;

import javafx.scene.input.MouseEvent;

public interface EventsReceiver {

    void onMouseReleased(MouseEvent mouseEvent);

    default void mouseReleased(MouseEvent mouseEvent) {
        onMouseReleased(mouseEvent);
    }
}