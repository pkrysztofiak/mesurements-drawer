package pl.pkrysztofiak.fx.drag;


import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleDragTestApp extends Application {

	private final Circle circle = new Circle(36, Color.CADETBLUE);
	private final Pane pane = new Pane(circle);


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

//		circle.setLayoutX(100);
//		circle.setLayoutY(100);
//		circle.setCenterX(100);
//		circle.setCenterY(100);
		circle.relocate(100, 100);

		pane.setPrefSize(400, 400);
		pane.setPickOnBounds(false);

		JavaFxObservable.eventsOf(pane, MouseEvent.ANY).subscribe(MouseEvent::consume);

		Scene scene = new Scene(pane);

		stage.setScene(scene);
		stage.show();

		Observable<Double> mousePressedXObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_PRESSED).map(MouseEvent::getX);
		Observable<Double> mousePressedYObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_PRESSED).map(MouseEvent::getY);

		Observable<Double> mouseDraggedXObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_DRAGGED).map(MouseEvent::getX);
		Observable<Double> mouseDraggedYObservable = JavaFxObservable.eventsOf(circle, MouseEvent.MOUSE_DRAGGED).map(MouseEvent::getY);

		Observable<Double> layoutXObservable = JavaFxObservable.valuesOf(circle.layoutXProperty()).map(Number::doubleValue);
		Observable<Double> layoutYObservable = JavaFxObservable.valuesOf(circle.layoutYProperty()).map(Number::doubleValue);

		mousePressedXObservable.switchMap(pressedX -> mouseDraggedXObservable.withLatestFrom(layoutXObservable, (draggedX, layoutX) -> (draggedX - pressedX) + layoutX)).subscribe(circle::setLayoutX);
		mousePressedYObservable.switchMap(pressedY -> mouseDraggedYObservable.withLatestFrom(layoutYObservable, (draggedY, layoutY) -> (draggedY - pressedY) + layoutY)).subscribe(circle::setLayoutY);

	}
}