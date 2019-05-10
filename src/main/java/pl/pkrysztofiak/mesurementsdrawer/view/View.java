package pl.pkrysztofiak.mesurementsdrawer.view;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.Panel;

public class View {

    private final Stage stage = new Stage();

    private final ToggleGroup toolsToggleGroup = new ToggleGroup();
    private final ToggleButton polygonToolButton = new ToggleButton("Polygon");
    private final ToggleButton lineToolButton = new ToggleButton("Line");

    private final HBox toolbarHbox = new HBox(polygonToolButton, lineToolButton);
    private final GridPane panelsGridPane = new GridPane();
    private final VBox vBox = new VBox(toolbarHbox, panelsGridPane);
    private final ObservableList<Panel> panels = FXCollections.observableArrayList();

    private final Observable<Panel> panelAddedObservable = JavaFxObservable.additionsOf(panels);

    public View() {
    	VBox.setVgrow(panelsGridPane, Priority.ALWAYS);

    	toolsToggleGroup.getToggles().addAll(polygonToolButton, lineToolButton);
    }

    public void show() {

        int rows = 2;
        int columns = 2;

        Bindings.bindContent(panelsGridPane.getChildren(), panels);

        for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(100, 100, 10e6, Priority.ALWAYS, HPos.LEFT, true);
            panelsGridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            RowConstraints rowConstraints = new RowConstraints(100, 100, 10e6, Priority.ALWAYS, VPos.TOP, true);
            panelsGridPane.getRowConstraints().add(rowConstraints);
        }

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                Panel panel = new Panel();
                GridPane.setConstraints(panel, columnIndex, rowIndex);
                panels.add(panel);
            }
        }

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


        stage.setScene(new Scene(vBox));
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setMaximized(true);
        stage.show();
    }

    public Observable<Panel> panelCreatedObservable() {
        return panelAddedObservable;
    }
}
