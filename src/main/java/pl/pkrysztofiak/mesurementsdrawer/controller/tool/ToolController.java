package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.model.measurements.Measurement;
//import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementViewOld;

public class ToolController {


    private final ObjectProperty<Tool> selectedToolProperty = new SimpleObjectProperty<>();
    private final Observable<Tool> selectedToolObservable = JavaFxObservable.valuesOf(selectedToolProperty);

    public ToolController(Model model) {

    }

    public void setSelectedTool(Tool tool) {
    	selectedToolProperty.set(tool);
    }

//    public Observable<MeasurementViewOld> newMeasurementViewCreatedObservable() {
//    	return selectedToolObservable.switchMap(Tool::newMeasurementViewCreatedObservable);
//    }

    public Observable<Measurement> newMeasurementCreatedObservable() {
    	return selectedToolObservable.switchMap(Tool::newMeasurementCreatedPublishable);
    }

    public Tool getSelectedTool() {
    	return selectedToolProperty.get();
    }

    public Observable<Tool> selectedToolObservable() {
        return selectedToolObservable;
    }
}