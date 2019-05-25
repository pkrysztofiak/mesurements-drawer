package pl.pkrysztofiak.fx.layout;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class LayoutTestApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Circle circle = new Circle(8, Color.BLUEVIOLET);

		AnchorPane anchorPane = new AnchorPane(circle);

	}
}