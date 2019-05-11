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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.PanelView;
import pl.pkrysztofiak.mesurementsdrawer.view.panel.image.ImagePanelView;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class View {

    private final Stage stage = new Stage();

    private final GridPane panelsGridPane = new GridPane();
    private final BorderPane borderPane = new BorderPane(panelsGridPane);

    private final ObservableList<PanelView> panelsViews = FXCollections.observableArrayList();
    private final Observable<PanelView> panelViewAddedObservable = JavaFxObservable.additionsOf(panelsViews);

    private final ObservableList<ImagePanelView> imagePanelViews = FXCollections.observableArrayList();

    private final Observable<ImagePanelView> panelAddedObservable = JavaFxObservable.additionsOf(imagePanelViews);

    public View() {
    	VBox.setVgrow(panelsGridPane, Priority.ALWAYS);
    }

    public void show() {

        int rows = 2;
        int columns = 2;

//        Bindings.bindContent(panelsGridPane.getChildren(), imagePanelViews);
        Bindings.bindContent(panelsGridPane.getChildren(), panelsViews);

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
            	PanelView panelView = new PanelView();
            	GridPane.setConstraints(panelView, columnIndex, rowIndex);
            	panelsViews.add(panelView);

//                ImagePanelView imagePanelView = new ImagePanelView();
//                GridPane.setConstraints(imagePanelView, columnIndex, rowIndex);
//                imagePanelViews.add(imagePanelView);
            }
        }


        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


        stage.setScene(new Scene(borderPane));
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setMaximized(true);
        stage.show();
    }

    public Observable<PanelView> panelViewAddedObservable() {
    	return panelViewAddedObservable;
    }

    public Observable<ImagePanelView> panelCreatedObservable() {
        return panelAddedObservable;
    }

    public void setToolbarView(ToolbarView toolbarView) {
    	borderPane.setTop(toolbarView);
    }
}
