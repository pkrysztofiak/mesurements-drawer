package pl.pkrysztofiak.mesurementsdrawer;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class LineInvertedClippedApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/image.jpg"));
		ImageView imageView = new ImageView(image);
		Image imageInverse = new Image(getClass().getClassLoader().getResourceAsStream("images/image-invert.jpg"));
		ImageView imageViewInverse = new ImageView(imageInverse);


		StackPane stackPane = new StackPane(imageView, imageViewInverse);
		Button button = new Button("Down");
		VBox vBox = new VBox(stackPane, button);

		Line line = new Line(100, 100, 300, 300);
		line.setStrokeWidth(1);

		imageViewInverse.setClip(line);

		JavaFxObservable.actionEventsOf(button)
		.subscribe(event -> {
			line.setStartX(line.getStartX() + 10);
			line.setStartY(line.getStartY() + 10);
			line.setEndX(line.getEndX() + 10);
			line.setEndY(line.getEndY() + 10);
		});

		stage.setScene(new Scene(vBox));
		stage.show();
	}
}
