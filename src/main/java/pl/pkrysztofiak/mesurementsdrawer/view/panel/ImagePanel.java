package pl.pkrysztofiak.mesurementsdrawer.view.panel;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ImagePanel extends StackPane {

    private final Behaviour behaviour = new Behaviour();

    private final Observable<MouseEvent> mousePressedObservable = JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_PRESSED);
    private final Observable<MouseEvent> mouseDraggedObservable = JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_DRAGGED);
    private final Observable<MouseEvent> mouseReleasedObservable = JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_RELEASED);

    private final PublishSubject<MouseEvent> mouseAnyPublishable = PublishSubject.create();


    public ImagePanel() {
        setStyle("-fx-background-color: gray; -fx-border-color: red;");
        initSubscriptions();
    }

    public void setSelected(boolean value) {
        String borderStyle = value ? "-fx-border-color: red;" : "-fx-border-color: yellow;";
        setStyle("-fx-background-color: gray;" + borderStyle);
    }

    private void initSubscriptions() {
        addEventFilter(MouseEvent.MOUSE_PRESSED, mouseAnyPublishable::onNext);
    }

    public Observable<MouseEvent> mouseReleasedObservable() {
    	System.out.println("panel MOUSE_PRESSED");
        return mouseReleasedObservable;
    }

    public Observable<MouseEvent> mouseAnyObservable() {
        return mouseAnyPublishable;
    }

    class Behaviour {

    }
}