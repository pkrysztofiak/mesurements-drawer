package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;

public class ToolController {


    private final ObjectProperty<Tool> selectedToolProperty = new SimpleObjectProperty<>();
    private final Observable<Tool> selectedToolObservable = JavaFxObservable.valuesOf(selectedToolProperty);

    public ToolController() {
    }

    public void setSelectedTool(Tool tool) {
    	selectedToolProperty.set(tool);
    }

    public Observable<MeasurementView> measurementCreatedObservable() {
    	return selectedToolObservable.switchMap(Tool::measurementCreatedObservable);
    }

    public Tool getSelectedTool() {
    	return selectedToolProperty.get();
    }

    public Observable<Tool> selectedToolObservable() {
        return selectedToolObservable;
    }
}