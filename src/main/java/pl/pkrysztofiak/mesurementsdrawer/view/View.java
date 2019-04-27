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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.Panel;

public class View {

    private final Stage stage = new Stage();
    private final GridPane gridPane = new GridPane();
    private final ObservableList<Panel> panels = FXCollections.observableArrayList();
    
    private final Observable<Panel> panelAddedObservable = JavaFxObservable.additionsOf(panels);
    
    public View() {
        
    }
    
    public void show() {

        int rows = 2;
        int columns = 2;

        Bindings.bindContent(gridPane.getChildren(), panels);
        
        for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(100, 100, 10e6, Priority.ALWAYS, HPos.LEFT, true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            RowConstraints rowConstraints = new RowConstraints(100, 100, 10e6, Priority.ALWAYS, VPos.TOP, true);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                Panel panel = new Panel();
                GridPane.setConstraints(panel, columnIndex, rowIndex);
                panels.add(panel);
            }
        }
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        
        stage.setScene(new Scene(gridPane));
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
